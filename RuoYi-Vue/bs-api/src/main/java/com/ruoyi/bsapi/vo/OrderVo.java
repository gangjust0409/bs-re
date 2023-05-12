package com.ruoyi.bsapi.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 每一个订单
 */
public class OrderVo {

    private String orderSn;

    private String consignName;

    /** 收货人联系电话 */
    private String consignPhone;

    /** 送货到哪个地址 */
    private String address;

    /** 所有商品总价格 */
    private BigDecimal totalPrice;

    /** 运费 */
    private BigDecimal fare;

    /** 最终支付的价格 */
    private BigDecimal payPrice;

    /** 支付方式 1 支付宝 2 本地支付 ... */
    private Long payStatus;

    /** 物流状态 1 待付款 2 待发货 3 待收货 4 已完成 */
    private Long logistics;

    /** 订单创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private List<OrderItemVo> items;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getConsignName() {
        return consignName;
    }

    public void setConsignName(String consignName) {
        this.consignName = consignName;
    }

    public String getConsignPhone() {
        return consignPhone;
    }

    public void setConsignPhone(String consignPhone) {
        this.consignPhone = consignPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public Long getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    public Long getLogistics() {
        return logistics;
    }

    public void setLogistics(Long logistics) {
        this.logistics = logistics;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<OrderItemVo> getItems() {
        return items;
    }

    public void setItems(List<OrderItemVo> items) {
        this.items = items;
    }

    //每一个订单项
    public static class OrderItemVo {

        /** 商品id */
        private Long productId;

        private String productTitle;

        private String productPic;

        /** 数量 */
        private Long productCount;

        private BigDecimal productPrice;

        /** 店铺id */
        private Long shopId;

        /** 店铺名称 */
        private String shopName;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public Long getProductCount() {
            return productCount;
        }

        public void setProductCount(Long productCount) {
            this.productCount = productCount;
        }

        public BigDecimal getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(BigDecimal productPrice) {
            this.productPrice = productPrice;
        }

        public Long getShopId() {
            return shopId;
        }

        public void setShopId(Long shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
        }
    }

}
