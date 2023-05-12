import request from '@/utils/request'

// 查询属性和商品关联列表
export function listProduct(query) {
  return request({
    url: '/bsapi/attr/product/list',
    method: 'get',
    params: query
  })
}

// 查询属性和商品关联详细
export function getProduct(attrProductId) {
  return request({
    url: '/bsapi/attr/product/' + attrProductId,
    method: 'get'
  })
}

// 新增属性和商品关联
export function addProduct(data) {
  return request({
    url: '/bsapi/attr/product',
    method: 'post',
    data: data
  })
}

// 修改属性和商品关联
export function updateProduct(data) {
  return request({
    url: '/bsapi/attr/product',
    method: 'put',
    data: data
  })
}

// 删除属性和商品关联
export function delProduct(attrProductId) {
  return request({
    url: '/bsapi/attr/product/' + attrProductId,
    method: 'delete'
  })
}
