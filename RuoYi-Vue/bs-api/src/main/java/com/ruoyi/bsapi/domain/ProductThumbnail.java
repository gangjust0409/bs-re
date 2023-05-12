package com.ruoyi.bsapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 缩略图对象 product_thumbnail
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class ProductThumbnail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long productThumbnailId;

    /** 缩略图 url */
    @Excel(name = "缩略图 url")
    private String productThumbnailUrl;

    /** 关联的商品id */
    @Excel(name = "关联的商品id")
    private Long productId;

    public void setProductThumbnailId(Long productThumbnailId) 
    {
        this.productThumbnailId = productThumbnailId;
    }

    public Long getProductThumbnailId() 
    {
        return productThumbnailId;
    }
    public void setProductThumbnailUrl(String productThumbnailUrl) 
    {
        this.productThumbnailUrl = productThumbnailUrl;
    }

    public String getProductThumbnailUrl() 
    {
        return productThumbnailUrl;
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
            .append("productThumbnailId", getProductThumbnailId())
            .append("productThumbnailUrl", getProductThumbnailUrl())
            .append("productId", getProductId())
            .toString();
    }
}
