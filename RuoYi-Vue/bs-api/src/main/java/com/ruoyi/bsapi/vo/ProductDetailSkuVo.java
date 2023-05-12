package com.ruoyi.bsapi.vo;

import java.math.BigDecimal;

/**
 * 商品详情的 sku部分
 */
public class ProductDetailSkuVo {

    /**
     * sku id
     */
    private Long skuId;

    /**
     * sku name
     */
    private String skuName;

    private BigDecimal skuPrice;

    /**
     * sku 封面
     */
    private String skuPic;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuPic() {
        return skuPic;
    }

    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
    }

    public void setSkuPic(String skuPic) {
        this.skuPic = skuPic;
    }
}
