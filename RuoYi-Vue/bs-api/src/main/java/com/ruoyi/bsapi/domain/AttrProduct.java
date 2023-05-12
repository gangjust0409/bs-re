package com.ruoyi.bsapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 属性和商品关联对象 attr_product
 * 
 * @author lqg
 * @date 2023-03-17
 */
public class AttrProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long attrProductId;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    /** 属性id */
    @Excel(name = "属性id")
    private Long attrId;

    public void setAttrProductId(Long attrProductId) 
    {
        this.attrProductId = attrProductId;
    }

    public Long getAttrProductId() 
    {
        return attrProductId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setAttrId(Long attrId) 
    {
        this.attrId = attrId;
    }

    public Long getAttrId() 
    {
        return attrId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("attrProductId", getAttrProductId())
            .append("productId", getProductId())
            .append("attrId", getAttrId())
            .toString();
    }
}
