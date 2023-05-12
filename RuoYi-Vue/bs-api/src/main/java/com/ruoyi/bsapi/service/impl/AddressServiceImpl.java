package com.ruoyi.bsapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.domain.User;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.AddressUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.AddressMapper;
import com.ruoyi.bsapi.domain.Address;
import com.ruoyi.bsapi.service.IAddressService;

import javax.annotation.Resource;

/**
 * 地址Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class AddressServiceImpl implements IAddressService 
{
    @Autowired
    private AddressMapper addressMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询地址
     * 
     * @param addressId 地址主键
     * @return 地址
     */
    @Override
    public Address selectAddressByAddressId(Long addressId)
    {
        return addressMapper.selectAddressByAddressId(addressId);
    }

    /**
     * 查询地址列表
     * 
     * @param address 地址
     * @return 地址
     */
    @Override
    public List<Address> selectAddressList(Address address)
    {
        return addressMapper.selectAddressList(address);
    }

    /**
     * 新增地址
     * 
     * @param address 地址
     * @return 结果
     */
    @Override
    public int insertAddress(Address address)
    {
        return addressMapper.insertAddress(address);
    }

    /**
     * 修改地址
     * 
     * @param address 地址
     * @return 结果
     */
    @Override
    public int updateAddress(Address address)
    {
        return addressMapper.updateAddress(address);
    }

    /**
     * 批量删除地址
     * 
     * @param addressIds 需要删除的地址主键
     * @return 结果
     */
    @Override
    public int deleteAddressByAddressIds(Long[] addressIds)
    {
        return addressMapper.deleteAddressByAddressIds(addressIds);
    }

    /**
     * 删除地址信息
     * 
     * @param addressId 地址主键
     * @return 结果
     */
    @Override
    public int deleteAddressByAddressId(Long addressId)
    {
        return addressMapper.deleteAddressByAddressId(addressId);
    }

    @Override
    public void addAddress(String token, Address address) {
        //获取当前用户信息
        User user = RedisConsant.getUser(stringRedisTemplate, token);
        address.setUserId(user.getUserId());
        //第一次添加地址时，以第一个为默认的
        Address addressParams = new Address();
        addressParams.setUserId(user.getUserId());
        List<Address> addresses = addressMapper.selectAddressList(addressParams);
        if (addresses.size() >= 1) {
            address.setDefaultAddress(2L);
        } else {
            address.setDefaultAddress(1L);
        }
        addressMapper.insertAddress(address);
    }


    @Override
    public List<AddressUserVo> queryCurrentUserAddress(String token) {
        User user = RedisConsant.getUser(stringRedisTemplate, token);
        //查询地址信息
        Address addressParams = new Address();
        addressParams.setUserId(user.getUserId());
        List<AddressUserVo> addressList = addressMapper.selectAddressList(addressParams).stream().
                map(address -> {
                    AddressUserVo addressUserVo = new AddressUserVo();
                    String addTitle = address.getUserName() + "-" + address.getPhone() + "-" +
                            address.getAddressName() + " " + address.getDescAddress();
                    addressUserVo.setAddressId(address.getAddressId());
                    addressUserVo.setAddress(addTitle);
                    addressUserVo.setDefaultAddress(address.getDefaultAddress());
                    return addressUserVo;
                })
                .collect(Collectors.toList());
        return addressList;
    }

    @Override
    public void editAddress(Address address) {
        this.updateAddress(address);
    }

    @Override
    public void startAddress(String token, Long addressId) {
        User user = RedisConsant.getUser(stringRedisTemplate, token);
        //把当前用户的全部地址改成
        addressMapper.updateAddressAll(user.getUserId());
        //改变当前选中的
        Address address = new Address();
        address.setAddressId(addressId);
        address.setDefaultAddress(1L);
        addressMapper.updateAddress(address);
    }
}
