import request from '@/utils/request'

// 查询商店列表
export function listShop(query) {
  return request({
    url: '/bsapi/shop/shops',
    method: 'get',
    params: query
  })
}

// 查询商店详细
export function getShop(shopId) {
  return request({
    url: '/bsapi/shop/' + shopId,
    method: 'get'
  })
}

// 新增商店
export function addShop(data) {
  return request({
    url: '/bsapi/shop',
    method: 'post',
    data: data
  })
}

// 修改商店
export function updateShop(data) {
  return request({
    url: '/bsapi/shop',
    method: 'put',
    data: data
  })
}

// 删除商店
export function delShop(shopId) {
  return request({
    url: '/bsapi/shop/' + shopId,
    method: 'delete'
  })
}
