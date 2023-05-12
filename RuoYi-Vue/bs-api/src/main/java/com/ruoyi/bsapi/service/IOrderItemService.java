package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.OrderItem;

/**
 * 每一项订单Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface IOrderItemService 
{
    /**
     * 查询每一项订单
     * 
     * @param orderItemId 每一项订单主键
     * @return 每一项订单
     */
    public OrderItem selectOrderItemByOrderItemId(Long orderItemId);

    /**
     * 查询每一项订单列表
     * 
     * @param orderItem 每一项订单
     * @return 每一项订单集合
     */
    public List<OrderItem> selectOrderItemList(OrderItem orderItem);

    /**
     * 新增每一项订单
     * 
     * @param orderItem 每一项订单
     * @return 结果
     */
    public int insertOrderItem(OrderItem orderItem);

    /**
     * 修改每一项订单
     * 
     * @param orderItem 每一项订单
     * @return 结果
     */
    public int updateOrderItem(OrderItem orderItem);

    /**
     * 批量删除每一项订单
     * 
     * @param orderItemIds 需要删除的每一项订单主键集合
     * @return 结果
     */
    public int deleteOrderItemByOrderItemIds(Long[] orderItemIds);

    /**
     * 删除每一项订单信息
     * 
     * @param orderItemId 每一项订单主键
     * @return 结果
     */
    public int deleteOrderItemByOrderItemId(Long orderItemId);

    /**
     * 保存每一项订单项
     * @param orderItems
     */
    void batchInsertOrderItems(List<OrderItem> orderItems);

    /**
     * 删除子项
     * @param orderSn
     */
    void deleteOrderItem(String orderSn);

    /**
     * 先批量删除当前订单的订单项
     * @param orderSn
     */
    void batchDeleteOrderItem(String[] orderSn);
}
