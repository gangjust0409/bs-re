package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.Catelog;
import com.ruoyi.bsapi.vo.CatelogAppVo;

/**
 * 分类Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface ICatelogService 
{
    /**
     * 查询分类
     * 
     * @param catelogId 分类主键
     * @return 分类
     */
    public Catelog selectCatelogByCatelogId(Long catelogId);

    /**
     * 查询分类列表
     * 
     * @param catelog 分类
     * @return 分类集合
     */
    public List<Catelog> selectCatelogList(Catelog catelog);

    /**
     * 新增分类
     * 
     * @param catelog 分类
     * @return 结果
     */
    public int insertCatelog(Catelog catelog);

    /**
     * 修改分类
     * 
     * @param catelog 分类
     * @return 结果
     */
    public int updateCatelog(Catelog catelog);

    /**
     * 批量删除分类
     * 
     * @param catelogIds 需要删除的分类主键集合
     * @return 结果
     */
    public int deleteCatelogByCatelogIds(Long[] catelogIds);

    /**
     * 删除分类信息
     * 
     * @param catelogId 分类主键
     * @return 结果
     */
    public int deleteCatelogByCatelogId(Long catelogId);

    /**
     * 分类树形菜单
     * @return
     */
    List<Catelog> treeList();

    /**
     * 根据分类ID获取分类信息
     * @param catelogId
     * @return
     */
    Catelog getCatelogByCatelogId(Long catelogId);

    /**
     * 根据商品id获取分类id和父分类id
     * @param catelogId
     * @return
     */
    List<Long> selectCatelogs(Long catelogId);

    /**
     * 首页分类
     * @return
     */
    List<Catelog> queryCatelogs();
}
