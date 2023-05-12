package com.ruoyi.bsapi.vo;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 新增商品模型
 */
public class AddProductVo {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productTitle;
    /**
     * 商品价格
     */
    private BigDecimal productPrice;
    /**
     * 商品封面
     */
    private String productPic;
    /**
     * 分类 id
     */
    private Long catelogId;
    /**
     * 商品详情地址
     */
    private String productDetailUrl;
    /**
     * 商品缩略图地址
     */
    private String productThumbnailUrl;
    /**
     * sku 信息
     */
    private AddProductItem[] skus;

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

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public Long getCatelogId() {
        return catelogId;
    }

    public void setCatelogId(Long catelogId) {
        this.catelogId = catelogId;
    }

    public String getProductDetailUrl() {
        return productDetailUrl;
    }

    public void setProductDetailUrl(String productDetailUrl) {
        this.productDetailUrl = productDetailUrl;
    }

    public String getProductThumbnailUrl() {
        return productThumbnailUrl;
    }

    public void setProductThumbnailUrl(String productThumbnailUrl) {
        this.productThumbnailUrl = productThumbnailUrl;
    }

    public AddProductItem[] getSkus() {
        return skus;
    }

    public void setSkus(AddProductItem[] skus) {
        this.skus = skus;
    }

    @Override
    public String toString() {
        return "AddProductVo{" +
                "productTitle='" + productTitle + '\'' +
                ", productPrice=" + productPrice +
                ", productPic='" + productPic + '\'' +
                ", catelogId=" + catelogId +
                ", productDetailUrl='" + productDetailUrl + '\'' +
                ", productThumbnailUrl='" + productThumbnailUrl + '\'' +
                ", skus=" + Arrays.toString(skus) +
                '}';
    }

    /**
     * sku info
     */
    public static class AddProductItem {
        /**
         * sku id
         */
        private Long id;
        /**
         * 文本
         */
        private String title;
        /**
         * sku 名称
         */
        private String skuName;
        /**
         * sku 价格
         */
        private BigDecimal skuPrice;

        private String skuPic;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public BigDecimal getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(BigDecimal skuPrice) {
            this.skuPrice = skuPrice;
        }

        public String getSkuPic() {
            return skuPic;
        }

        public void setSkuPic(String skuPic) {
            this.skuPic = skuPic;
        }

        @Override
        public String toString() {
            return "AddProductItem{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", skuName='" + skuName + '\'' +
                    ", skuPrice=" + skuPrice +
                    ", skuPic='" + skuPic + '\'' +
                    '}';
        }
    }
}
