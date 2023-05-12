package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.Address;
import com.ruoyi.bsapi.vo.AddressUserVo;

/**
 * 地址Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface IAddressService 
{
    /**
     * 查询地址
     * 
     * @param addressId 地址主键
     * @return 地址
     */
    public Address selectAddressByAddressId(Long addressId);

    /**
     * 查询地址列表
     * 
     * @param address 地址
     * @return 地址集合
     */
    public List<Address> selectAddressList(Address address);

    /**
     * 新增地址
     * 
     * @param address 地址
     * @return 结果
     */
    public int insertAddress(Address address);

    /**
     * 修改地址
     * 
     * @param address 地址
     * @return 结果
     */
    public int updateAddress(Address address);

    /**
     * 批量删除地址
     * 
     * @param addressIds 需要删除的地址主键集合
     * @return 结果
     */
    public int deleteAddressByAddressIds(Long[] addressIds);

    /**
     * 删除地址信息
     * 
     * @param addressId 地址主键
     * @return 结果
     */
    public int deleteAddressByAddressId(Long addressId);

    /**
     * 添加地址
     * @param token
     * @param address
     */
    void addAddress(String token, Address address);

    /**
     * 查询当前用户的地址信息
     * @param token
     * @return
     */
    List<AddressUserVo> queryCurrentUserAddress(String token);

    /**
     * 修改地址
     * @param address
     */
    void editAddress(Address address);

    /**
     * 改变地址
     *
     * @param token
     * @param addressId
     */
    void startAddress(String token, Long addressId);
}
