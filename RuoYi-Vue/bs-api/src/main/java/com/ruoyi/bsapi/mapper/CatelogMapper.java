package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.Catelog;
import org.apache.ibatis.annotations.Param;

/**
 * 分类Mapper接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface CatelogMapper 
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
     * 删除分类
     * 
     * @param catelogId 分类主键
     * @return 结果
     */
    public int deleteCatelogByCatelogId(Long catelogId);

    /**
     * 批量删除分类
     * 
     * @param catelogIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCatelogByCatelogIds(Long[] catelogIds);

}
