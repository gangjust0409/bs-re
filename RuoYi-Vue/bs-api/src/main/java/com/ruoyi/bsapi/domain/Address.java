package com.ruoyi.bsapi.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 地址对象 address
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class Address extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long addressId;

    @Excel(name = "用户名称")
    private String userName;

    @Excel(name = "手机号")
    private String phone;

    /** 地址名称 */
    @Excel(name = "地址名称")
    private String addressName;

    /** 用户关联的地址 */
    @Excel(name = "用户关联的地址")
    private Long userId;

    /** 地址以上的省、市、区 */
    @Excel(name = "地址以上的省、市、区")
    private Long defaultAddress;

    /** 用户详细地址 */
    @Excel(name = "用户详细地址")
    private String descAddress;

    /** 运费 */
    @Excel(name = "运费")
    private BigDecimal fare;

    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
    }
    public void setAddressName(String addressName) 
    {
        this.addressName = addressName;
    }

    public String getAddressName() 
    {
        return addressName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public Long getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Long defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public void setDescAddress(String descAddress)
    {
        this.descAddress = descAddress;
    }

    public String getDescAddress() 
    {
        return descAddress;
    }
    public void setFare(BigDecimal fare) 
    {
        this.fare = fare;
    }

    public BigDecimal getFare() 
    {
        return fare;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("addressId", getAddressId())
            .append("addressName", getAddressName())
            .append("userId", getUserId())
            .append("descAddress", getDescAddress())
            .append("fare", getFare())
            .toString();
    }
}
