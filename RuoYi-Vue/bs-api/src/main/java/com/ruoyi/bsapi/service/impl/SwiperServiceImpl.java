package com.ruoyi.bsapi.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.service.IProductService;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.SwiperProductVo;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.my.FileUtil;
import com.ruoyi.common.utils.my.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.SwiperMapper;
import com.ruoyi.bsapi.domain.Swiper;
import com.ruoyi.bsapi.service.ISwiperService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 轮播图Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class SwiperServiceImpl implements ISwiperService 
{
    @Autowired
    private SwiperMapper swiperMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Resource
    private IProductService productService;

    /**
     * 查询轮播图
     * 
     * @param swiperId 轮播图主键
     * @return 轮播图
     */
    @Override
    public Swiper selectSwiperBySwiperId(Long swiperId) {
        Swiper swiper = swiperMapper.selectSwiperBySwiperId(swiperId);
        swiper.setSwiperUrl(com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + swiper.getSwiperUrl());
        return swiper;
    }

    /**
     * 查询轮播图列表
     * 
     * @param swiper 轮播图
     * @return 轮播图
     */
    @Override
    public List<Swiper> selectSwiperList(Swiper swiper) {
        return swiperMapper.selectSwiperList(swiper).stream()
                .map(swiper1 -> {
                    swiper1.setSwiperUrl(com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD+swiper1.getSwiperUrl());
                    return swiper1;
                }).collect(Collectors.toList());
    }

    /**
     * 新增轮播图
     * 
     * @param swiper 轮播图
     * @return 结果
     */
    @Override
    public int insertSwiper(Swiper swiper) {
        String newName = stringRedisTemplate.opsForValue().get(RedisConsant.UPLOAD_SWIPER_IMG);
        if (StringUtils.isNotNull(newName)) {
            swiper.setSwiperUrl(newName);
        }
        swiper.setEnable(1l);
        int i = swiperMapper.insertSwiper(swiper);
        stringRedisTemplate.delete(RedisConsant.UPLOAD_SWIPER_IMG);
        return i;
    }

    /**
     * 修改轮播图
     * 
     * @param swiper 轮播图
     * @return 结果
     */
    @Override
    public int updateSwiper(Swiper swiper) {
        String newName = stringRedisTemplate.opsForValue().get(RedisConsant.UPLOAD_SWIPER_IMG);
        if (StringUtils.isNotNull(newName)) {
            swiper.setSwiperUrl(newName);
        }
        stringRedisTemplate.delete(RedisConsant.UPLOAD_SWIPER_IMG);
        return swiperMapper.updateSwiper(swiper);
    }

    /**
     * 批量删除轮播图
     * 
     * @param swiperIds 需要删除的轮播图主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSwiperBySwiperIds(Long[] swiperIds) {
        //先删除图片
        for (Long swiperId : swiperIds) {
            Swiper swiper = this.selectSwiperBySwiperId(swiperId);
            String path = RuoYiConfig.getProfile()  + "/" + swiper.getSwiperUrl();
            new File(path).delete();
        }
        return swiperMapper.deleteSwiperBySwiperIds(swiperIds);
    }

    /**
     * 删除轮播图信息
     * 
     * @param swiperId 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteSwiperBySwiperId(Long swiperId)
    {
        return swiperMapper.deleteSwiperBySwiperId(swiperId);
    }

    @Override
    public void uploadSwiper(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String prefix = FileUtil.getPrefix(originalFilename);
                String newName = StringUtils.randomUUID() + "." + prefix;
                stringRedisTemplate.opsForValue().set(RedisConsant.UPLOAD_SWIPER_IMG, newName, 2L, TimeUnit.MINUTES);
                File dirFile = new File(RuoYiConfig.getProfile(), newName);
                InputStream is = file.getInputStream();
                FileUtil.transformTo(is, dirFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<SwiperProductVo> selectProductReleaseSwiper(String productTitle) {
        //查询商品信息
        Product productParams = new Product();
        productParams.setProductTitle(productTitle);
        List<Product> products = null;
        List<Swiper> swipers = null;
        List<SwiperProductVo> swiperProductVos = null;
        if (StringUtils.isNotNull(productTitle)) {
            startPage();
            products = productService.selectProductList(productParams);
            swipers = swiperMapper.selectSwiperList(null);
        } else {
            startPage();
            swipers = swiperMapper.selectSwiperList(null);
            products = productService.selectProductList(productParams);
        }
        //查询轮播图
        if (StringUtils.isNotNull(productTitle)) {
            //条件
            List<Swiper> finalSwipers = swipers;
            swiperProductVos = products.stream().map(pr -> {
                SwiperProductVo swiperProductVo = new SwiperProductVo();
                for (Swiper swiper : finalSwipers) {
                    BeanUtils.copyProperties(swiper, swiperProductVo);
                    swiperProductVo.setSwiperUrl(com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD+swiper.getSwiperUrl());
                    if (swiper.getProductId().equals(pr.getProductId())) {
                        swiperProductVo.setProductId(pr.getProductId());
                        swiperProductVo.setProductTitle(pr.getProductTitle());
                        return swiperProductVo;
                    }
                }
                return swiperProductVo;
            }).collect(Collectors.toList());
        } else {
            //全部
            List<Product> finalProducts = products;
            swiperProductVos = swipers.stream()
                    .map(swiper -> {
                        SwiperProductVo swiperProductVo = new SwiperProductVo();
                        for (Product product : finalProducts) {
                            if (product.getProductId().equals(swiper.getProductId())) {
                                BeanUtils.copyProperties(swiper, swiperProductVo);
                                swiperProductVo.setSwiperUrl(com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD+swiper.getSwiperUrl());
                                swiperProductVo.setProductId(product.getProductId());
                                swiperProductVo.setProductTitle(product.getProductTitle());
                                return swiperProductVo;
                            }
                        }
                        return swiperProductVo;
                    }).collect(Collectors.toList());
        }

        return swiperProductVos;
    }

    @Override
    public int selectCount(String productTitle) {
        int total=0;
        if (StringUtils.isNotNull(productTitle)) {
            total = Math.toIntExact(productService.selectCount(productTitle, 0L));
        } else {
            total  = swiperMapper.selectCount();
        }

        return total;
    }

    @Override
    public int editSwiperStatus(String str) {
        String[] split = str.split("-");
        Long swiperId = Long.valueOf(split[0]);
        Long status = Long.valueOf(split[1]);

        //只能时 2 或1
        if (status == 2 || status == 1) {
            Swiper swiper = this.selectSwiperBySwiperId(swiperId);
            String fileName = swiper.getSwiperUrl().substring(swiper.getSwiperUrl().lastIndexOf("/") + 1);
            swiper.setSwiperUrl(fileName);
            swiper.setEnable(status);
            //执行修改
            return this.updateSwiper(swiper);
        } else {
            return 3;
        }

    }

    @Override
    public List<Swiper> swiperList() {
        Swiper swiperParams = new Swiper();
        swiperParams.setEnable(1L);
        List<Swiper> swipers = this.selectSwiperList(swiperParams);
        return swipers;
    }


    //搜索

}
