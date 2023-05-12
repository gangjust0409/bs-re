package com.ruoyi.bsapi.vo;

import com.ruoyi.bsapi.domain.Attrs;
import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用于展示商品列表
 */
public class ProductInfoVo {

    /** 商品id */
    private Long productId;

    /** 商品名称 */
    private String productTitle;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 商品封面 */
    private String productPic;

    /** 商品状态 */
    private Long status;

    /** 商店名称 */
    private String shopName;

    /** 属性 */
    private List<Attrs> attrs;

    /** 商品分类id */
    private Long catelgoId;

    /** 商品分类 */
    private String catelogName;

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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<Attrs> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<Attrs> attrs) {
        this.attrs = attrs;
    }

    public String getCatelogName() {
        return catelogName;
    }

    public void setCatelogName(String catelogName) {
        this.catelogName = catelogName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCatelgoId() {
        return catelgoId;
    }

    public void setCatelgoId(Long catelgoId) {
        this.catelgoId = catelgoId;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "productTitle='" + productTitle + '\'' +
                ", productPrice=" + productPrice +
                ", productPic='" + productPic + '\'' +
                ", status=" + status +
                ", shopName='" + shopName + '\'' +
                ", attrs=" + attrs +
                ", catelogName='" + catelogName + '\'' +
                '}';
    }
}
