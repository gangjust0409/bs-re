import request from '@/utils/request'

// 查询店铺的商品列表
export function listProduct(query) {
  return request({
    url: '/bsapi/shop/product/list',
    method: 'get',
    params: query
  })
}

// 查询店铺的商品详细
export function getProduct(shopProductId) {
  return request({
    url: '/bsapi/shop/product/' + shopProductId,
    method: 'get'
  })
}

// 新增店铺的商品
export function addProduct(data, id) {
  return request({
    url: `/bsapi/shop/product/${id}`,
    method: 'post',
    data: data
  })
}

// 修改店铺的商品
export function updateProduct(data) {
  return request({
    url: '/bsapi/shop/product',
    method: 'put',
    data: data
  })
}

// 删除店铺的商品
export function delProduct(shopProductId) {
  return request({
    url: '/bsapi/shop/product/' + shopProductId,
    method: 'delete'
  })
}
