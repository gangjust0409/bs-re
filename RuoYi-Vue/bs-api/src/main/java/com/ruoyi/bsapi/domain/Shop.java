package com.ruoyi.bsapi.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商店对象 shop
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class Shop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long shopId;

    /** 商店名称 */
    @Excel(name = "商店名称")
    private String shopName;

    /** 商店封面 */
    @Excel(name = "商店封面")
    private String shopPic;

    /** 商店创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "商店创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    public void setShopId(Long shopId) 
    {
        this.shopId = shopId;
    }

    public Long getShopId() 
    {
        return shopId;
    }
    public void setShopName(String shopName) 
    {
        this.shopName = shopName;
    }

    public String getShopName() 
    {
        return shopName;
    }
    public void setShopPic(String shopPic) 
    {
        this.shopPic = shopPic;
    }

    public String getShopPic() 
    {
        return shopPic;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("shopId", getShopId())
            .append("shopName", getShopName())
            .append("shopPic", getShopPic())
            .append("createDate", getCreateDate())
            .toString();
    }
}
