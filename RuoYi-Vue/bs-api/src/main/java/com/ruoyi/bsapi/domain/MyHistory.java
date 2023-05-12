package com.ruoyi.bsapi.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 我的足迹对象 my_history
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class MyHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long myHistoryId;

    /** 关联的商品 id */
    @Excel(name = "关联的商品 id")
    private Long productId;

    /** 关联的用户id */
    @Excel(name = "关联的用户id")
    private Long userId;

    /** 浏览日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "浏览日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date historyDate;

    public void setMyHistoryId(Long myHistoryId) 
    {
        this.myHistoryId = myHistoryId;
    }

    public Long getMyHistoryId() 
    {
        return myHistoryId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setHistoryDate(Date historyDate) 
    {
        this.historyDate = historyDate;
    }

    public Date getHistoryDate() 
    {
        return historyDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("myHistoryId", getMyHistoryId())
            .append("productId", getProductId())
            .append("userId", getUserId())
            .append("historyDate", getHistoryDate())
            .toString();
    }
}
