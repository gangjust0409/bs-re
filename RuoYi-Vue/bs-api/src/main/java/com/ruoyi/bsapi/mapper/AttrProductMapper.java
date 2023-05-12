package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.AttrProduct;
import org.apache.ibatis.annotations.Param;

/**
 * 属性和商品关联Mapper接口
 * 
 * @author lqg
 * @date 2023-03-17
 */
public interface AttrProductMapper 
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
     * 删除属性和商品关联
     * 
     * @param attrProductId 属性和商品关联主键
     * @return 结果
     */
    public int deleteAttrProductByAttrProductId(Long attrProductId);

    /**
     * 批量删除属性和商品关联
     * 
     * @param attrProductIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttrProductByAttrProductIds(Long[] attrProductIds);

    /**
     * 根据商品id删除属性关联
     * @param productId
     */
    void deleteProductId(@Param("productId") Long productId);
}
