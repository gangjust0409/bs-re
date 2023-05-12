package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.ProductDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 详情图片Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface ProductDetailMapper 
{
    /**
     * 查询详情图片
     * 
     * @param productDetailId 详情图片主键
     * @return 详情图片
     */
    public ProductDetail selectProductDetailByProductDetailId(Long productDetailId);

    /**
     * 查询详情图片列表
     * 
     * @param productDetail 详情图片
     * @return 详情图片集合
     */
    public List<ProductDetail> selectProductDetailList(ProductDetail productDetail);

    /**
     * 新增详情图片
     * 
     * @param productDetail 详情图片
     * @return 结果
     */
    public int insertProductDetail(ProductDetail productDetail);

    /**
     * 修改详情图片
     * 
     * @param productDetail 详情图片
     * @return 结果
     */
    public int updateProductDetail(ProductDetail productDetail);

    /**
     * 删除详情图片
     * 
     * @param productDetailId 详情图片主键
     * @return 结果
     */
    public int deleteProductDetailByProductDetailId(Long productDetailId);

    /**
     * 批量删除详情图片
     * 
     * @param productDetailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductDetailByProductDetailIds(Long[] productDetailIds);

    /**
     * 根据商品id删除详情图片
     * @param productId
     */
    void deleteProductId(@Param("productId") Long productId);

    /**
     * 修改数据库的记录
     * @param img
     */
    void deleteProductImgUrl(@Param("img") String img);
}
