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
import com.ruoyi.bsapi.domain.OrderItem;
import com.ruoyi.bsapi.service.IOrderItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 每一项订单Controller
 * 
 * @author lqg
 * @date 2023-04-09
 */
@RestController
@RequestMapping("/bsapi/item")
public class OrderItemController extends BaseController
{
    @Autowired
    private IOrderItemService orderItemService;

    /**
     * 查询每一项订单列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderItem orderItem)
    {
        startPage();
        List<OrderItem> list = orderItemService.selectOrderItemList(orderItem);
        return getDataTable(list);
    }

    /**
     * 导出每一项订单列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:item:export')")
    @Log(title = "每一项订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderItem orderItem)
    {
        List<OrderItem> list = orderItemService.selectOrderItemList(orderItem);
        ExcelUtil<OrderItem> util = new ExcelUtil<OrderItem>(OrderItem.class);
        util.exportExcel(response, list, "每一项订单数据");
    }

    /**
     * 获取每一项订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:item:query')")
    @GetMapping(value = "/{orderItemId}")
    public AjaxResult getInfo(@PathVariable("orderItemId") Long orderItemId)
    {
        return success(orderItemService.selectOrderItemByOrderItemId(orderItemId));
    }

    /**
     * 新增每一项订单
     */
    @PreAuthorize("@ss.hasPermi('bsapi:item:add')")
    @Log(title = "每一项订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderItem orderItem)
    {
        return toAjax(orderItemService.insertOrderItem(orderItem));
    }

    /**
     * 修改每一项订单
     */
    @PreAuthorize("@ss.hasPermi('bsapi:item:edit')")
    @Log(title = "每一项订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderItem orderItem)
    {
        return toAjax(orderItemService.updateOrderItem(orderItem));
    }

    /**
     * 删除每一项订单
     */
    @PreAuthorize("@ss.hasPermi('bsapi:item:remove')")
    @Log(title = "每一项订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderItemIds}")
    public AjaxResult remove(@PathVariable Long[] orderItemIds)
    {
        return toAjax(orderItemService.deleteOrderItemByOrderItemIds(orderItemIds));
    }
}
