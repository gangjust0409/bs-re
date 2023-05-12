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
import com.ruoyi.bsapi.domain.ShopProduct;
import com.ruoyi.bsapi.service.IShopProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 店铺的商品Controller
 * 
 * @author lqg
 * @date 2023-03-26
 */
@RestController
@RequestMapping("/bsapi/shop/product")
public class ShopProductController extends BaseController
{
    @Autowired
    private IShopProductService shopProductService;

    /**
     * 查询店铺的商品列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopProduct shopProduct)
    {
        startPage();
        List<ShopProduct> list = shopProductService.selectShopProductList(shopProduct);
        return getDataTable(list);
    }

    /**
     * 导出店铺的商品列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:export')")
    @Log(title = "店铺的商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopProduct shopProduct)
    {
        List<ShopProduct> list = shopProductService.selectShopProductList(shopProduct);
        ExcelUtil<ShopProduct> util = new ExcelUtil<ShopProduct>(ShopProduct.class);
        util.exportExcel(response, list, "店铺的商品数据");
    }

    /**
     * 获取店铺的商品详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:query')")
    @GetMapping(value = "/{shopProductId}")
    public AjaxResult getInfo(@PathVariable("shopProductId") Long shopProductId)
    {
        return success(shopProductService.selectShopProductByShopProductId(shopProductId));
    }

    /**
     * 新增店铺的商品
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:add')")
    @Log(title = "店铺的商品", businessType = BusinessType.INSERT)
    @PostMapping("{status}")
    public AjaxResult add(@PathVariable Long status,@RequestBody ShopProduct shopProduct)
    {
        return toAjax(shopProductService.insertShopProduct(status,shopProduct));
    }

    /**
     * 修改店铺的商品
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:edit')")
    @Log(title = "店铺的商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopProduct shopProduct)
    {
        return toAjax(shopProductService.updateShopProduct(shopProduct));
    }

    /**
     * 删除店铺的商品
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:remove')")
    @Log(title = "店铺的商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{shopProductIds}")
    public AjaxResult remove(@PathVariable Long[] shopProductIds)
    {
        return toAjax(shopProductService.deleteShopProductByShopProductIds(shopProductIds));
    }
}
