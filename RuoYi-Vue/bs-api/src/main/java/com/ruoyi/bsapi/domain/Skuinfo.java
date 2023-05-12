package com.ruoyi.bsapi.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * sku info对象 skuinfo
 * 
 * @author lqg
 * @date 2023-03-17
 */
public class Skuinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long skuId;

    /** sku名称 */
    @Excel(name = "sku名称")
    private String skuName;

    /** sku 价格 */
    @Excel(name = "sku 价格")
    private BigDecimal skuPrice;

    /** sku 图片 */
    @Excel(name = "sku 图片")
    private String skuPic;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    public void setSkuId(Long skuId) 
    {
        this.skuId = skuId;
    }

    public Long getSkuId() 
    {
        return skuId;
    }
    public void setSkuName(String skuName) 
    {
        this.skuName = skuName;
    }

    public String getSkuName() 
    {
        return skuName;
    }
    public void setSkuPrice(BigDecimal skuPrice) 
    {
        this.skuPrice = skuPrice;
    }

    public BigDecimal getSkuPrice() 
    {
        return skuPrice;
    }
    public void setSkuPic(String skuPic) 
    {
        this.skuPic = skuPic;
    }

    public String getSkuPic() 
    {
        return skuPic;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("skuId", getSkuId())
            .append("skuName", getSkuName())
            .append("skuPrice", getSkuPrice())
            .append("skuPic", getSkuPic())
            .append("productId", getProductId())
            .toString();
    }
}
