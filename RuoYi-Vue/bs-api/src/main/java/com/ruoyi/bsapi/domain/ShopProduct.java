package com.ruoyi.bsapi.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 店铺的商品对象 shop_product
 * 
 * @author lqg
 * @date 2023-03-26
 */
public class ShopProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long shopProductId;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    /** 关联日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "关联日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseDate;

    public void setShopProductId(Long shopProductId) 
    {
        this.shopProductId = shopProductId;
    }

    public Long getShopProductId() 
    {
        return shopProductId;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setReleaseDate(Date releaseDate) 
    {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate() 
    {
        return releaseDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("shopProductId", getShopProductId())
            .append("shopId", getShopId())
            .append("productId", getProductId())
            .append("releaseDate", getReleaseDate())
            .toString();
    }
}
