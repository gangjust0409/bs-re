package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.Shop;
import com.ruoyi.bsapi.vo.ShopReleaseProductVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商店Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface IShopService 
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
     * 批量删除商店
     * 
     * @param shopIds 需要删除的商店主键集合
     * @return 结果
     */
    public int deleteShopByShopIds(Long[] shopIds);

    /**
     * 删除商店信息
     * 
     * @param shopId 商店主键
     * @return 结果
     */
    public int deleteShopByShopId(Long shopId);

    /**
     * 根据id获取商店信息
     * @param shopId
     * @return
     */
    Shop getShopById(Long shopId);

    /**
     * 上传店铺图标
     * @param file
     */
    void uploadShopIcon(MultipartFile file);

    /**
     * 查询店铺和店铺关联商品信息
     * @param shopName
     * @return
     */
    List<ShopReleaseProductVo> shopsAndShopReleaseProducts(String shopName);

    /**
     * 查询店铺和店铺关联商品总条数信息
     * @param shopName
     * @return
     */
    int selectCount(String shopName);
}
