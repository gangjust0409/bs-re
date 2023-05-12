package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.Attrs;
import com.ruoyi.bsapi.vo.AttrProductGroupByVo;
import com.ruoyi.bsapi.vo.ProductAttrItemVo;
import org.apache.ibatis.annotations.Param;

/**
 * 属性Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface AttrsMapper 
{
    /**
     * 查询属性
     * 
     * @param attrId 属性主键
     * @return 属性
     */
    public Attrs selectAttrsByAttrId(Long attrId);

    /**
     * 查询属性列表
     * 
     * @param attrs 属性
     * @return 属性集合
     */
    public List<Attrs> selectAttrsList(Attrs attrs);

    /**
     * 新增属性
     * 
     * @param attrs 属性
     * @return 结果
     */
    public int insertAttrs(Attrs attrs);

    /**
     * 修改属性
     * 
     * @param attrs 属性
     * @return 结果
     */
    public int updateAttrs(Attrs attrs);

    /**
     * 删除属性
     * 
     * @param attrId 属性主键
     * @return 结果
     */
    public int deleteAttrsByAttrId(Long attrId);

    /**
     * 批量删除属性
     * 
     * @param attrIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttrsByAttrIds(Long[] attrIds);

    /**
     * 新增商品时，展示属性
     * @return
     */
    List<ProductAttrItemVo> selectAttrsGroupByAttrName();

    //新增商品时，展示属性
    List<ProductAttrItemVo> selectAttrsGroupByAttrNameByCatelogId(@Param("catelogId") Long catelogId);

    /**
     * 查询属性信息
     * @param attrIds
     * @return
     */
    List<AttrProductGroupByVo> selectAttrsByGroupByProductId(@Param("attrIds") List<Long> attrIds);
}
