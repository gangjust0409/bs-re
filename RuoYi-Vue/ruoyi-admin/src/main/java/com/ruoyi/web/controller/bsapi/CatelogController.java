package com.ruoyi.web.controller.bsapi;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bsapi.domain.Catelog;
import com.ruoyi.bsapi.service.ICatelogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分类Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/catelog")
public class CatelogController extends BaseController
{
    @Autowired
    private ICatelogService catelogService;

    //查询所有的分类
    @PreAuthorize("@ss.hasPermi('bsapi:catelog:tree:list')")
    @Log(title = "分类树形菜单", businessType = BusinessType.OTHER)
    @GetMapping("/tree/list")
    public R treeListCatelogs(){
        List<Catelog> catelogs = catelogService.treeList();
        return R.ok(catelogs);
    }

    /**
     * 查询分类列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:catelog:list')")
    @GetMapping("/list")
    public TableDataInfo list(Catelog catelog)
    {
        startPage();
        List<Catelog> list = catelogService.selectCatelogList(catelog);
        return getDataTable(list);
    }

    /**
     * 导出分类列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:catelog:export')")
    @Log(title = "分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Catelog catelog)
    {
        List<Catelog> list = catelogService.selectCatelogList(catelog);
        ExcelUtil<Catelog> util = new ExcelUtil<Catelog>(Catelog.class);
        util.exportExcel(response, list, "分类数据");
    }

    /**
     * 获取分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:catelog:query')")
    @GetMapping(value = "/{catelogId}")
    public AjaxResult getInfo(@PathVariable("catelogId") Long catelogId)
    {
        return success(catelogService.selectCatelogByCatelogId(catelogId));
    }

    /**
     * 新增分类
     */
    @PreAuthorize("@ss.hasPermi('bsapi:catelog:add')")
    @Log(title = "分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Catelog catelog)
    {
        return toAjax(catelogService.insertCatelog(catelog));
    }

    /**
     * 修改分类
     */
    @PreAuthorize("@ss.hasPermi('bsapi:catelog:edit')")
    @Log(title = "分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Catelog catelog)
    {
        return toAjax(catelogService.updateCatelog(catelog));
    }

    /**
     * 删除分类
     */
    @PreAuthorize("@ss.hasPermi('bsapi:catelog:remove')")
    @Log(title = "分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{catelogIds}")
    public AjaxResult remove(@PathVariable Long[] catelogIds)
    {
        return toAjax(catelogService.deleteCatelogByCatelogIds(catelogIds));
    }
}
