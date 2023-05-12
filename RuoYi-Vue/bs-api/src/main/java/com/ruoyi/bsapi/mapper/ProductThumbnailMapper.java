package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.ProductThumbnail;
import org.apache.ibatis.annotations.Param;

/**
 * 缩略图Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface ProductThumbnailMapper 
{
    /**
     * 查询缩略图
     * 
     * @param productThumbnailId 缩略图主键
     * @return 缩略图
     */
    public ProductThumbnail selectProductThumbnailByProductThumbnailId(Long productThumbnailId);

    /**
     * 查询缩略图列表
     * 
     * @param productThumbnail 缩略图
     * @return 缩略图集合
     */
    public List<ProductThumbnail> selectProductThumbnailList(ProductThumbnail productThumbnail);

    /**
     * 新增缩略图
     * 
     * @param productThumbnail 缩略图
     * @return 结果
     */
    public int insertProductThumbnail(ProductThumbnail productThumbnail);

    /**
     * 修改缩略图
     * 
     * @param productThumbnail 缩略图
     * @return 结果
     */
    public int updateProductThumbnail(ProductThumbnail productThumbnail);

    /**
     * 删除缩略图
     * 
     * @param productThumbnailId 缩略图主键
     * @return 结果
     */
    public int deleteProductThumbnailByProductThumbnailId(Long productThumbnailId);

    /**
     * 批量删除缩略图
     * 
     * @param productThumbnailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductThumbnailByProductThumbnailIds(Long[] productThumbnailIds);

    /**
     * 根据商品id，删除缩略图
     * @param productId
     */
    void deleteProductId(@Param("productId") Long productId);

    /**
     * 删除数据库的记录
     * @param img
     */
    void deleteProductImgUrl(@Param("img") String img);
}
