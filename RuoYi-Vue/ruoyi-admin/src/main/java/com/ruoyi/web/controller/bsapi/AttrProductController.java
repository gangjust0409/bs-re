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
import com.ruoyi.bsapi.domain.AttrProduct;
import com.ruoyi.bsapi.service.IAttrProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 属性和商品关联Controller
 * 
 * @author lqg
 * @date 2023-03-17
 */
@RestController
@RequestMapping("/bsapi/attr/product")
public class AttrProductController extends BaseController
{
    @Autowired
    private IAttrProductService attrProductService;

    /**
     * 查询属性和商品关联列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttrProduct attrProduct)
    {
        startPage();
        List<AttrProduct> list = attrProductService.selectAttrProductList(attrProduct);
        return getDataTable(list);
    }

    /**
     * 导出属性和商品关联列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:export')")
    @Log(title = "属性和商品关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttrProduct attrProduct)
    {
        List<AttrProduct> list = attrProductService.selectAttrProductList(attrProduct);
        ExcelUtil<AttrProduct> util = new ExcelUtil<AttrProduct>(AttrProduct.class);
        util.exportExcel(response, list, "属性和商品关联数据");
    }

    /**
     * 获取属性和商品关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:query')")
    @GetMapping(value = "/{attrProductId}")
    public AjaxResult getInfo(@PathVariable("attrProductId") Long attrProductId)
    {
        return success(attrProductService.selectAttrProductByAttrProductId(attrProductId));
    }

    /**
     * 新增属性和商品关联
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:add')")
    @Log(title = "属性和商品关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AttrProduct attrProduct)
    {
        return toAjax(attrProductService.insertAttrProduct(attrProduct));
    }

    /**
     * 修改属性和商品关联
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:edit')")
    @Log(title = "属性和商品关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AttrProduct attrProduct)
    {
        return toAjax(attrProductService.updateAttrProduct(attrProduct));
    }

    /**
     * 删除属性和商品关联
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:remove')")
    @Log(title = "属性和商品关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{attrProductIds}")
    public AjaxResult remove(@PathVariable Long[] attrProductIds)
    {
        return toAjax(attrProductService.deleteAttrProductByAttrProductIds(attrProductIds));
    }
}
