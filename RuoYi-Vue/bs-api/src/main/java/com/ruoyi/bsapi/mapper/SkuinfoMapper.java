package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.Skuinfo;
import org.apache.ibatis.annotations.Param;

/**
 * sku infoMapper接口
 * 
 * @author lqg
 * @date 2023-03-17
 */
public interface SkuinfoMapper 
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
     * 删除sku info
     * 
     * @param skuId sku info主键
     * @return 结果
     */
    public int deleteSkuinfoBySkuId(Long skuId);

    /**
     * 批量删除sku info
     * 
     * @param skuIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSkuinfoBySkuIds(Long[] skuIds);

    /**
     * 批量添加
     * @param skuinfos
     */
    void batchSave(@Param("skuinfos") List<Skuinfo> skuinfos);

    /**
     * 根据商品id删除sku
     * @param productId
     */
    void deleteProductId(@Param("productId") Long productId);

    /**
     * 点击删除修改时的图片
     * @param img
     */
    void deleteProductImgUrl(@Param("img") String img);
}
