package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.ShopProduct;

/**
 * 店铺的商品Service接口
 * 
 * @author lqg
 * @date 2023-03-26
 */
public interface IShopProductService 
{
    /**
     * 查询店铺的商品
     * 
     * @param shopProductId 店铺的商品主键
     * @return 店铺的商品
     */
    public ShopProduct selectShopProductByShopProductId(Long shopProductId);

    /**
     * 查询店铺的商品列表
     * 
     * @param shopProduct 店铺的商品
     * @return 店铺的商品集合
     */
    public List<ShopProduct> selectShopProductList(ShopProduct shopProduct);

    /**
     * 新增店铺的商品
     *
     * @param status
     * @param shopProduct 店铺的商品
     * @return 结果
     */
    public int insertShopProduct(Long status, ShopProduct shopProduct);

    /**
     * 修改店铺的商品
     * 
     * @param shopProduct 店铺的商品
     * @return 结果
     */
    public int updateShopProduct(ShopProduct shopProduct);

    /**
     * 批量删除店铺的商品
     * 
     * @param shopProductIds 需要删除的店铺的商品主键集合
     * @return 结果
     */
    public int deleteShopProductByShopProductIds(Long[] shopProductIds);

    /**
     * 删除店铺的商品信息
     * 
     * @param shopProductId 店铺的商品主键
     * @return 结果
     */
    public int deleteShopProductByShopProductId(Long shopProductId);

    /**
     * 根据商品id删除关联店铺
     * @param productId
     */
    void deleteProductId(Long productId);
}
