package com.ruoyi.bsapi.to;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺下的商品
 */
public class AddCartShopItemTo {

    /**
     * 是否全选
     */
    private  boolean ifCheckedAll;

    /**
     * 总数量
     */
    private Long count;

    /**
     * 选中数量
     */
    private Long checkedCount;

    private Boolean isIndeterminate;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 购物车商品列表
     */
    private List<AddCartItemTo> carts;

    public boolean isIfCheckedAll() {
        return count == this.checkedCount;
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

    public List<AddCartItemTo> getCarts() {
        return carts;
    }

    public void setCarts(List<AddCartItemTo> carts) {
        this.carts = carts;
    }

    public boolean isIndeterminate() {
        //只控制店铺下的商品
        return !(count == this.checkedCount);
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCheckedCount() {
        return checkedCount;
    }

    public void setCheckedCount(Long checkedCount) {
        this.checkedCount = checkedCount;
    }
}
