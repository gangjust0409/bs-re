package com.ruoyi.bsapi.vo;

/**
 * 将商品添加到购物车时，传输的对象模型
 */
public class AddProductCartVo {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * sku id
     */
    private Long skuId;

    /**
     * 数量
     */
    private Long count;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
