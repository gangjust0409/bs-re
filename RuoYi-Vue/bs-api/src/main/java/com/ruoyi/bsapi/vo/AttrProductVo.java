package com.ruoyi.bsapi.vo;

import com.ruoyi.bsapi.domain.Attrs;
import com.ruoyi.bsapi.domain.Catelog;

import java.util.List;

/**
 * 添加时，查询属性和分类
 */
public class AttrProductVo {

    /**
     * 属性列表
     */
    List<ProductAttrItemVo> attrItems;


    public List<ProductAttrItemVo> getAttrItems() {
        return attrItems;
    }

    public void setAttrItems(List<ProductAttrItemVo> attrItems) {
        this.attrItems = attrItems;
    }


}
