package com.ruoyi.bsapi.vo;

import com.ruoyi.bsapi.domain.Attrs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 返回订单条数
 */
public class OrderResp {

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 商品总价格
     */
    private BigDecimal totalPrice;

    /**
     * 状态
     */
    private Long status;

    /**
     * 运费
     */
    private BigDecimal fare;

    /**
     * 订单项列表
     */
    private List<OrderItemVo> items;

    /**
     * 时间
     */
    private String time;

    private Long m;

    private Long s;

    public Long getM() {
        return m;
    }

    public void setM(Long m) {
        this.m = m;
    }

    public Long getS() {
        return s;
    }

    public void setS(Long s) {
        this.s = s;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<OrderItemVo> getItems() {
        return items;
    }

    public void setItems(List<OrderItemVo> items) {
        this.items = items;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //订单中的每一项
    public static class OrderItemVo {
        /**
         * 商品id
         */
        private Long productId;
        /**
         * 商品名称
         */
        private String productTitle;
        /**
         * 商品封面
         */
        private String productPic;
        /**
         * 商品属性
         */
        private List<Attrs> attrs;
        /**
         * 商品单价
         */
        private BigDecimal productPrice;
        /**
         * 商品数量
         */
        private Long productCount;

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

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
        }

        public List<Attrs> getAttrs() {
            return attrs;
        }

        public void setAttrs(List<Attrs> attrs) {
            this.attrs = attrs;
        }

        public BigDecimal getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(BigDecimal productPrice) {
            this.productPrice = productPrice;
        }

        public Long getProductCount() {
            return productCount;
        }

        public void setProductCount(Long productCount) {
            this.productCount = productCount;
        }
    }

}
