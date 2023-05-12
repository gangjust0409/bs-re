package com.ruoyi.bsapi.vo;

/**
 * 查询商品关联属性分组信息
 */
public class AttrProductGroupByVo {

    private String attrName;

    private String attrValues;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues;
    }

    @Override
    public String toString() {
        return "AttrProductGroupByVo{" +
                "attrName='" + attrName + '\'' +
                ", attrValues='" + attrValues + '\'' +
                '}';
    }
}
