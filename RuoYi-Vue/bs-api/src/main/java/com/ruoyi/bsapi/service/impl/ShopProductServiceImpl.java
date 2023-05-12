package com.ruoyi.bsapi.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.service.IProductService;
import com.ruoyi.common.enums.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.ShopProductMapper;
import com.ruoyi.bsapi.domain.ShopProduct;
import com.ruoyi.bsapi.service.IShopProductService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * 店铺的商品Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-26
 */
@Service
public class ShopProductServiceImpl implements IShopProductService 
{
    @Autowired
    private ShopProductMapper shopProductMapper;

    @Resource
    private IProductService productService;

    /**
     * 查询店铺的商品
     * 
     * @param shopProductId 店铺的商品主键
     * @return 店铺的商品
     */
    @Override
    public ShopProduct selectShopProductByShopProductId(Long shopProductId)
    {
        return shopProductMapper.selectShopProductByShopProductId(shopProductId);
    }

    /**
     * 查询店铺的商品列表
     * 
     * @param shopProduct 店铺的商品
     * @return 店铺的商品
     */
    @Override
    public List<ShopProduct> selectShopProductList(ShopProduct shopProduct)
    {
        return shopProductMapper.selectShopProductList(shopProduct);
    }

    /**
     * 新增店铺的商品
     *
     * @param status
     * @param shopProduct 店铺的商品
     * @return 结果
     */
    @Override
    public int insertShopProduct(Long status, ShopProduct shopProduct)
    {
        //改变商品状态
        Product product = productService.selectProductByProductId(shopProduct.getProductId());
        if (!getProductStatus(status)) {
            return 3;
        }
        product.setStatus(status);
        productService.updateProduct(product);
        if (status == 2) {
            //设置关联时间
            shopProduct.setReleaseDate(new Date());
            shopProductMapper.insertShopProduct(shopProduct);
        } else if(status == 3) {
            ShopProduct shopProductParams = new ShopProduct();
            shopProductParams.setProductId(shopProduct.getProductId());
            List<ShopProduct> shopProducts = shopProductMapper.selectShopProductList(shopProduct);
            if (!CollectionUtils.isEmpty(shopProducts)) {
                ShopProduct shopProduct1 = shopProducts.get(0);
                shopProductMapper.deleteShopProductByShopProductId(shopProduct1.getShopProductId());
            }
        }
        return 1;
    }

    private Boolean getProductStatus(Long status) {
        if (status.equals(ProductStatus.PUT_SHELVES.getId()) || status.equals(ProductStatus.TAKE_OFF_SHELVES.getId())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改店铺的商品
     * 
     * @param shopProduct 店铺的商品
     * @return 结果
     */
    @Override
    public int updateShopProduct(ShopProduct shopProduct)
    {
        return shopProductMapper.updateShopProduct(shopProduct);
    }

    /**
     * 批量删除店铺的商品
     * 
     * @param shopProductIds 需要删除的店铺的商品主键
     * @return 结果
     */
    @Override
    public int deleteShopProductByShopProductIds(Long[] shopProductIds)
    {
        return shopProductMapper.deleteShopProductByShopProductIds(shopProductIds);
    }

    /**
     * 删除店铺的商品信息
     * 
     * @param shopProductId 店铺的商品主键
     * @return 结果
     */
    @Override
    public int deleteShopProductByShopProductId(Long shopProductId)
    {
        return shopProductMapper.deleteShopProductByShopProductId(shopProductId);
    }

    @Override
    public void deleteProductId(Long productId) {

    }
}
