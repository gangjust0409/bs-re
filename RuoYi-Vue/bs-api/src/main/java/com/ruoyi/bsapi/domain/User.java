package com.ruoyi.bsapi.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户对象 user
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long userId;

    /** 账号，可以随机生成 */
    @Excel(name = "账号，可以随机生成")
    private String account;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String nickName;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 性别 1 男 2 女 3 保密 */
    @Excel(name = "性别 1 男 2 女 3 保密")
    private Long sex;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 用户头像，注册默认给一个头像，后面可自行上传 */
    @Excel(name = "用户头像，注册默认给一个头像，后面可自行上传")
    private String userPic;

    /** 默认地址 */
    @Excel(name = "默认地址")
    private Long defaultAddressId;

    @Excel(name = "该用户的钱")
    private BigDecimal totalPrice;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setSex(Long sex) 
    {
        this.sex = sex;
    }

    public Long getSex() 
    {
        return sex;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }
    public void setUserPic(String userPic) 
    {
        this.userPic = userPic;
    }

    public String getUserPic() 
    {
        return userPic;
    }
    public void setDefaultAddressId(Long defaultAddressId) 
    {
        this.defaultAddressId = defaultAddressId;
    }

    public Long getDefaultAddressId() 
    {
        return defaultAddressId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("account", getAccount())
            .append("nickName", getNickName())
            .append("password", getPassword())
            .append("sex", getSex())
            .append("email", getEmail())
            .append("phone", getPhone())
            .append("createDate", getCreateDate())
            .append("userPic", getUserPic())
            .append("defaultAddressId", getDefaultAddressId())
            .toString();
    }
}
