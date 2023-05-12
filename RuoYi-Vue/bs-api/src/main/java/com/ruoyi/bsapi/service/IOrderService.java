package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.Order;
import com.ruoyi.bsapi.vo.OrderResp;
import com.ruoyi.bsapi.vo.OrderVo;

/**
 * 订单Service接口
 * 
 * @author lqg
 * @date 2023-04-07
 */
public interface IOrderService 
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
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(Long[] orderIds);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(Long orderId);

    /**
     * 提交订单
     *
     * @param token
     */
    String submitOrder(String token);

    /**
     * 查看我购买的订单信息
     *
     * @param token
     * @param status
     * @param orderSn
     * @return
     */
    List<OrderResp> myOrderList(String token, Integer status, String orderSn);

    /**
     * 删除当前订单
     * @param orderSn
     */
    void deleteOrder(String orderSn);

    /**
     * 支付、提交并支付
     *
     * @param token
     * @param payType
     * @param orderSn
     */
    void payOrder(String token, Long payType, String orderSn) throws Exception;

    /**
     * 查询订单信息
     * @param orderSn
     * @param logistics
     * @return
     */
    List<OrderVo> queryAllOrderList(String orderSn, Integer logistics);

    /**
     * 查询订单总条数信息
     * @param orderSn
     * @param logistics
     * @return
     */
    Integer queryOrderCount(String orderSn, Integer logistics);

    /**
     * 更新物流状态
     * @param str
     * @return
     */
    Integer updateLogistics(String str);

    /**
     * 删除订单
     * @param orderSns
     * @return
     */
    Integer deleteOrderByOrderSn(String[] orderSns);
}
