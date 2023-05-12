package com.ruoyi.bsapi.vo;

import com.ruoyi.bsapi.domain.Attrs;

import java.math.BigDecimal;
import java.util.List;

/**
 * 结算页中店铺列表的每一页
 */
public class CartResultShopItemVo {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品列表
     */
    private List<CartResultProductItemVo> products;

    /**
     * 运费
     */
    private BigDecimal fare;

    /**
     * 合计
     */
    private BigDecimal totalMoney;

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

    public List<CartResultProductItemVo> getProducts() {
        return products;
    }

    public void setProducts(List<CartResultProductItemVo> products) {
        this.products = products;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "CartResultShopItemVo{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", products=" + products +
                ", fare=" + fare +
                ", totalMoney=" + totalMoney +
                '}';
    }

    /**
     * 每一个商品项
     */
    public static  class CartResultProductItemVo {
        /**
         * 商品价格
         */
        private Long productId;

        /**
         * 商品标题
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

        /**‘
         *小计
         */
        private BigDecimal subtotal;


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

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }

        @Override
        public String toString() {
            return "CartResultProductItemVo{" +
                    "productId=" + productId +
                    ", productTitle='" + productTitle + '\'' +
                    ", productPic='" + productPic + '\'' +
                    ", attrs=" + attrs +
                    ", productPrice=" + productPrice +
                    ", productCount=" + productCount +
                    ", subtotal=" + subtotal +
                    '}';
        }
    }

}
