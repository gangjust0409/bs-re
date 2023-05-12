import request from '@/utils/request'

// 查询详情图片列表
export function listDetail(query) {
  return request({
    url: '/bsapi/detail/list',
    method: 'get',
    params: query
  })
}

// 查询详情图片详细
export function getDetail(productDetailId) {
  return request({
    url: '/bsapi/detail/' + productDetailId,
    method: 'get'
  })
}

// 新增详情图片
export function addDetail(data) {
  return request({
    url: '/bsapi/detail',
    method: 'post',
    data: data
  })
}

// 修改详情图片
export function updateDetail(data) {
  return request({
    url: '/bsapi/detail',
    method: 'put',
    data: data
  })
}

// 删除详情图片
export function delDetail(productDetailId) {
  return request({
    url: '/bsapi/detail/' + productDetailId,
    method: 'delete'
  })
}
