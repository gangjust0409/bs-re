package com.ruoyi.bsapi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.domain.User;
import com.ruoyi.bsapi.service.IProductService;
import com.ruoyi.bsapi.to.ProductHistoryTo;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.ProductHistory;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.MyHistoryMapper;
import com.ruoyi.bsapi.domain.MyHistory;
import com.ruoyi.bsapi.service.IMyHistoryService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * 我的足迹Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class MyHistoryServiceImpl implements IMyHistoryService 
{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private MyHistoryMapper myHistoryMapper;

    @Resource
    private IProductService productService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询我的足迹
     * 
     * @param myHistoryId 我的足迹主键
     * @return 我的足迹
     */
    @Override
    public MyHistory selectMyHistoryByMyHistoryId(Long myHistoryId)
    {
        return myHistoryMapper.selectMyHistoryByMyHistoryId(myHistoryId);
    }

    /**
     * 查询我的足迹列表
     * 
     * @param myHistory 我的足迹
     * @return 我的足迹
     */
    @Override
    public List<MyHistory> selectMyHistoryList(MyHistory myHistory)
    {
        return myHistoryMapper.selectMyHistoryList(myHistory);
    }

    /**
     * 新增我的足迹
     * 
     * @param myHistory 我的足迹
     * @return 结果
     */
    @Override
    public int insertMyHistory(MyHistory myHistory)
    {
        return myHistoryMapper.insertMyHistory(myHistory);
    }

    /**
     * 修改我的足迹
     * 
     * @param myHistory 我的足迹
     * @return 结果
     */
    @Override
    public int updateMyHistory(MyHistory myHistory)
    {
        return myHistoryMapper.updateMyHistory(myHistory);
    }

    /**
     * 批量删除我的足迹
     * 
     * @param myHistoryIds 需要删除的我的足迹主键
     * @return 结果
     */
    @Override
    public int deleteMyHistoryByMyHistoryIds(Long[] myHistoryIds)
    {
        return myHistoryMapper.deleteMyHistoryByMyHistoryIds(myHistoryIds);
    }

    /**
     * 删除我的足迹信息
     * 
     * @param myHistoryId 我的足迹主键
     * @return 结果
     */
    @Override
    public int deleteMyHistoryByMyHistoryId(Long myHistoryId)
    {
        return myHistoryMapper.deleteMyHistoryByMyHistoryId(myHistoryId);
    }

    @Override
    public void saveHistoryProduct(Long productId, String token) {
        User user = RedisConsant.getUser(stringRedisTemplate, token);
        MyHistory myHistory = new MyHistory();
        myHistory.setUserId(user.getUserId());
        myHistory.setProductId(productId);
        myHistory.setHistoryDate(new Date());
        //保存
        this.insertMyHistory(myHistory);
    }

    @Override
    public List<ProductHistory> getCurrentUserHistoryProducts(String token) {
        //查询当前用户
        User user = RedisConsant.getUser(stringRedisTemplate, token);
        List<Product> products = productService.selectProductList(null);
        //查询我的历史
        List<ProductHistoryTo> myHistories = myHistoryMapper.selectMyHistory(user.getUserId());
        if (!CollectionUtils.isEmpty(myHistories)) {
            return myHistories.stream().map(myHistory -> {
                ProductHistory productHistory = new ProductHistory();
                //遍历输入商品id
                List<Product> historyHasNulls = products.stream().map(product -> {
                    for (String productId : myHistory.getPros().split(",")) {
                        if (product.getProductId().equals(Long.parseLong(productId))) {
                            return product;
                        }
                    }
                    return null;
                }).collect(Collectors.toList());

                productHistory.setDate(sdf.format(myHistory.getDate()));
                productHistory.setHistorys(historyHasNulls.stream().filter(e -> e != null).collect(Collectors.toList()));
                return productHistory;
            }).collect(Collectors.toList());
        }
        return null;
    }
}
