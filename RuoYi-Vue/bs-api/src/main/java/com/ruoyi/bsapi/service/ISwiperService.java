package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.Swiper;
import com.ruoyi.bsapi.vo.SwiperProductVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 轮播图Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface ISwiperService 
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
     * 批量删除轮播图
     * 
     * @param swiperIds 需要删除的轮播图主键集合
     * @return 结果
     */
    public int deleteSwiperBySwiperIds(Long[] swiperIds);

    /**
     * 删除轮播图信息
     * 
     * @param swiperId 轮播图主键
     * @return 结果
     */
    public int deleteSwiperBySwiperId(Long swiperId);

    /**
     * 上传轮播图
     * @param file
     */
    void uploadSwiper(MultipartFile file);

    /**
     * 查询轮播图数据
     * @param productTitle
     * @return
     */
    List<SwiperProductVo> selectProductReleaseSwiper(String productTitle);

    /**
     * 查询总条数
     * @param productTitle
     * @return
     */
    int selectCount(String productTitle);

    /**
     * 启用、禁用
     * @param str
     */
    int editSwiperStatus(String str);

    /**
     * 轮播图数据
     * @return
     */
    List<Swiper> swiperList();
}
