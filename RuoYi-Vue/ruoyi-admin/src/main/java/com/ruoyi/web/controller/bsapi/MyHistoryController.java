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
import com.ruoyi.bsapi.domain.MyHistory;
import com.ruoyi.bsapi.service.IMyHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 我的足迹Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/history")
public class MyHistoryController extends BaseController
{
    @Autowired
    private IMyHistoryService myHistoryService;

    /**
     * 查询我的足迹列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(MyHistory myHistory)
    {
        startPage();
        List<MyHistory> list = myHistoryService.selectMyHistoryList(myHistory);
        return getDataTable(list);
    }

    /**
     * 导出我的足迹列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:history:export')")
    @Log(title = "我的足迹", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MyHistory myHistory)
    {
        List<MyHistory> list = myHistoryService.selectMyHistoryList(myHistory);
        ExcelUtil<MyHistory> util = new ExcelUtil<MyHistory>(MyHistory.class);
        util.exportExcel(response, list, "我的足迹数据");
    }

    /**
     * 获取我的足迹详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:history:query')")
    @GetMapping(value = "/{myHistoryId}")
    public AjaxResult getInfo(@PathVariable("myHistoryId") Long myHistoryId)
    {
        return success(myHistoryService.selectMyHistoryByMyHistoryId(myHistoryId));
    }

    /**
     * 新增我的足迹
     */
    @PreAuthorize("@ss.hasPermi('bsapi:history:add')")
    @Log(title = "我的足迹", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MyHistory myHistory)
    {
        return toAjax(myHistoryService.insertMyHistory(myHistory));
    }

    /**
     * 修改我的足迹
     */
    @PreAuthorize("@ss.hasPermi('bsapi:history:edit')")
    @Log(title = "我的足迹", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MyHistory myHistory)
    {
        return toAjax(myHistoryService.updateMyHistory(myHistory));
    }

    /**
     * 删除我的足迹
     */
    @PreAuthorize("@ss.hasPermi('bsapi:history:remove')")
    @Log(title = "我的足迹", businessType = BusinessType.DELETE)
	@DeleteMapping("/{myHistoryIds}")
    public AjaxResult remove(@PathVariable Long[] myHistoryIds)
    {
        return toAjax(myHistoryService.deleteMyHistoryByMyHistoryIds(myHistoryIds));
    }
}
