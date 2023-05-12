package com.ruoyi.bsapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 详情图片对象 product_detail
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class ProductDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long productDetailId;

    /** 详情id */
    @Excel(name = "详情id")
    private String productDetailUrl;

    /** 关联的商品 id */
    @Excel(name = "关联的商品 id")
    private Long productId;

    public void setProductDetailId(Long productDetailId) 
    {
        this.productDetailId = productDetailId;
    }

    public Long getProductDetailId() 
    {
        return productDetailId;
    }
    public void setProductDetailUrl(String productDetailUrl) 
    {
        this.productDetailUrl = productDetailUrl;
    }

    public String getProductDetailUrl() 
    {
        return productDetailUrl;
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
            .append("productDetailId", getProductDetailId())
            .append("productDetailUrl", getProductDetailUrl())
            .append("productId", getProductId())
            .toString();
    }
}
