package com.ruoyi.bsapi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.bsapi.vo.CatelogAppVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.CatelogMapper;
import com.ruoyi.bsapi.domain.Catelog;
import com.ruoyi.bsapi.service.ICatelogService;
import org.springframework.util.CollectionUtils;

/**
 * 分类Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class CatelogServiceImpl implements ICatelogService 
{
    @Autowired
    private CatelogMapper catelogMapper;

    /**
     * 查询分类
     * 
     * @param catelogId 分类主键
     * @return 分类
     */
    @Override
    public Catelog selectCatelogByCatelogId(Long catelogId)
    {
        return catelogMapper.selectCatelogByCatelogId(catelogId);
    }

    /**
     * 查询分类列表
     * 
     * @param catelog 分类
     * @return 分类
     */
    @Override
    public List<Catelog> selectCatelogList(Catelog catelog)
    {
        return catelogMapper.selectCatelogList(catelog);
    }

    /**
     * 新增分类
     * 
     * @param catelog 分类
     * @return 结果
     */
    @Override
    public int insertCatelog(Catelog catelog)
    {
        return catelogMapper.insertCatelog(catelog);
    }

    /**
     * 修改分类
     * 
     * @param catelog 分类
     * @return 结果
     */
    @Override
    public int updateCatelog(Catelog catelog)
    {
        return catelogMapper.updateCatelog(catelog);
    }

    /**
     * 批量删除分类
     * 
     * @param catelogIds 需要删除的分类主键
     * @return 结果
     */
    @Override
    public int deleteCatelogByCatelogIds(Long[] catelogIds)
    {
        return catelogMapper.deleteCatelogByCatelogIds(catelogIds);
    }

    /**
     * 删除分类信息
     * 
     * @param catelogId 分类主键
     * @return 结果
     */
    @Override
    public int deleteCatelogByCatelogId(Long catelogId)
    {
        return catelogMapper.deleteCatelogByCatelogId(catelogId);
    }

    @Override
    public List<Catelog> treeList() {
        //获取搜有的分类
        List<Catelog> catelogs = catelogMapper.selectCatelogList(null);
        List<Catelog> catelogList = catelogs.stream().filter(catelog -> catelog.getParentId() == 0)
                .map(catelog -> {
                    catelog.setChildren(getCatalogChildren(catelogs, catelog.getCatelogId()));
                    return catelog;
                }).collect(Collectors.toList());
        return catelogList;
    }

    @Override
    public Catelog getCatelogByCatelogId(Long catelogId) {
        return this.selectCatelogByCatelogId(catelogId);
    }

    @Override
    public List<Long> selectCatelogs(Long catelogId) {
        Catelog catelog = catelogMapper.selectCatelogByCatelogId(catelogId);
        return Arrays.asList(catelog.getParentId(), catelogId);
    }

    @Override
    public List<Catelog> queryCatelogs() {
        List<Catelog> catelogs = new ArrayList<>();
        List<Catelog> list = this.treeList();
        for (int i = 0; i< 5; i++) {
            catelogs.add(list.get(i));
        }
        return catelogs;
    }

    /**
     * 获取当前分类的子分类
     * @param catelogs
     * @param catelogId
     * @return
     */
    private List<Catelog> getCatalogChildren(List<Catelog> catelogs, Long catelogId) {
        List<Catelog> catelogList = catelogs.stream().filter(catelog -> catelog.getParentId().equals(catelogId))
                .map(catelog -> {
                    List<Catelog> catalogChildren = getCatalogChildren(catelogs, catelog.getCatelogId());
                    if (CollectionUtils.isEmpty(catalogChildren)) {
                        return catelog;
                    } else {
                        catelog.setChildren(catalogChildren);
                    }
                    return catelog;
                }).collect(Collectors.toList());
        return catelogList;
    }
}
