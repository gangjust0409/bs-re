package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.ProductThumbnail;

/**
 * 缩略图Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface IProductThumbnailService 
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
     * 批量删除缩略图
     * 
     * @param productThumbnailIds 需要删除的缩略图主键集合
     * @return 结果
     */
    public int deleteProductThumbnailByProductThumbnailIds(Long[] productThumbnailIds);

    /**
     * 删除缩略图信息
     * 
     * @param productThumbnailId 缩略图主键
     * @return 结果
     */
    public int deleteProductThumbnailByProductThumbnailId(Long productThumbnailId);

    /**
     * 保存商品缩略图
     * @param productId
     */
    void saveProductThumbnailImgs(Long productId);

    /**
     * 根据商品id，删除缩略图
     * @param productId
     */
    void deleteProductId(Long productId);

    /**
     * 点击删除修改时的图片
     * @param img
     */
    void deleteProductImgUrl(String img);
}
