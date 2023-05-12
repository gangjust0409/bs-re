package com.ruoyi.bsapi.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.domain.ShopProduct;
import com.ruoyi.bsapi.service.IProductService;
import com.ruoyi.bsapi.service.IShopProductService;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.ShopReleaseProductItemVo;
import com.ruoyi.bsapi.vo.ShopReleaseProductVo;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.my.FileUtil;
import com.ruoyi.common.utils.my.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.ShopMapper;
import com.ruoyi.bsapi.domain.Shop;
import com.ruoyi.bsapi.service.IShopService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 商店Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class ShopServiceImpl implements IShopService 
{
    @Autowired
    private ShopMapper shopMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IShopProductService shopProductService;

    @Autowired
    private IProductService productService;

    /**
     * 查询商店
     * 
     * @param shopId 商店主键
     * @return 商店
     */
    @Override
    public Shop selectShopByShopId(Long shopId)
    {
        Shop shop = shopMapper.selectShopByShopId(shopId);
        if (StringUtils.isNotNull(shop)&&StringUtils.isNotNull(shop.getShopPic())) {
            shop.setShopPic(com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + shop.getShopPic());
        }
        return shop;
    }

    /**
     * 查询商店列表
     * 
     * @param shop 商店
     * @return 商店
     */
    @Override
    public List<Shop> selectShopList(Shop shop)
    {
        return shopMapper.selectShopList(shop);
    }

    /**
     * 新增商店
     * 
     * @param shop 商店
     * @return 结果
     */
    @Override
    public int insertShop(Shop shop)
    {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //设置图标
        String newName = stringRedisTemplate.opsForValue().get(RedisConsant.UPLOAD_SHOP_ICON);
        shop.setShopPic(newName);
        shop.setCreateDate(new Date());
        stringRedisTemplate.delete(RedisConsant.UPLOAD_SHOP_ICON);
        return shopMapper.insertShop(shop);
    }

    /**
     * 修改商店
     * 
     * @param shop 商店
     * @return 结果
     */
    @Override
    public int updateShop(Shop shop) {
        //获取最新的文件
        String newName = stringRedisTemplate.opsForValue().get(RedisConsant.UPLOAD_SHOP_ICON);
        //如果没有上传文件则不修改原来的数据
        if (StringUtils.isNotNull(newName)) {
            shop.setShopPic(newName);
        }
        stringRedisTemplate.delete(RedisConsant.UPLOAD_SHOP_ICON);
        return shopMapper.updateShop(shop);
    }

    /**
     * 批量删除商店
     * 
     * @param shopIds 需要删除的商店主键
     * @return 结果
     */
    @Override
    public int deleteShopByShopIds(Long[] shopIds)
    {
        return shopMapper.deleteShopByShopIds(shopIds);
    }

    /**
     * 删除商店信息
     * 
     * @param shopId 商店主键
     * @return 结果
     */
    @Override
    public int deleteShopByShopId(Long shopId)
    {
        return shopMapper.deleteShopByShopId(shopId);
    }

    @Override
    public Shop getShopById(Long shopId) {
        return this.selectShopByShopId(shopId);
    }

    @Override
    public void uploadShopIcon(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                //获取源文件名称
                String originalFilename = file.getOriginalFilename();
                //获取源文件名称后缀
                String prefix = FileUtil.getPrefix(originalFilename);
                //得到最新的文件名
                String newName = StringUtils.randomUUID() + "." + prefix;
                //放到 redis
                stringRedisTemplate.opsForValue().setIfAbsent(RedisConsant.UPLOAD_SHOP_ICON, newName, 5L, TimeUnit.HOURS);
                InputStream is = file.getInputStream();
                //保存文件
                File dirFile = new File(RuoYiConfig.getProfile(), newName);
                FileUtil.transformTo(is, dirFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ShopReleaseProductVo> shopsAndShopReleaseProducts(String shopName) {
        Shop shopParams = new Shop();
        shopParams.setShopName(shopName);
        startPage();
        //查询
        List<ShopReleaseProductVo> shopReleaseProductVos = shopMapper.selectShopList(shopParams).stream()
                .map(shop -> {
                    ShopReleaseProductVo shopReleaseProductVo = new ShopReleaseProductVo();
                    shopReleaseProductVo.setShopId(shop.getShopId());
                    shopReleaseProductVo.setShopName(shop.getShopName());
                    shopReleaseProductVo.setShopIcon(com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD +shop.getShopPic());
                    shopReleaseProductVo.setProducts(this.getShopReleaseProducts(shop.getShopId()));
                    shopReleaseProductVo.setCreatedDate(shop.getCreateDate());
                    return shopReleaseProductVo;
                }).collect(Collectors.toList());
        return shopReleaseProductVos;
    }

    /**
     * 获取当前店铺的所有的商品信息
     * @param shopId
     * @return
     */
    private List<ShopReleaseProductItemVo> getShopReleaseProducts(Long shopId) {
        //查询商品id
        List<Product> products = productService.selectProductList(null);
        //查询关联
        ShopProduct shopProductParams = new ShopProduct();
        shopProductParams.setShopId(shopId);
        List<ShopReleaseProductItemVo> shopProducts = shopProductService.selectShopProductList(shopProductParams).stream()
                .map(shopProduct -> {
                    for (Product product : products) {
                        ShopReleaseProductItemVo shopReleaseProductItemVo = new ShopReleaseProductItemVo();
                        if (product.getProductId().equals(shopProduct.getProductId())) {
                            shopReleaseProductItemVo.setProductId(product.getProductId());
                            shopReleaseProductItemVo.setProductTitle(product.getProductTitle());
                            shopReleaseProductItemVo.setProductPrice(product.getProductPrice());
                            shopReleaseProductItemVo.setProductPic(product.getProductPic());
                            shopReleaseProductItemVo.setStatus(product.getStatus());
                            return shopReleaseProductItemVo;
                        }
                    }
                    return null;
                }).collect(Collectors.toList());
        return shopProducts;
    }

    @Override
    public int selectCount(String shopName) {
        return shopMapper.selectCount(shopName);
    }
}
