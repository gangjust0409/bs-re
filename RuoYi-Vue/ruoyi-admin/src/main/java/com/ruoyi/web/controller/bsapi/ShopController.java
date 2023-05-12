package com.ruoyi.web.controller.bsapi;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.bsapi.vo.ShopReleaseProductVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bsapi.domain.Shop;
import com.ruoyi.bsapi.service.IShopService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商店Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/shop")
public class ShopController extends BaseController
{
    @Autowired
    private IShopService shopService;

    //查询当前商品和当前商品关联的商品
    @PreAuthorize("@ss.hasPermi('bsapi:shops')")
    @GetMapping("/shops")
    public TableDataInfo shopsAndShopReleaseProducts(@RequestParam(required = false) String shopName) {
        List<ShopReleaseProductVo> shopReleaseProductVos = shopService.shopsAndShopReleaseProducts(shopName);
        //重新设置总条数
        int total = shopService.selectCount(shopName);
        return new TableDataInfo(shopReleaseProductVos, total);
    }

    /**
     * 查询商店列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:shop:list')")
    @GetMapping("/list")
    public TableDataInfo list(Shop shop)
    {
        startPage();
        List<Shop> list = shopService.selectShopList(shop);
        return getDataTable(list);
    }

    /**
     * 导出商店列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:shop:export')")
    @Log(title = "商店", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Shop shop)
    {
        List<Shop> list = shopService.selectShopList(shop);
        ExcelUtil<Shop> util = new ExcelUtil<Shop>(Shop.class);
        util.exportExcel(response, list, "商店数据");
    }

    /**
     * 获取商店详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:shop:query')")
    @GetMapping(value = "/{shopId}")
    public AjaxResult getInfo(@PathVariable("shopId") Long shopId)
    {
        return success(shopService.selectShopByShopId(shopId));
    }

    /**
     * 新增商店
     */
    @PreAuthorize("@ss.hasPermi('bsapi:shop:add')")
    @Log(title = "商店", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Shop shop)
    {
        return toAjax(shopService.insertShop(shop));
    }

    /**
     * 修改商店
     */
    @PreAuthorize("@ss.hasPermi('bsapi:shop:edit')")
    @Log(title = "商店", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Shop shop)
    {
        return toAjax(shopService.updateShop(shop));
    }

    /**
     * 删除商店
     */
    @PreAuthorize("@ss.hasPermi('bsapi:shop:remove')")
    @Log(title = "商店", businessType = BusinessType.DELETE)
	@DeleteMapping("/{shopIds}")
    public AjaxResult remove(@PathVariable Long[] shopIds)
    {
        return toAjax(shopService.deleteShopByShopIds(shopIds));
    }
}
