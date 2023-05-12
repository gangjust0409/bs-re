package com.ruoyi.bsapi.to;

/**
 * 添加商品时，每一个属性
 */
public class ProductAttrItemTo {

    /**
     * attr  Id
     */
    private Long attrId;

    /**
     * 属性值
     */
    private String attrValue;

    /**
     * 是否选中
     */
    private boolean checked;

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
