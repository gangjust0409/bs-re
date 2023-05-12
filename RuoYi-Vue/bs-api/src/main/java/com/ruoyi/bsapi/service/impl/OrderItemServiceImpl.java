package com.ruoyi.bsapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.OrderItemMapper;
import com.ruoyi.bsapi.domain.OrderItem;
import com.ruoyi.bsapi.service.IOrderItemService;
import org.springframework.util.CollectionUtils;

/**
 * 每一项订单Service业务层处理
 * 
 * @author lqg
 * @date 2023-04-09
 */
@Service
public class OrderItemServiceImpl implements IOrderItemService 
{
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 查询每一项订单
     * 
     * @param orderItemId 每一项订单主键
     * @return 每一项订单
     */
    @Override
    public OrderItem selectOrderItemByOrderItemId(Long orderItemId)
    {
        return orderItemMapper.selectOrderItemByOrderItemId(orderItemId);
    }

    /**
     * 查询每一项订单列表
     * 
     * @param orderItem 每一项订单
     * @return 每一项订单
     */
    @Override
    public List<OrderItem> selectOrderItemList(OrderItem orderItem)
    {
        return orderItemMapper.selectOrderItemList(orderItem);
    }

    /**
     * 新增每一项订单
     * 
     * @param orderItem 每一项订单
     * @return 结果
     */
    @Override
    public int insertOrderItem(OrderItem orderItem)
    {
        return orderItemMapper.insertOrderItem(orderItem);
    }

    /**
     * 修改每一项订单
     * 
     * @param orderItem 每一项订单
     * @return 结果
     */
    @Override
    public int updateOrderItem(OrderItem orderItem)
    {
        return orderItemMapper.updateOrderItem(orderItem);
    }

    /**
     * 批量删除每一项订单
     * 
     * @param orderItemIds 需要删除的每一项订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderItemByOrderItemIds(Long[] orderItemIds)
    {
        return orderItemMapper.deleteOrderItemByOrderItemIds(orderItemIds);
    }

    /**
     * 删除每一项订单信息
     * 
     * @param orderItemId 每一项订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderItemByOrderItemId(Long orderItemId)
    {
        return orderItemMapper.deleteOrderItemByOrderItemId(orderItemId);
    }

    @Override
    public void batchInsertOrderItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            this.insertOrderItem(orderItem);
        }
    }

    @Override
    public void deleteOrderItem(String orderSn) {
        OrderItem orderItemParams = new OrderItem();
        orderItemParams.setOrderSn(orderSn);
        List<OrderItem> orderItems = orderItemMapper.selectOrderItemList(orderItemParams);
        if (!CollectionUtils.isEmpty(orderItems)) {
            for (OrderItem orderItem : orderItems) {
                orderItemMapper.deleteOrderItemByOrderItemId(orderItem.getOrderItemId());
            }
        }
    }

    @Override
    public void batchDeleteOrderItem(String[] orderSns) {
        orderItemMapper.batchDeleteOrderItem(orderSns);
    }
}
