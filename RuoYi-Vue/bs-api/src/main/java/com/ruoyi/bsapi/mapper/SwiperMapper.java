package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.Swiper;

/**
 * 轮播图Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface SwiperMapper 
{
    /**
     * 查询轮播图
     * 
     * @param swiperId 轮播图主键
     * @return 轮播图
     */
    public Swiper selectSwiperBySwiperId(Long swiperId);

    /**
     * 查询轮播图列表
     * 
     * @param swiper 轮播图
     * @return 轮播图集合
     */
    public List<Swiper> selectSwiperList(Swiper swiper);

    /**
     * 新增轮播图
     * 
     * @param swiper 轮播图
     * @return 结果
     */
    public int insertSwiper(Swiper swiper);

    /**
     * 修改轮播图
     * 
     * @param swiper 轮播图
     * @return 结果
     */
    public int updateSwiper(Swiper swiper);

    /**
     * 删除轮播图
     * 
     * @param swiperId 轮播图主键
     * @return 结果
     */
    public int deleteSwiperBySwiperId(Long swiperId);

    /**
     * 批量删除轮播图
     * 
     * @param swiperIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSwiperBySwiperIds(Long[] swiperIds);

    /**
     * 查询总表数
     * @param productTitle
     * @return
     */
    int selectCount();
}
