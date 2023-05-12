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
import com.ruoyi.bsapi.domain.MyCollect;
import com.ruoyi.bsapi.service.IMyCollectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 我的收藏Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/collect")
public class MyCollectController extends BaseController
{
    @Autowired
    private IMyCollectService myCollectService;

    /**
     * 查询我的收藏列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:collect:list')")
    @GetMapping("/list")
    public TableDataInfo list(MyCollect myCollect)
    {
        startPage();
        List<MyCollect> list = myCollectService.selectMyCollectList(myCollect);
        return getDataTable(list);
    }

    /**
     * 导出我的收藏列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:collect:export')")
    @Log(title = "我的收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MyCollect myCollect)
    {
        List<MyCollect> list = myCollectService.selectMyCollectList(myCollect);
        ExcelUtil<MyCollect> util = new ExcelUtil<MyCollect>(MyCollect.class);
        util.exportExcel(response, list, "我的收藏数据");
    }

    /**
     * 获取我的收藏详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:collect:query')")
    @GetMapping(value = "/{myCollectId}")
    public AjaxResult getInfo(@PathVariable("myCollectId") Long myCollectId)
    {
        return success(myCollectService.selectMyCollectByMyCollectId(myCollectId));
    }

    /**
     * 新增我的收藏
     */
    @PreAuthorize("@ss.hasPermi('bsapi:collect:add')")
    @Log(title = "我的收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MyCollect myCollect)
    {
        return toAjax(myCollectService.insertMyCollect(myCollect));
    }

    /**
     * 修改我的收藏
     */
    @PreAuthorize("@ss.hasPermi('bsapi:collect:edit')")
    @Log(title = "我的收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MyCollect myCollect)
    {
        return toAjax(myCollectService.updateMyCollect(myCollect));
    }

    /**
     * 删除我的收藏
     */
    @PreAuthorize("@ss.hasPermi('bsapi:collect:remove')")
    @Log(title = "我的收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{myCollectIds}")
    public AjaxResult remove(@PathVariable Long[] myCollectIds)
    {
        return toAjax(myCollectService.deleteMyCollectByMyCollectIds(myCollectIds));
    }
}
