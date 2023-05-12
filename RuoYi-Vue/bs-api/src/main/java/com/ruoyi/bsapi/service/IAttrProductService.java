package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.AttrProduct;
import com.ruoyi.bsapi.domain.Attrs;
import com.ruoyi.bsapi.vo.ProductAttrItemVo;

/**
 * 属性和商品关联Service接口
 * 
 * @author lqg
 * @date 2023-03-17
 */
public interface IAttrProductService 
{
    /**
     * 查询属性和商品关联
     * 
     * @param attrProductId 属性和商品关联主键
     * @return 属性和商品关联
     */
    public AttrProduct selectAttrProductByAttrProductId(Long attrProductId);

    /**
     * 查询属性和商品关联列表
     * 
     * @param attrProduct 属性和商品关联
     * @return 属性和商品关联集合
     */
    public List<AttrProduct> selectAttrProductList(AttrProduct attrProduct);

    /**
     * 新增属性和商品关联
     * 
     * @param attrProduct 属性和商品关联
     * @return 结果
     */
    public int insertAttrProduct(AttrProduct attrProduct);

    /**
     * 修改属性和商品关联
     * 
     * @param attrProduct 属性和商品关联
     * @return 结果
     */
    public int updateAttrProduct(AttrProduct attrProduct);

    /**
     * 批量删除属性和商品关联
     * 
     * @param attrProductIds 需要删除的属性和商品关联主键集合
     * @return 结果
     */
    public int deleteAttrProductByAttrProductIds(Long[] attrProductIds);

    /**
     * 删除属性和商品关联信息
     * 
     * @param attrProductId 属性和商品关联主键
     * @return 结果
     */
    public int deleteAttrProductByAttrProductId(Long attrProductId);

    /**
     * 保存属性
     * @param productId
     * @param catelogId
     */
    void saveAttrs(Long productId, Long catelogId);

    /**
     * 根据商品id，获取属性
     * @param productId
     * @return
     */
    List<Attrs> getAttrsReleaseProduct(Long productId);

    /**
     * 根据商品id删除属性关联
     * @param productId
     */
    void deleteProductId(Long productId);

    /**
     * 详情页展示属性选择
     * @param productId
     */
    List<ProductAttrItemVo> selectAttrsByGroupByProductId(Long productId);
}
