package com.ruoyi.bsapi.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品对象 product
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class Product extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long productId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productTitle;

    /** 商品价格 */
    @Excel(name = "商品价格")
    private BigDecimal productPrice;

    /** 商品封面 */
    @Excel(name = "商品封面")
    private String productPic;

    /** 商店id 必须分配给商店id，才可以上架 */
    @Excel(name = "商店id 必须分配给商店id，才可以上架")
    private Long shopId;

    /** 通过定时任务来计算卖掉的商品 */
    @Excel(name = "通过定时任务来计算卖掉的商品")
    private Long monthPin;

    /** 旧的价格 */
    @Excel(name = "旧的价格")
    private BigDecimal productOldPrice;

    /** 属性id */
    @Excel(name = "属性id")
    private Long attrId;

    @Excel(name = "商品状态")
    private Long status;

    @Excel(name = "分类id")
    private Long catelogId;

    @Excel(name = "是否收藏")
    private Boolean ifCollected;

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setProductTitle(String productTitle) 
    {
        this.productTitle = productTitle;
    }

    public String getProductTitle() 
    {
        return productTitle;
    }
    public void setProductPrice(BigDecimal productPrice) 
    {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductPrice() 
    {
        return productPrice;
    }
    public void setProductPic(String productPic) 
    {
        this.productPic = productPic;
    }

    public String getProductPic() 
    {
        return productPic;
    }
    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setMonthPin(Long monthPin) 
    {
        this.monthPin = monthPin;
    }

    public Long getMonthPin() 
    {
        return monthPin;
    }
    public void setProductOldPrice(BigDecimal productOldPrice) 
    {
        this.productOldPrice = productOldPrice;
    }

    public BigDecimal getProductOldPrice() 
    {
        return productOldPrice;
    }
    public void setAttrId(Long attrId) 
    {
        this.attrId = attrId;
    }

    public Long getAttrId() 
    {
        return attrId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getCatelogId() {
        return catelogId;
    }

    public void setCatelogId(Long catelogId) {
        this.catelogId = catelogId;
    }

    public Boolean getIfCollected() {
        return ifCollected;
    }

    public void setIfCollected(Boolean ifCollected) {
        this.ifCollected = ifCollected;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("productTitle", getProductTitle())
            .append("productPrice", getProductPrice())
            .append("productPic", getProductPic())
            .append("shopId", getShopId())
            .append("monthPin", getMonthPin())
            .append("productOldPrice", getProductOldPrice())
            .append("attrId", getAttrId())
                .append("catelogId", getCatelogId())
            .toString();
    }
}
