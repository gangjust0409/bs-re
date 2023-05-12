package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.MyCollect;

/**
 * 我的收藏Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface MyCollectMapper 
{
    /**
     * 查询我的收藏
     * 
     * @param myCollectId 我的收藏主键
     * @return 我的收藏
     */
    public MyCollect selectMyCollectByMyCollectId(Long myCollectId);

    /**
     * 查询我的收藏列表
     * 
     * @param myCollect 我的收藏
     * @return 我的收藏集合
     */
    public List<MyCollect> selectMyCollectList(MyCollect myCollect);

    /**
     * 新增我的收藏
     * 
     * @param myCollect 我的收藏
     * @return 结果
     */
    public int insertMyCollect(MyCollect myCollect);

    /**
     * 修改我的收藏
     * 
     * @param myCollect 我的收藏
     * @return 结果
     */
    public int updateMyCollect(MyCollect myCollect);

    /**
     * 删除我的收藏
     * 
     * @param myCollectId 我的收藏主键
     * @return 结果
     */
    public int deleteMyCollectByMyCollectId(Long myCollectId);

    /**
     * 批量删除我的收藏
     * 
     * @param myCollectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyCollectByMyCollectIds(Long[] myCollectIds);
}
