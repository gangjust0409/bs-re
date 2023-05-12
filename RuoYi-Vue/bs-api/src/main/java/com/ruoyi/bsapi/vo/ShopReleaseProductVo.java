package com.ruoyi.bsapi.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 店铺和店铺关联的商品信息
 */
public class ShopReleaseProductVo {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品图标
     */
    private String shopIcon;

    /**
     * 创建店铺的日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    /**
     * 关联商品列表
     */
    private List<ShopReleaseProductItemVo> products;

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

    public String getShopIcon() {
        return shopIcon;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public List<ShopReleaseProductItemVo> getProducts() {
        return products;
    }

    public void setProducts(List<ShopReleaseProductItemVo> products) {
        this.products = products;
    }
}
