package com.ruoyi.bsapi.vo;

import com.ruoyi.bsapi.to.AddCartItemTo;
import com.ruoyi.bsapi.to.AddCartShopItemTo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个用户的购物车
 */
public class AddCartResp {

    /**
     * 购物车总数量
     */
    private Long count;

    /**
     * 选中其中一项样式改变
     */
    private boolean isIndeterminate = false;


    /**
     * 是否全选中
     */
    private boolean ifCheckedAll;

    /**
     * 选中的数量
     */
    private Long checkedCount;

    /**
     * 购物车所有的商品总价格
     */
    private BigDecimal totalPrice;

    /**
     * 当前用户下的购物车信息
     */
    private List<AddCartShopItemTo> shopItemTos = new ArrayList<>();

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public boolean isIfCheckedAll() {
        //是否全选
        if (this.count == this.checkedCount) {
            return true;
        }
        return false;
    }

    public Long getCheckedCount() {
        return checkedCount;
    }

    public void setCheckedCount(Long checkedCount) {
        this.checkedCount = checkedCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<AddCartShopItemTo> getShopItemTos() {
        return shopItemTos;
    }

    public void setShopItemTos(List<AddCartShopItemTo> shopItemTos) {
        this.shopItemTos = shopItemTos;
    }

    public boolean isIndeterminate() {
        if (this.checkedCount == 0 || this.isIfCheckedAll()) {
            return false;
        }
        return true;
    }

    public void setIndeterminate(boolean indeterminate) {
        isIndeterminate = indeterminate;
    }

    @Override
    public String toString() {
        return "AddCartResp{" +
                "count=" + count +
                ", isIndeterminate=" + isIndeterminate +
                ", ifCheckedAll=" + ifCheckedAll +
                ", checkedCount=" + checkedCount +
                ", totalPrice=" + totalPrice +
                ", shopItemTos=" + shopItemTos +
                '}';
    }
}
