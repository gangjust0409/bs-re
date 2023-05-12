import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/bsapi/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(orderId) {
  return request({
    url: '/bsapi/order/' + orderId,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/bsapi/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/bsapi/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(orderId) {
  return request({
    url: '/bsapi/order/' + orderId,
    method: 'delete'
  })
}

//查询所有的订单信息
export function queryOrderList(params){
  return request({
    url: `/bsapi/order/query/list`,
    method: 'get',
    params
  })
}
//更新物流状态
export function updateLogistics(str){
  return request({
    url: `/bsapi/order/update/logistics/${str}`,
    method: 'put'
  })
}
//删除订单
export function deleteOrderByOrderSn(orderSns){
  return request({
    url: `/bsapi/order/delete/${orderSns}`,
    method: 'delete'
  })
}

