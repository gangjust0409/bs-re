package com.ruoyi.bsapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图对象 swiper
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class Swiper extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long swiperId;

    /** 关联商品id */
    @Excel(name = "关联商品id")
    private Long productId;

    /** 轮播图 url */
    @Excel(name = "轮播图 url")
    private String swiperUrl;

    /** 是否启用，1启用 2 禁用 */
    @Excel(name = "是否启用，1启用 2 禁用")
    private Long enable;

    public void setSwiperId(Long swiperId) 
    {
        this.swiperId = swiperId;
    }

    public Long getSwiperId() 
    {
        return swiperId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setSwiperUrl(String swiperUrl) 
    {
        this.swiperUrl = swiperUrl;
    }

    public String getSwiperUrl() 
    {
        return swiperUrl;
    }
    public void setEnable(Long enable) 
    {
        this.enable = enable;
    }

    public Long getEnable() 
    {
        return enable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("swiperId", getSwiperId())
            .append("productId", getProductId())
            .append("swiperUrl", getSwiperUrl())
            .append("enable", getEnable())
            .toString();
    }
}
