package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.ShopProduct;

/**
 * 店铺的商品Mapper接口
 * 
 * @author lqg
 * @date 2023-03-26
 */
public interface ShopProductMapper 
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
     * @param shopProduct 店铺的商品
     * @return 结果
     */
    public int insertShopProduct(ShopProduct shopProduct);

    /**
     * 修改店铺的商品
     * 
     * @param shopProduct 店铺的商品
     * @return 结果
     */
    public int updateShopProduct(ShopProduct shopProduct);

    /**
     * 删除店铺的商品
     * 
     * @param shopProductId 店铺的商品主键
     * @return 结果
     */
    public int deleteShopProductByShopProductId(Long shopProductId);

    /**
     * 批量删除店铺的商品
     * 
     * @param shopProductIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopProductByShopProductIds(Long[] shopProductIds);
}
