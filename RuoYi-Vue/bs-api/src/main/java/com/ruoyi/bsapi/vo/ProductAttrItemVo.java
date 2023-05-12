package com.ruoyi.bsapi.vo;

import com.ruoyi.bsapi.domain.Attrs;
import com.ruoyi.bsapi.to.ProductAttrItemTo;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表新增商品时，展示的属性列表
 */
public class ProductAttrItemVo {

    /**
     * 属性id
     */
    private Long attrId;

    /**
     * 属性名称
     */
    private String title;

    /**
     * 是否全选
     */
    private boolean ifAll;

    /**
     * 所有的属性值（字符串版）
     */
    private String attrValuesWithStr;

    /**
     * 所有的属性值
     */
    private List<ProductAttrItemTo> attrValues = new ArrayList<>();

    private boolean ifIndeterminate;

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIfAll() {
        return ifAll;
    }

    public void setIfAll(boolean ifAll) {
        this.ifAll = ifAll;
    }

    public String getAttrValuesWithStr() {
        return attrValuesWithStr;
    }

    public void setAttrValuesWithStr(String attrValuesWithStr) {
        this.attrValuesWithStr = attrValuesWithStr;
    }

    public List<ProductAttrItemTo> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(List<ProductAttrItemTo> attrValues) {
        this.attrValues = attrValues;
    }

    public boolean isIfIndeterminate() {
        return ifIndeterminate;
    }

    public void setIfIndeterminate(boolean ifIndeterminate) {
        this.ifIndeterminate = ifIndeterminate;
    }
}
