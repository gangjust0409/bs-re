package com.ruoyi.web.controller.bsapi;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.bsapi.vo.OrderVo;
import io.swagger.models.auth.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bsapi.domain.Order;
import com.ruoyi.bsapi.service.IOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单Controller
 * 
 * @author lqg
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/bsapi/order")
public class OrderController extends BaseController
{
    @Autowired
    private IOrderService orderService;

    //删除订单
    @DeleteMapping("/delete/{orderSns}")
    @PreAuthorize("@ss.hasPermi('bsapi:order:delete:ordersn')")
    public AjaxResult deleteOrderByOrderSn(@PathVariable String[] orderSns){
        return AjaxResult.success(orderService.deleteOrderByOrderSn(orderSns));
    }


    //更新物流状态
    @PutMapping("/update/logistics/{str}")
    @PreAuthorize("@ss.hasPermi('bsapi:order:update:logistics')")
    public AjaxResult updateLogistics(@PathVariable String str){
        return AjaxResult.success(orderService.updateLogistics(str));
    }

    //查询所有的订单信息
    @GetMapping("/query/list")
    @PreAuthorize("@ss.hasPermi('bsapi:order:query:list')")
    public TableDataInfo queryAllOrderList(@RequestParam(required = false) String orderSn,
                                           @RequestParam(required = false) Integer logistics){
        //查询订单信息
        List<OrderVo> orders = orderService.queryAllOrderList(orderSn, logistics);
        //查询订单总条数信息
        Integer total = orderService.queryOrderCount(orderSn, logistics);
        return new TableDataInfo(orders, total);
    }

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(Order order)
    {
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Order order)
    {
        List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(orderService.selectOrderByOrderId(orderId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('bsapi:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Order order)
    {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('bsapi:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Order order)
    {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('bsapi:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(orderService.deleteOrderByOrderIds(orderIds));
    }
}
