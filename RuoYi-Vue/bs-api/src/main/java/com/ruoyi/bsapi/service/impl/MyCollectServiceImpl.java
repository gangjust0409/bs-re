package com.ruoyi.bsapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.domain.User;
import com.ruoyi.bsapi.service.IProductService;
import com.ruoyi.bsapi.utils.RedisConsant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.MyCollectMapper;
import com.ruoyi.bsapi.domain.MyCollect;
import com.ruoyi.bsapi.service.IMyCollectService;

import javax.annotation.Resource;

/**
 * 我的收藏Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class MyCollectServiceImpl implements IMyCollectService 
{
    @Autowired
    private MyCollectMapper myCollectMapper;

    @Resource
    private IProductService productService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询我的收藏
     * 
     * @param myCollectId 我的收藏主键
     * @return 我的收藏
     */
    @Override
    public MyCollect selectMyCollectByMyCollectId(Long myCollectId)
    {
        return myCollectMapper.selectMyCollectByMyCollectId(myCollectId);
    }

    /**
     * 查询我的收藏列表
     * 
     * @param myCollect 我的收藏
     * @return 我的收藏
     */
    @Override
    public List<MyCollect> selectMyCollectList(MyCollect myCollect)
    {
        return myCollectMapper.selectMyCollectList(myCollect);
    }

    /**
     * 新增我的收藏
     * 
     * @param myCollect 我的收藏
     * @return 结果
     */
    @Override
    public int insertMyCollect(MyCollect myCollect)
    {
        return myCollectMapper.insertMyCollect(myCollect);
    }

    /**
     * 修改我的收藏
     * 
     * @param myCollect 我的收藏
     * @return 结果
     */
    @Override
    public int updateMyCollect(MyCollect myCollect)
    {
        return myCollectMapper.updateMyCollect(myCollect);
    }

    /**
     * 批量删除我的收藏
     * 
     * @param myCollectIds 需要删除的我的收藏主键
     * @return 结果
     */
    @Override
    public int deleteMyCollectByMyCollectIds(Long[] myCollectIds)
    {
        return myCollectMapper.deleteMyCollectByMyCollectIds(myCollectIds);
    }

    /**
     * 删除我的收藏信息
     * 
     * @param myCollectId 我的收藏主键
     * @return 结果
     */
    @Override
    public int deleteMyCollectByMyCollectId(Long myCollectId)
    {
        return myCollectMapper.deleteMyCollectByMyCollectId(myCollectId);
    }

    @Override
    public List<Product> collectList(String token) {
        User user = RedisConsant.getUser(stringRedisTemplate, token);
        List<Product> products = productService.selectProductList(null);
        MyCollect myCollectParams = new MyCollect();
        myCollectParams.setUserId(user.getUserId());
        List<MyCollect> myCollects = this.selectMyCollectList(myCollectParams);
        List<Product> productList = products.stream().map(pro -> {
            for (MyCollect myCollect : myCollects) {
                if (pro.getProductId().equals(myCollect.getProductId())) {
                    return pro;
                }
            }
            return null;
        }).collect(Collectors.toList());
        return productList;
    }
}
