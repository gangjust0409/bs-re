package com.ruoyi.bsapi.to;

import com.ruoyi.bsapi.domain.Attrs;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车每一项
 */
public class AddCartItemTo {

    /**
     * 是否选中
     */
    private boolean checked;

    /**
     * 商品id
     */
    private Long productId;


    /**
     * 商品数量
     */
    private Long count;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 商品标题
     */
    private String productTitle;

    /**
     * 商品封面
     */
    private String productPic;

    /**
     * 当前商品总价格 数量 * 单价
     */
    private BigDecimal totalMoney;

    /**
     * 属性
     */
    private List<Attrs> attrs;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<Attrs> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<Attrs> attrs) {
        this.attrs = attrs;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "AddCartItemVo{" +
                "checked=" + checked +
                ", count=" + count +
                ", price=" + price +
                ", productTitle='" + productTitle + '\'' +
                ", productPic='" + productPic + '\'' +
                ", totalMoney=" + totalMoney +
                ", attrs=" + attrs +
                '}';
    }
}
