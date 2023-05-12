package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.ProductDetail;

/**
 * 详情图片Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface IProductDetailService 
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
     * 批量删除详情图片
     * 
     * @param productDetailIds 需要删除的详情图片主键集合
     * @return 结果
     */
    public int deleteProductDetailByProductDetailIds(Long[] productDetailIds);

    /**
     * 删除详情图片信息
     * 
     * @param productDetailId 详情图片主键
     * @return 结果
     */
    public int deleteProductDetailByProductDetailId(Long productDetailId);

    /**
     * 保存 商品详情图
     * @param productId
     */
    void saveProductDetailImgs(Long productId);

    /**
     * 根据商品id删除
     * @param productId
     */
    void deleteProductId(Long productId);

    /**
     * 点击删除修改时的图片
     * @param img
     */
    void deleteProductImgUrl(String img);
}
