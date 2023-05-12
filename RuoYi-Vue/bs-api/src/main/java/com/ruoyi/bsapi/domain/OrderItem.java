package com.ruoyi.bsapi.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 每一项订单对象 order_item
 * 
 * @author lqg
 * @date 2023-04-09
 */
public class OrderItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long orderItemId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderSn;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    /** 数量 */
    @Excel(name = "数量")
    private Long productCount;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    /** 每个店铺不同的运费 */
    @Excel(name = "每个店铺不同的运费")
    private BigDecimal fare;

    /** 商品小计 */
    @Excel(name = "商品小计")
    private BigDecimal subtotal;

    public void setOrderItemId(Long orderItemId) 
    {
        this.orderItemId = orderItemId;
    }

    public Long getOrderItemId() 
    {
        return orderItemId;
    }
    public void setOrderSn(String orderSn) 
    {
        this.orderSn = orderSn;
    }

    public String getOrderSn() 
    {
        return orderSn;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setProductCount(Long productCount) 
    {
        this.productCount = productCount;
    }

    public Long getProductCount() 
    {
        return productCount;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setFare(BigDecimal fare)
    {
        this.fare = fare;
    }

    public BigDecimal getFare()
    {
        return fare;
    }
    public void setSubtotal(BigDecimal subtotal) 
    {
        this.subtotal = subtotal;
    }

    public BigDecimal getSubtotal() 
    {
        return subtotal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderItemId", getOrderItemId())
            .append("orderSn", getOrderSn())
            .append("productId", getProductId())
            .append("productCount", getProductCount())
            .append("shopId", getShopId())
            .append("fare", getFare())
            .append("subtotal", getSubtotal())
            .toString();
    }
}
