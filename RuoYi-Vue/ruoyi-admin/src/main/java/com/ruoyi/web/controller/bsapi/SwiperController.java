package com.ruoyi.web.controller.bsapi;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.bsapi.vo.SwiperProductVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bsapi.domain.Swiper;
import com.ruoyi.bsapi.service.ISwiperService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 轮播图Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/swiper")
public class SwiperController extends BaseController
{
    @Autowired
    private ISwiperService swiperService;

    //启用、禁用
    @PreAuthorize("@ss.hasPermi('bsapi:swiper:enable')")
    @Log(title = "轮播图启用、禁用", businessType = BusinessType.UPDATE)
    @GetMapping("/enable/{str}")
    public AjaxResult editSwiperStatus(@PathVariable String str){
        int res = swiperService.editSwiperStatus(str);
        return success(res);
    }

    //查询所有的轮播图信息
    @PreAuthorize("@ss.hasPermi('bsapi:swipers')")
    @GetMapping("/query/list")
    public TableDataInfo querySwiperList(@RequestParam(required = false) String productTitle){
        //查询数据
        List<SwiperProductVo> productVos = swiperService.selectProductReleaseSwiper(productTitle);
        //查询总条数
        int total = swiperService.selectCount(productTitle);
        return new TableDataInfo(productVos, total);
    }

    /**
     * 查询轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:swiper:list')")
    @GetMapping("/list")
    public TableDataInfo list(Swiper swiper)
    {
        startPage();
        List<Swiper> list = swiperService.selectSwiperList(swiper);
        return getDataTable(list);
    }

    /**
     * 导出轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:swiper:export')")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Swiper swiper)
    {
        List<Swiper> list = swiperService.selectSwiperList(swiper);
        ExcelUtil<Swiper> util = new ExcelUtil<Swiper>(Swiper.class);
        util.exportExcel(response, list, "轮播图数据");
    }

    /**
     * 获取轮播图详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:swiper:query')")
    @GetMapping(value = "/{swiperId}")
    public AjaxResult getInfo(@PathVariable("swiperId") Long swiperId)
    {
        return success(swiperService.selectSwiperBySwiperId(swiperId));
    }

    /**
     * 新增轮播图
     */
    @PreAuthorize("@ss.hasPermi('bsapi:swiper:add')")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Swiper swiper)
    {
        return toAjax(swiperService.insertSwiper(swiper));
    }

    /**
     * 修改轮播图
     */
    @PreAuthorize("@ss.hasPermi('bsapi:swiper:edit')")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Swiper swiper)
    {
        return toAjax(swiperService.updateSwiper(swiper));
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@ss.hasPermi('bsapi:swiper:remove')")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{swiperIds}")
    public AjaxResult remove(@PathVariable Long[] swiperIds)
    {
        return toAjax(swiperService.deleteSwiperBySwiperIds(swiperIds));
    }
}
