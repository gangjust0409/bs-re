package com.ruoyi.bsapi.vo;

/**
 * 当前用户下的地址信息
 */
public class AddressUserVo {

    private Long addressId;
    private String address;
    private Long defaultAddress;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Long defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
