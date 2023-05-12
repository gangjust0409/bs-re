package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.MyHistory;
import com.ruoyi.bsapi.vo.ProductHistory;

/**
 * 我的足迹Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface IMyHistoryService 
{
    /**
     * 查询我的足迹
     * 
     * @param myHistoryId 我的足迹主键
     * @return 我的足迹
     */
    public MyHistory selectMyHistoryByMyHistoryId(Long myHistoryId);

    /**
     * 查询我的足迹列表
     * 
     * @param myHistory 我的足迹
     * @return 我的足迹集合
     */
    public List<MyHistory> selectMyHistoryList(MyHistory myHistory);

    /**
     * 新增我的足迹
     * 
     * @param myHistory 我的足迹
     * @return 结果
     */
    public int insertMyHistory(MyHistory myHistory);

    /**
     * 修改我的足迹
     * 
     * @param myHistory 我的足迹
     * @return 结果
     */
    public int updateMyHistory(MyHistory myHistory);

    /**
     * 批量删除我的足迹
     * 
     * @param myHistoryIds 需要删除的我的足迹主键集合
     * @return 结果
     */
    public int deleteMyHistoryByMyHistoryIds(Long[] myHistoryIds);

    /**
     * 删除我的足迹信息
     * 
     * @param myHistoryId 我的足迹主键
     * @return 结果
     */
    public int deleteMyHistoryByMyHistoryId(Long myHistoryId);

    /**
     * 记录到我的历史
     * @param productId
     * @param token
     */
    void saveHistoryProduct(Long productId, String token);

    /**
     * 查询当前用户的历史记录
     * @param token
     * @return
     */
    List<ProductHistory> getCurrentUserHistoryProducts(String token);
}
