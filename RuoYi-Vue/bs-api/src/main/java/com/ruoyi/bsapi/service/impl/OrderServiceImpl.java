package com.ruoyi.bsapi.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.domain.*;
import com.ruoyi.bsapi.service.*;
import com.ruoyi.bsapi.utils.FileUtil;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.*;
import com.ruoyi.common.utils.my.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.OrderMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 订单Service业务层处理
 *
 * @author lqg
 * @date 2023-04-07
 */
@Service
public class OrderServiceImpl implements IOrderService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private IProductService productService;

    @Resource
    private IAddressService addressService;

    @Resource
    private IOrderItemService orderItemService;

    @Resource
    private ISkuinfoService skuinfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IAttrProductService attrProductService;

    @Resource
    private IAttrsService attrsService;

    @Resource
    private IUserService userService;

    @Resource
    private IShopService shopService;

    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public Order selectOrderByOrderId(Long orderId) {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<Order> selectOrderList(Order order) {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    public int insertOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderIds(Long[] orderIds) {
        return orderMapper.deleteOrderByOrderIds(orderIds);
    }

    /**
     * 删除订单信息
     *
     * @param orderId 订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderId(Long orderId) {
        return orderMapper.deleteOrderByOrderId(orderId);
    }

    @Transactional
    @Override
    public String submitOrder(String token) {
        //获取最新的购物车信息
        CartResultVo cartResultVo = productService.resultCart(token);
        //构建一个大大的订单
        Order order = createOrder(cartResultVo, token);
        //状态
        order.setLogistics(1L);
        //没有支付的先保存在redis中，过期自动删除
        String key = RedisConsant.ORDER_TEMPLATE_KEY + order.getOrderSn();
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(order), 30L, TimeUnit.MINUTES);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.MINUTES);
        //保存订单
        orderMapper.insertOrder(order);
        return time.format(new Date(expire));
    }

    @Override
    public List<OrderResp> myOrderList(String token, Integer status, String orderSn) {
        //查询当前用户的购物车
        List<Skuinfo> skuinfos = skuinfoService.selectSkuinfoList(null);
        List<Product> products = productService.selectProductList(null);
        User user = RedisConsant.getUser(stringRedisTemplate, token);
        Order orderParams = new Order();
        orderParams.setUserId(user.getUserId());
        orderParams.setOrderSn(orderSn);
        orderParams.setLogistics(Long.valueOf(status));
        List<OrderResp> orderResps = this.selectOrderList(orderParams).stream().map(order -> {
            final BigDecimal[] fare = {new BigDecimal("0")};
            OrderResp orderResp = new OrderResp();
            orderResp.setOrderSn(order.getOrderSn());
            //查看是否有过期时间
            String key = RedisConsant.ORDER_TEMPLATE_KEY + order.getOrderSn();
            Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (StringUtils.isNotNull(expire)) {
                orderResp.setTime(expire.toString());
            }
            orderResp.setTotalPrice(order.getPayPrice());
            orderResp.setCreateDate(sdf.format(order.getCreateDate()));
            orderResp.setStatus(order.getLogistics());
            //每一个订单项
            OrderItem orderItemParams = new OrderItem();
            orderItemParams.setOrderSn(order.getOrderSn());
            List<OrderResp.OrderItemVo> itemVos = orderItemService.selectOrderItemList(orderItemParams).stream().map(orderItem -> {
                OrderResp.OrderItemVo orderItemVo = new OrderResp.OrderItemVo();
                for (Skuinfo skuinfo : skuinfos) {
                    for (Product product : products) {
                        if (skuinfo.getSkuId().equals(orderItem.getProductId()) && skuinfo.getProductId().equals(product.getProductId())) {
                            orderItemVo.setProductId(skuinfo.getSkuId());
                            orderItemVo.setProductTitle(product.getProductTitle() + " " + skuinfo.getSkuName());
                            orderItemVo.setProductPic(skuinfo.getSkuPic() == null ? FileUtil.LOCALHOST_SERVER_UPLOAD + product.getProductPic() : FileUtil.LOCALHOST_SERVER_UPLOAD + skuinfo.getSkuPic());
                            orderItemVo.setProductPrice(skuinfo.getSkuPrice());
                            orderItemVo.setProductCount(orderItem.getProductCount());
                            //计算运费
                            fare[0] = fare[0].add(orderItem.getFare());
                            AttrProduct attrProductParams = new AttrProduct();
                            attrProductParams.setProductId(product.getProductId());
                            List<Long> attrIds = attrProductService.selectAttrProductList(attrProductParams).stream().map(AttrProduct::getAttrId).collect(Collectors.toList());
                            List<AttrProductGroupByVo> attrProductGroupByVos = attrsService.selectAttrsByGroupByProductId(attrIds);
                            //List<Attrs> attrList = attrProductGroupByVos.stream().map(attr -> {
                            //    Attrs attrs = new Attrs();
                            //    if (StringUtils.isNotNull(attr)) {
                            //        attrs.setAttrName(attr.getAttrName());
                            //        attrs.setAttrValue(attr.getAttrValues());
                            //    } else {
                            //        attrs.setAttrName("款式");
                            //        attrs.setAttrValue(skuinfo.getSkuName());
                            //    }
                            //    return attrs;
                            //}).collect(Collectors.toList());
                            Attrs attrs = new Attrs();
                            attrs.setAttrName("款式");
                            attrs.setAttrValue(skuinfo.getSkuName());
                            orderItemVo.setAttrs(Arrays.asList(attrs));
                            return orderItemVo;
                        }
                    }
                }
                return orderItemVo;
            }).collect(Collectors.toList());
            orderResp.setFare(fare[0]);
            orderResp.setItems(itemVos);
            return orderResp;
        }).collect(Collectors.toList());

        return orderResps;
    }

    @Override
    public void deleteOrder(String orderSn) {
        //删除子项
         orderItemService.deleteOrderItem(orderSn);
        //再删除当前订单
        orderMapper.deleteOrder(orderSn);
    }

    @Transactional
    @Override
    public void payOrder(String token, Long payType, String orderSn) throws Exception {
        BigDecimal payPrice = new BigDecimal("0");
        if (StringUtils.isNotNull(orderSn)) {
            //查询当前的订单信息
            Order orderParams = new Order();
            orderParams.setOrderSn(orderSn);
            List<Order> orders = this.selectOrderList(orderParams);
            if (!CollectionUtils.isEmpty(orders)) {
                Order order = orders.get(0);
                order.setPayStatus(payType);
                order.setLogistics(2L);
                payPrice = order.getPayPrice();
                //修改状态
                orderMapper.updateOrder(order);
                //确保redis中计时关闭
                stringRedisTemplate.delete(RedisConsant.ORDER_TEMPLATE_KEY + order.getOrderSn());
            }
        } else {
            //获取最新的购物车信息
            CartResultVo cartResultVo = productService.resultCart(token);
            Order order = this.createOrder(cartResultVo, token);
            order.setPayStatus(payType);
            order.setLogistics(2L);
            payPrice = order.getPayPrice();
            orderMapper.insertOrder(order);
        }
        //如果是本地支付
        if (payType == 2) {
            User currentUser = RedisConsant.getUser(stringRedisTemplate, token);
            User user = userService.selectUserByUserId(currentUser.getUserId());
            //减去价格
            if (user.getTotalPrice().intValue() < payPrice.intValue()) {
                throw new RuntimeException("你的余额不足，请充值！");
            }
            user.setTotalPrice(user.getTotalPrice().subtract(payPrice));
            userService.updateUserById(user);
            String userKey = RedisConsant.CLIENT_CURRENT_USER + token;
            Long expire = stringRedisTemplate.getExpire(userKey);
            stringRedisTemplate.opsForValue().set(userKey, JSON.toJSONString(user), expire, TimeUnit.SECONDS);
        }
        //支付成功后，计算每个商品的销量
        OrderItem orderItemParams = new OrderItem();
        orderItemParams.setOrderSn(orderSn);
        for (OrderItem orderItem : orderItemService.selectOrderItemList(orderItemParams)) {
            //key
            Skuinfo skuinfo = skuinfoService.selectSkuinfoBySkuId(orderItem.getProductId());
            Product product = productService.selectProductByProductId(skuinfo.getProductId());
            String key = RedisConsant.PRODUCT_MONTH_PIN_KEY + product.getProductId();
            //value
            Integer sum = 1;
            String pin = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotNull(pin)) {
                sum = Integer.parseInt(pin);
                sum++;
            }
            //重新设置进入
            stringRedisTemplate.opsForValue().set(key, sum.toString());
        }
    }

    @Override
    public List<OrderVo> queryAllOrderList(String orderSn, Integer logistics) {
        //封装条件
        Order orderParams = new Order();
        if (StringUtils.isNotNull(orderSn)) {
            orderParams.setOrderSn(orderSn);
        }
        if (StringUtils.isNotNull(logistics)) {
            orderParams.setLogistics(Long.parseLong(logistics.toString()));
        }
        //开启分页
        startPage();
        List<Order> orders = orderMapper.selectOrderList(orderParams);
        //所有订单项
        List<OrderItem> orderItems = orderItemService.selectOrderItemList(null);
        //商品
        List<Product> productList = productService.selectProductList(null);
        List<Skuinfo> skuinfos = skuinfoService.selectSkuinfoList(null);
        //店铺
        List<Shop> shops = shopService.selectShopList(null);

        //组装数据
        List<OrderVo> orderVoList = orders.stream().map(order -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(order, orderVo);
            //订单项
            List<OrderVo.OrderItemVo> items = orderItems.stream().map(orderItem -> {
                if (orderItem.getOrderSn().equals(order.getOrderSn())) {
                    OrderVo.OrderItemVo orderItemVo = new OrderVo.OrderItemVo();
                    BeanUtils.copyProperties(orderItem, orderItemVo);
                    //订单商品名称
                    for (Skuinfo skuinfo : skuinfos) {
                        for (Product product : productList) {
                            if (skuinfo.getSkuId().equals(orderItem.getProductId()) && skuinfo.getProductId().equals(product.getProductId())) {
                                orderItemVo.setProductTitle(product.getProductTitle());
                                orderItemVo.setProductPrice(skuinfo.getSkuPrice());
                                orderItemVo.setProductPic(FileUtil.LOCALHOST_SERVER_UPLOAD + skuinfo.getSkuPic());
                            }
                        }
                    }
                    //设置店铺名称
                    for (Shop shop : shops) {
                        if (orderItem.getShopId().equals(shop.getShopId())) {
                            orderItemVo.setShopName(shop.getShopName());
                        }
                    }
                    return orderItemVo;
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            //设置订单项
            orderVo.setItems(items);
            return orderVo;
        }).collect(Collectors.toList());

        return orderVoList;
    }


    @Override
    public Integer queryOrderCount(String orderSn, Integer logistics) {
        return orderMapper.queryOrderCount(orderSn, logistics);
    }

    @Override
    public Integer updateLogistics(String str) {
        //截取字符串
        String[] order = str.split("-");
        String orderSn = order[0]; //订单号
        Integer logistics = Integer.parseInt(order[1]); //物流状态
        //如果物流状态大于 4 ，那么是无效值
        logistics++;
        if (logistics > 4) {
            return 0;
        }
        return orderMapper.updateLogistics(orderSn, logistics);
    }

    @Override
    public Integer deleteOrderByOrderSn(String[] orderSn) {
        int result = 1;
        try {
            //先批量删除当前订单的订单项
            orderItemService.batchDeleteOrderItem(orderSn);
            //批量删除
            orderMapper.batchDeleteOrder(orderSn);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }

    /**
     * 构建一个订单
     *
     * @param cartResultVo
     * @param token
     * @return
     */
    private Order createOrder(CartResultVo cartResultVo, String token) {
        Order order = new Order();
        BigDecimal fare = new BigDecimal("0");
        //生成一个订单号
        String orderSn = "2" + StringUtils.generateNum(15);
        order.setOrderSn(orderSn);
        order.setLogistics(2L);
        order.setCreateDate(new Date());
        //获取当前用户id
        User user = RedisConsant.getUser(stringRedisTemplate, token);
        order.setUserId(user.getUserId());
        //设置地址信息
        List<AddressUserVo> addressUserVos = addressService.queryCurrentUserAddress(token).stream().filter(addr -> addr.getDefaultAddress() == 1).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(addressUserVos)) {
            Address address = addressService.selectAddressByAddressId(addressUserVos.get(0).getAddressId());
            order.setConsignName(address.getUserName());
            order.setConsignPhone(address.getPhone());
            fare = address.getFare();
            order.setAddress(address.getAddressName() + " " + address.getDescAddress());
            order.setTotalPrice(cartResultVo.getPayPrice());
            //TODO 没有做优惠管理，所以最终支付价格就是总价格
            order.setPayPrice(cartResultVo.getPayPrice());
        }
        //设置每个订单项
        //List<Product> products = productService.selectProductList(null);
        List<Skuinfo> products = skuinfoService.selectSkuinfoList(null);
        BigDecimal finalFare = fare;
        for (CartResultShopItemVo shop : cartResultVo.getShops()) {
            //设置商品信息
            List<OrderItem> orderItems = shop.getProducts().stream().map(prod -> {
                for (Skuinfo product : products) {
                    if (prod.getProductId().equals(product.getSkuId())) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setProductId(product.getSkuId());
                        orderItem.setShopId(shop.getShopId());
                        orderItem.setProductCount(prod.getProductCount());
                        orderItem.setOrderSn(orderSn);
                        orderItem.setFare(StringUtils.isNotNull(finalFare) ? finalFare : new BigDecimal("0"));
                        orderItem.setSubtotal(prod.getSubtotal());
                        return orderItem;
                    }
                }
                return null;
            }).collect(Collectors.toList());
            //保存每一项订单项
            orderItemService.batchInsertOrderItems(orderItems);
        }

        return order;
    }
}
