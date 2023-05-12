package com.ruoyi.bsapi.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 order
 * 
 * @author lqg
 * @date 2023-04-07
 */
public class Order extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long orderId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderSn;

    /** 收货人名称 */
    @Excel(name = "收货人名称")
    private String consignName;

    /** 收货人联系电话 */
    @Excel(name = "收货人联系电话")
    private String consignPhone;

    /** 送货到哪个地址 */
    @Excel(name = "送货到哪个地址")
    private String address;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long shopId;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private String shopName;

    /** 所有商品总价格 */
    @Excel(name = "所有商品总价格")
    private BigDecimal totalPrice;

    /** 运费 */
    @Excel(name = "运费")
    private BigDecimal fare;

    /** 最终支付的价格 */
    @Excel(name = "最终支付的价格")
    private BigDecimal payPrice;

    /** 支付方式 1 支付宝 2 本地支付 ... */
    @Excel(name = "支付方式 1 支付宝 2 本地支付 ...")
    private Long payStatus;

    /** 物流状态 1 待付款 2 待发货 3 待收货 4 已完成 */
    @Excel(name = "物流状态 1 待付款 2 待发货 3 待收货 4 已完成")
    private Long logistics;

    /** 订单创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订单创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    @Excel(name = "用户id")
    private Long userId;

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setOrderSn(String orderSn) 
    {
        this.orderSn = orderSn;
    }

    public String getOrderSn() 
    {
        return orderSn;
    }
    public void setConsignName(String consignName) 
    {
        this.consignName = consignName;
    }

    public String getConsignName() 
    {
        return consignName;
    }
    public void setConsignPhone(String consignPhone) 
    {
        this.consignPhone = consignPhone;
    }

    public String getConsignPhone() 
    {
        return consignPhone;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setShopName(String shopName) 
    {
        this.shopName = shopName;
    }

    public String getShopName() 
    {
        return shopName;
    }
    public void setTotalPrice(BigDecimal totalPrice) 
    {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() 
    {
        return totalPrice;
    }
    public void setFare(BigDecimal fare) 
    {
        this.fare = fare;
    }

    public BigDecimal getFare() 
    {
        return fare;
    }
    public void setPayPrice(BigDecimal payPrice) 
    {
        this.payPrice = payPrice;
    }

    public BigDecimal getPayPrice() 
    {
        return payPrice;
    }
    public void setPayStatus(Long payStatus) 
    {
        this.payStatus = payStatus;
    }

    public Long getPayStatus() 
    {
        return payStatus;
    }
    public void setLogistics(Long logistics) 
    {
        this.logistics = logistics;
    }

    public Long getLogistics() 
    {
        return logistics;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("orderSn", getOrderSn())
            .append("consignName", getConsignName())
            .append("consignPhone", getConsignPhone())
            .append("address", getAddress())
            .append("shopId", getShopId())
            .append("shopName", getShopName())
            .append("totalPrice", getTotalPrice())
            .append("fare", getFare())
            .append("payPrice", getPayPrice())
            .append("payStatus", getPayStatus())
            .append("logistics", getLogistics())
            .append("createDate", getCreateDate())
            .toString();
    }
}
