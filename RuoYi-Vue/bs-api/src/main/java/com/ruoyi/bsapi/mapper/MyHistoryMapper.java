package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.MyHistory;
import com.ruoyi.bsapi.to.ProductHistoryTo;
import org.apache.ibatis.annotations.Param;

/**
 * 我的足迹Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface MyHistoryMapper 
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
     * 删除我的足迹
     * 
     * @param myHistoryId 我的足迹主键
     * @return 结果
     */
    public int deleteMyHistoryByMyHistoryId(Long myHistoryId);

    /**
     * 批量删除我的足迹
     * 
     * @param myHistoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyHistoryByMyHistoryIds(Long[] myHistoryIds);

    /**
     * 查询我的历史
     * @param userId
     * @return
     */
    List<ProductHistoryTo> selectMyHistory(@Param("userId") Long userId);
}
