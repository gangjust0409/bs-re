package com.ruoyi.bsapi.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 结算页模型
 */
public class CartResultVo {

    /**
     * 商品信息
     */
    private List<CartResultShopItemVo> shops;

    /**
     * 最终支付价格
     */
    private BigDecimal payPrice;

    /**
     * 支付方式
     */
    private Long payType;

    public List<CartResultShopItemVo> getShops() {
        return shops;
    }

    public void setShops(List<CartResultShopItemVo> shops) {
        this.shops = shops;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public Long getPayType() {
        return payType;
    }

    public void setPayType(Long payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "CartResultVo{" +
                "shops=" + shops +
                ", payPrice=" + payPrice +
                ", payType=" + payType +
                '}';
    }
}
