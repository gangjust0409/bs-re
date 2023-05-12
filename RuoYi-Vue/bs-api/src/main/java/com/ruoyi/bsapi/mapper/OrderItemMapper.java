package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.OrderItem;
import org.apache.ibatis.annotations.Param;

/**
 * 每一项订单Mapper接口
 * 
 * @author lqg
 * @date 2023-04-09
 */
public interface OrderItemMapper 
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
     * 删除每一项订单
     * 
     * @param orderItemId 每一项订单主键
     * @return 结果
     */
    public int deleteOrderItemByOrderItemId(Long orderItemId);

    /**
     * 批量删除每一项订单
     * 
     * @param orderItemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderItemByOrderItemIds(Long[] orderItemIds);

    /**
     * 保存每一项订单项
     * @param orderItems
     */
    void batchInsertOrderItems(@Param("orderItems") List<OrderItem> orderItems);

    /**
     * 先批量删除当前订单的订单项
     * @param orderSns
     */
    void batchDeleteOrderItem(@Param("orderSns") String[] orderSns);
}

