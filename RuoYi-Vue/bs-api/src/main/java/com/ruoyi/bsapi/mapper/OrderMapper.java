package com.ruoyi.bsapi.mapper;

import java.util.List;
import com.ruoyi.bsapi.domain.Order;
import org.apache.ibatis.annotations.Param;

/**
 * 订单Mapper接口
 * 
 * @author lqg
 * @date 2023-04-07
 */
public interface OrderMapper 
{
    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    public Order selectOrderByOrderId(Long orderId);

    /**
     * 查询订单列表
     * 
     * @param order 订单
     * @return 订单集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 删除订单
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(Long orderId);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(Long[] orderIds);

    /**
     * 再删除当前订单
     * @param orderSn
     */
    void deleteOrder(@Param("orderSn") String orderSn);

    /**
     * 查询订单总条数信息
     * @param orderSn
     * @param logistics
     * @return
     */
    Integer queryOrderCount(@Param("orderSn") String orderSn, @Param("logistics") Integer logistics);

    /**
     * 批量删除
     * @param orderSn
     */
    void batchDeleteOrder(@Param("orderSn") String[] orderSn);

    /**
     * 更新物流状态
     * @param orderSn
     * @param logistics
     * @return
     */
    Integer updateLogistics(@Param("orderSn") String orderSn, @Param("logistics") Integer logistics);
}
