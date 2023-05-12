package com.ruoyi.bsapi.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.bsapi.domain.Product;
import org.apache.ibatis.annotations.Param;

/**
 * 商品Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface ProductMapper 
{
    /**
     * 查询商品
     * 
     * @param productId 商品主键
     * @return 商品
     */
    public Product selectProductByProductId(Long productId);

    /**
     * 查询商品列表
     * 
     * @param product 商品
     * @return 商品集合
     */
    public List<Product> selectProductList(Product product);

    /**
     * 新增商品
     * 
     * @param product 商品
     * @return 结果
     */
    public int insertProduct(Product product);

    /**
     * 修改商品
     * 
     * @param product 商品
     * @return 结果
     */
    public int updateProduct(Product product);

    /**
     * 删除商品
     * 
     * @param productId 商品主键
     * @return 结果
     */
    public int deleteProductByProductId(Long productId);

    /**
     * 批量删除商品
     * 
     * @param productIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductByProductIds(Long[] productIds);

    /**
     * 查询全部商品的总条数
     * @param productTitle
     * @param status
     * @return
     */
    Long selectCount(@Param("productTitle") String productTitle, @Param("status") Long status);

    /**
     * 点击删除修改时的图片
     * @param img
     */
    void deleteProductImgUrl(@Param("img") String img);

    List<Product> queryProductList(@Param("params") Product params, @Param("catelogIds") List<Long> catelogIds, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
}
