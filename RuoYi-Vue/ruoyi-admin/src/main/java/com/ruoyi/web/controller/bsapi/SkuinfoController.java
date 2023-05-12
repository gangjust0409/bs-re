package com.ruoyi.web.controller.bsapi;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.bsapi.domain.Skuinfo;
import com.ruoyi.bsapi.service.ISkuinfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * sku infoController
 * 
 * @author lqg
 * @date 2023-03-17
 */
@RestController
@RequestMapping("/bsapi/skuinfo")
public class SkuinfoController extends BaseController
{
    @Autowired
    private ISkuinfoService skuinfoService;

    /**
     * 查询sku info列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:skuinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Skuinfo skuinfo)
    {
        startPage();
        List<Skuinfo> list = skuinfoService.selectSkuinfoList(skuinfo);
        return getDataTable(list);
    }

    /**
     * 导出sku info列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:skuinfo:export')")
    @Log(title = "sku info", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Skuinfo skuinfo)
    {
        List<Skuinfo> list = skuinfoService.selectSkuinfoList(skuinfo);
        ExcelUtil<Skuinfo> util = new ExcelUtil<Skuinfo>(Skuinfo.class);
        util.exportExcel(response, list, "sku info数据");
    }

    /**
     * 获取sku info详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:skuinfo:query')")
    @GetMapping(value = "/{skuId}")
    public AjaxResult getInfo(@PathVariable("skuId") Long skuId)
    {
        return success(skuinfoService.selectSkuinfoBySkuId(skuId));
    }

    /**
     * 新增sku info
     */
    @PreAuthorize("@ss.hasPermi('bsapi:skuinfo:add')")
    @Log(title = "sku info", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Skuinfo skuinfo)
    {
        return toAjax(skuinfoService.insertSkuinfo(skuinfo));
    }

    /**
     * 修改sku info
     */
    @PreAuthorize("@ss.hasPermi('bsapi:skuinfo:edit')")
    @Log(title = "sku info", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Skuinfo skuinfo)
    {
        return toAjax(skuinfoService.updateSkuinfo(skuinfo));
    }

    /**
     * 删除sku info
     */
    @PreAuthorize("@ss.hasPermi('bsapi:skuinfo:remove')")
    @Log(title = "sku info", businessType = BusinessType.DELETE)
	@DeleteMapping("/{skuIds}")
    public AjaxResult remove(@PathVariable Long[] skuIds)
    {
        return toAjax(skuinfoService.deleteSkuinfoBySkuIds(skuIds));
    }
}
