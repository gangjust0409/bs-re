import request from '@/utils/request'

// 查询每一项订单列表
export function listItem(query) {
  return request({
    url: '/bsapi/item/list',
    method: 'get',
    params: query
  })
}

// 查询每一项订单详细
export function getItem(orderItemId) {
  return request({
    url: '/bsapi/item/' + orderItemId,
    method: 'get'
  })
}

// 新增每一项订单
export function addItem(data) {
  return request({
    url: '/bsapi/item',
    method: 'post',
    data: data
  })
}

// 修改每一项订单
export function updateItem(data) {
  return request({
    url: '/bsapi/item',
    method: 'put',
    data: data
  })
}

// 删除每一项订单
export function delItem(orderItemId) {
  return request({
    url: '/bsapi/item/' + orderItemId,
    method: 'delete'
  })
}
