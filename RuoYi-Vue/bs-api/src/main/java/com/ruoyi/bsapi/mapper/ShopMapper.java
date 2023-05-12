package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.Shop;
import org.apache.ibatis.annotations.Param;

/**
 * 商店Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface ShopMapper 
{
    /**
     * 查询商店
     * 
     * @param shopId 商店主键
     * @return 商店
     */
    public Shop selectShopByShopId(Long shopId);

    /**
     * 查询商店列表
     * 
     * @param shop 商店
     * @return 商店集合
     */
    public List<Shop> selectShopList(Shop shop);

    /**
     * 新增商店
     * 
     * @param shop 商店
     * @return 结果
     */
    public int insertShop(Shop shop);

    /**
     * 修改商店
     * 
     * @param shop 商店
     * @return 结果
     */
    public int updateShop(Shop shop);

    /**
     * 删除商店
     * 
     * @param shopId 商店主键
     * @return 结果
     */
    public int deleteShopByShopId(Long shopId);

    /**
     * 批量删除商店
     * 
     * @param shopIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopByShopIds(Long[] shopIds);

    /**
     * 店铺信息总条数
     * @param shopName
     * @return
     */
    Integer selectCount(@Param("shopName") String shopName);
}
