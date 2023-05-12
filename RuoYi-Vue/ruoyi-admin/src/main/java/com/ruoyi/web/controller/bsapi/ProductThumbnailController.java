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
import com.ruoyi.bsapi.domain.ProductThumbnail;
import com.ruoyi.bsapi.service.IProductThumbnailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 缩略图Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/thumbnail")
public class ProductThumbnailController extends BaseController
{
    @Autowired
    private IProductThumbnailService productThumbnailService;

    /**
     * 查询缩略图列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:thumbnail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductThumbnail productThumbnail)
    {
        startPage();
        List<ProductThumbnail> list = productThumbnailService.selectProductThumbnailList(productThumbnail);
        return getDataTable(list);
    }

    /**
     * 导出缩略图列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:thumbnail:export')")
    @Log(title = "缩略图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductThumbnail productThumbnail)
    {
        List<ProductThumbnail> list = productThumbnailService.selectProductThumbnailList(productThumbnail);
        ExcelUtil<ProductThumbnail> util = new ExcelUtil<ProductThumbnail>(ProductThumbnail.class);
        util.exportExcel(response, list, "缩略图数据");
    }

    /**
     * 获取缩略图详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:thumbnail:query')")
    @GetMapping(value = "/{productThumbnailId}")
    public AjaxResult getInfo(@PathVariable("productThumbnailId") Long productThumbnailId)
    {
        return success(productThumbnailService.selectProductThumbnailByProductThumbnailId(productThumbnailId));
    }

    /**
     * 新增缩略图
     */
    @PreAuthorize("@ss.hasPermi('bsapi:thumbnail:add')")
    @Log(title = "缩略图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductThumbnail productThumbnail)
    {
        return toAjax(productThumbnailService.insertProductThumbnail(productThumbnail));
    }

    /**
     * 修改缩略图
     */
    @PreAuthorize("@ss.hasPermi('bsapi:thumbnail:edit')")
    @Log(title = "缩略图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductThumbnail productThumbnail)
    {
        return toAjax(productThumbnailService.updateProductThumbnail(productThumbnail));
    }

    /**
     * 删除缩略图
     */
    @PreAuthorize("@ss.hasPermi('bsapi:thumbnail:remove')")
    @Log(title = "缩略图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productThumbnailIds}")
    public AjaxResult remove(@PathVariable Long[] productThumbnailIds)
    {
        return toAjax(productThumbnailService.deleteProductThumbnailByProductThumbnailIds(productThumbnailIds));
    }
}
