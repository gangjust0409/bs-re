package com.ruoyi.web.controller.bsapi.app;

import com.ruoyi.bsapi.domain.Catelog;
import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.domain.Swiper;
import com.ruoyi.bsapi.service.*;
import com.ruoyi.bsapi.vo.*;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.my.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/app/menu")
@RestController
public class AppMenuController {

    @Resource
    private ICatelogService catelogService;

    @Resource
    private ISwiperService swiperService;

    @Resource
    private IProductService productService;

    @Resource
    private IMyHistoryService myHistoryService;

    @Resource
    private IOrderService orderService;

    @GetMapping("/catelog")
    public AjaxResult catelogList() {
        List<Catelog> catelogAppVoList = catelogService.queryCatelogs();
        return AjaxResult.success(catelogAppVoList);
    }

    @GetMapping("/swipers")
    public AjaxResult swiperList(){
        List<Swiper> swipers = swiperService.swiperList();
        return AjaxResult.success(swipers);
    }

    //猜你喜欢
    @GetMapping("/love/products")
    public AjaxResult loveProducts(@RequestHeader(name = "X-Token",required = false) String token) {
        List<Product> products = productService.loveProducts(token);
        return AjaxResult.success(products);
    }

    //详情
    @GetMapping("/detail/{productId}")
    public AjaxResult detail(@RequestHeader(name = "X-Token", required = false) String token, @PathVariable Long productId){
        ProductDetailVo detailVos = productService.menuDetail(productId);
        //记录到我的历史
        if (StringUtils.isNotNull(token)) {
            myHistoryService.saveHistoryProduct(productId, token);
        }
        return AjaxResult.success(detailVos);
    }

    //商品检索
    @GetMapping("/search")
    public AjaxResult searchProduct(SearchProductParams params) {
        List<Product> products = productService.searchProduct(params);
        return AjaxResult.success(products);
    }

    //将商品添加到购物车
    @PostMapping("/add/cart")
    public AjaxResult addProductCart(@RequestHeader("X-Token") String token, @RequestBody AddProductCartVo addProductCartVo){
        productService.addProductCart(token, addProductCartVo);
        return AjaxResult.success();
    }

    //查询购物车中的商品信息
    @GetMapping("/carts")
    public AjaxResult carts(@RequestHeader("X-Token") String token){
        AddCartResp carts = productService.carts(token);
        return AjaxResult.success(carts);
    }

    //选中商品和改变商品数量
    @GetMapping("/change/cart/{shopId}")
    public AjaxResult changeCart(@RequestHeader("X-Token") String token,
                                 @PathVariable Long shopId,
                                 @RequestParam Long productId,
                                 @RequestParam(required = false) Long count,
                                 @RequestParam(required = false) Boolean isChecked){
        productService.changeCart(token, productId, count, isChecked,shopId);
        return AjaxResult.success();
    }
    //全选和选中店铺
    @GetMapping("/checked/all/shop")
    public AjaxResult checkedAllAndShop(@RequestHeader("X-Token") String token,
                                        @RequestParam(required = false) Long shopId,
                                        @RequestParam Boolean checkAll){
        productService.checkedAllAndShop(token, shopId, checkAll);
        return AjaxResult.success();
    }

    //删除购物车
    @DeleteMapping("/del/cart")
    public AjaxResult delCart(@RequestHeader("X-Token") String token,@RequestParam(required = false) Long cartId){
        productService.delCart(token,cartId);
        return AjaxResult.success();
    }

    //结算
    //确认订单信息
    //1 地址信息
    //2 商品信息
    //3支付方式
    @GetMapping("/cart/result")
    public AjaxResult resultCaCartResultVort(@RequestHeader("X-Token") String token){
        CartResultVo cartResultVos =  productService.resultCart(token);
        return AjaxResult.success(cartResultVos);
    }

    //提交订单
    @RepeatSubmit
    @GetMapping("/submit/order")
    public AjaxResult submitOrder(@RequestHeader("X-Token") String token){
        String time = orderService.submitOrder(token);
        return AjaxResult.success(time);
    }

    //支付、提交并支付
    @GetMapping("/pay/order")
    public AjaxResult payOrder(@RequestHeader("X-Token") String token,
                               @RequestParam Long payType, @RequestParam(required = false) String orderSn){
        try {
            orderService.payOrder(token, payType,orderSn);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    //查看我购买的订单信息
    @GetMapping("/my/order/list")
    public AjaxResult myOrderList(@RequestHeader("X-Token") String token,
                                  @RequestParam(required = false,defaultValue = "0") Integer status,
                                  @RequestParam(required = false) String orderSn){
        List<OrderResp> orders = orderService.myOrderList(token, status,orderSn);
        return AjaxResult.success(orders);
    }

    //删除当前订单
    @DeleteMapping("/delete/order/{orderSn}")
    public AjaxResult deleteOrder(@PathVariable String orderSn){
        orderService.deleteOrder(orderSn);
        return AjaxResult.success();
    }

}
