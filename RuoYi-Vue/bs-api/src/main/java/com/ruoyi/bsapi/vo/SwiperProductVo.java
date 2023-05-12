package com.ruoyi.bsapi.vo;

/**
 * 轮播图和商品模型
 */
public class SwiperProductVo {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品标题
     */
    private String productTitle;

    /**
     * 轮播图id
     */
    private Long swiperId;

    /**
     * 轮播图路径
     */
    private String swiperUrl;

    /**
     * 是否启用
     */
    private Long enable;

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

    public Long getSwiperId() {
        return swiperId;
    }

    public void setSwiperId(Long swiperId) {
        this.swiperId = swiperId;
    }

    public String getSwiperUrl() {
        return swiperUrl;
    }

    public void setSwiperUrl(String swiperUrl) {
        this.swiperUrl = swiperUrl;
    }

    public Long getEnable() {
        return enable;
    }

    public void setEnable(Long enable) {
        this.enable = enable;
    }
}
