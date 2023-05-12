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
import com.ruoyi.bsapi.domain.ProductDetail;
import com.ruoyi.bsapi.service.IProductDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 详情图片Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/detail")
public class ProductDetailController extends BaseController
{
    @Autowired
    private IProductDetailService productDetailService;

    /**
     * 查询详情图片列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:detail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductDetail productDetail)
    {
        startPage();
        List<ProductDetail> list = productDetailService.selectProductDetailList(productDetail);
        return getDataTable(list);
    }

    /**
     * 导出详情图片列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:detail:export')")
    @Log(title = "详情图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductDetail productDetail)
    {
        List<ProductDetail> list = productDetailService.selectProductDetailList(productDetail);
        ExcelUtil<ProductDetail> util = new ExcelUtil<ProductDetail>(ProductDetail.class);
        util.exportExcel(response, list, "详情图片数据");
    }

    /**
     * 获取详情图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:detail:query')")
    @GetMapping(value = "/{productDetailId}")
    public AjaxResult getInfo(@PathVariable("productDetailId") Long productDetailId)
    {
        return success(productDetailService.selectProductDetailByProductDetailId(productDetailId));
    }

    /**
     * 新增详情图片
     */
    @PreAuthorize("@ss.hasPermi('bsapi:detail:add')")
    @Log(title = "详情图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductDetail productDetail)
    {
        return toAjax(productDetailService.insertProductDetail(productDetail));
    }

    /**
     * 修改详情图片
     */
    @PreAuthorize("@ss.hasPermi('bsapi:detail:edit')")
    @Log(title = "详情图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductDetail productDetail)
    {
        return toAjax(productDetailService.updateProductDetail(productDetail));
    }

    /**
     * 删除详情图片
     */
    @PreAuthorize("@ss.hasPermi('bsapi:detail:remove')")
    @Log(title = "详情图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productDetailIds}")
    public AjaxResult remove(@PathVariable List<Long> productDetailIds)
    {
        Long[] ids = (Long[]) productDetailIds.toArray();
        return toAjax(productDetailService.deleteProductDetailByProductDetailIds(ids));
    }
}
