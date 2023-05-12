package com.ruoyi.bsapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 我的收藏对象 my_collect
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class MyCollect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long myCollectId;

    /** 关联的商品 id */
    @Excel(name = "关联的商品 id")
    private Long productId;

    /** 关联的用户id */
    @Excel(name = "关联的用户id")
    private Long userId;

    public void setMyCollectId(Long myCollectId) 
    {
        this.myCollectId = myCollectId;
    }

    public Long getMyCollectId() 
    {
        return myCollectId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("myCollectId", getMyCollectId())
            .append("productId", getProductId())
            .append("userId", getUserId())
            .toString();
    }
}
