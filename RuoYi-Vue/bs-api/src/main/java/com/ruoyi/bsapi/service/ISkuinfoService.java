package com.ruoyi.bsapi.service;

import java.util.List;

import com.ruoyi.bsapi.domain.Shop;
import com.ruoyi.bsapi.domain.Skuinfo;
import com.ruoyi.bsapi.vo.AddProductVo;

/**
 * sku infoService接口
 * 
 * @author lqg
 * @date 2023-03-17
 */
public interface ISkuinfoService 
{
    /**
     * 查询sku info
     * 
     * @param skuId sku info主键
     * @return sku info
     */
    public Skuinfo selectSkuinfoBySkuId(Long skuId);

    /**
     * 查询sku info列表
     * 
     * @param skuinfo sku info
     * @return sku info集合
     */
    public List<Skuinfo> selectSkuinfoList(Skuinfo skuinfo);

    /**
     * 新增sku info
     * 
     * @param skuinfo sku info
     * @return 结果
     */
    public int insertSkuinfo(Skuinfo skuinfo);

    /**
     * 修改sku info
     * 
     * @param skuinfo sku info
     * @return 结果
     */
    public int updateSkuinfo(Skuinfo skuinfo);

    /**
     * 批量删除sku info
     * 
     * @param skuIds 需要删除的sku info主键集合
     * @return 结果
     */
    public int deleteSkuinfoBySkuIds(Long[] skuIds);

    /**
     * 删除sku info信息
     * 
     * @param skuId sku info主键
     * @return 结果
     */
    public int deleteSkuinfoBySkuId(Long skuId);

    /**
     * 保存 sku info
     *
     * @param productId
     * @param skus
     */
    void saveSkuInfo(Long productId, AddProductVo.AddProductItem[] skus);

    /**
     * 根据商品id删除sku信息
     * @param productId
     */
    void deleteProductId(Long productId);

    /**
     * 把sku的文件内容信息删除
     */
    void deleteSkuInfoByPrimaryKey();

    /**
     * 点击删除修改时的图片
     * @param img
     */
    void deleteProductImgUrl(String img);
}
