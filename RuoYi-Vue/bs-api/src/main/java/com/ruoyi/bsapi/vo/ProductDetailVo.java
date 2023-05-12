package com.ruoyi.bsapi.vo;

import com.ruoyi.bsapi.domain.Shop;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情类
 */
public class ProductDetailVo {

    /**
     * 商品 id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productTitle;

    /**
     * 当前选中的大图
     */
    private String activePic;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 销量
     */
    private Long monthPin;

    /**
     * 缩略图列表
     */
    private List<String> smailPics;

    /**
     * sku 信息
     */
    private List<ProductDetailSkuVo> skus;

    /**
     * 属性列表
     */
    private List<String> attrs;

    /**
     * 详情图片
     */
    private List<String> detailImages;

    /**
     * 分类
     */
    private List<Long> catelogIds;

    /**
     * 店铺信息
     */
    private Shop shop;

    /**
     * 属性列表
     */
    private List<ProductAttrItemVo> attrItems;

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

    public String getActivePic() {
        return activePic;
    }

    public void setActivePic(String activePic) {
        this.activePic = activePic;
    }

    public List<String> getSmailPics() {
        return smailPics;
    }

    public void setSmailPics(List<String> smailPics) {
        this.smailPics = smailPics;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public List<ProductDetailSkuVo> getSkus() {
        return skus;
    }

    public void setSkus(List<ProductDetailSkuVo> skus) {
        this.skus = skus;
    }

    public List<String> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<String> attrs) {
        this.attrs = attrs;
    }

    public List<String> getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(List<String> detailImages) {
        this.detailImages = detailImages;
    }

    public List<Long> getCatelogIds() {
        return catelogIds;
    }

    public void setCatelogIds(List<Long> catelogIds) {
        this.catelogIds = catelogIds;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<ProductAttrItemVo> getAttrItems() {
        return attrItems;
    }

    public void setAttrItems(List<ProductAttrItemVo> attrItems) {
        this.attrItems = attrItems;
    }

    public Long getMonthPin() {
        return monthPin;
    }

    public void setMonthPin(Long monthPin) {
        this.monthPin = monthPin;
    }
}
