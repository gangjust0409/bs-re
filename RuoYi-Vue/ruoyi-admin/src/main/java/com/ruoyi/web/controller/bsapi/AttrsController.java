package com.ruoyi.web.controller.bsapi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.vo.ProductAttrItemVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bsapi.domain.Attrs;
import com.ruoyi.bsapi.service.IAttrsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 属性Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/attrs")
public class AttrsController extends BaseController
{
    @Autowired
    private IAttrsService attrsService;

    //查询当前分类的属性
    @PreAuthorize("@ss.hasPermi('bsapi:catelog:attrs')")
    @GetMapping("/catelog/{catelogId}/{templateProductId}")
    public AjaxResult queryAttrsByCatelogId(@PathVariable Long catelogId,
                                            @PathVariable Long templateProductId){
        List<Attrs> attrs = attrsService.queryAttrsByCatelogId(catelogId,templateProductId);
        return AjaxResult.success(attrs);
    }


    //修改属性状态
    @PreAuthorize("@ss.hasPermi('bsapi:edit:attrs:status')")
    @GetMapping("/update/attrs/status/{catelogId}")
    public AjaxResult updateAttrStatus(@PathVariable Long catelogId, @RequestParam("json") String json) {
        List<ProductAttrItemVo> list = JSON.parseArray(json, ProductAttrItemVo.class);
        attrsService.updateAttrStatus(catelogId,list);
        return AjaxResult.success();
    }


    /**
     * 查询属性列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:attrs:list')")
    @GetMapping("/list")
    public TableDataInfo list(Attrs attrs)
    {
        startPage();
        List<Attrs> list = attrsService.selectAttrsList(attrs);
        return getDataTable(list);
    }

    /**
     * 导出属性列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:attrs:export')")
    @Log(title = "属性", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Attrs attrs)
    {
        List<Attrs> list = attrsService.selectAttrsList(attrs);
        ExcelUtil<Attrs> util = new ExcelUtil<Attrs>(Attrs.class);
        util.exportExcel(response, list, "属性数据");
    }

    /**
     * 获取属性详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:attrs:query')")
    @GetMapping(value = "/{attrId}")
    public AjaxResult getInfo(@PathVariable("attrId") Long attrId)
    {
        return success(attrsService.selectAttrsByAttrId(attrId));
    }

    /**
     * 新增属性
     */
    @PreAuthorize("@ss.hasPermi('bsapi:attrs:add')")
    @Log(title = "属性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Attrs attrs)
    {
        return toAjax(attrsService.insertAttrs(attrs));
    }

    /**
     * 修改属性
     */
    @PreAuthorize("@ss.hasPermi('bsapi:attrs:edit')")
    @Log(title = "属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Attrs attrs)
    {
        return toAjax(attrsService.updateAttrs(attrs));
    }

    /**
     * 删除属性
     */
    @PreAuthorize("@ss.hasPermi('bsapi:attrs:remove')")
    @Log(title = "属性", businessType = BusinessType.DELETE)
	@DeleteMapping("/{attrIds}")
    public AjaxResult remove(@PathVariable Long[] attrIds)
    {
        return toAjax(attrsService.deleteAttrsByAttrIds(attrIds));
    }
}
