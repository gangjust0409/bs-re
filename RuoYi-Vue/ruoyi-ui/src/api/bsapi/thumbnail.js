import request from '@/utils/request'

// 查询缩略图列表
export function listThumbnail(query) {
  return request({
    url: '/bsapi/thumbnail/list',
    method: 'get',
    params: query
  })
}

// 查询缩略图详细
export function getThumbnail(productThumbnailId) {
  return request({
    url: '/bsapi/thumbnail/' + productThumbnailId,
    method: 'get'
  })
}

// 新增缩略图
export function addThumbnail(data) {
  return request({
    url: '/bsapi/thumbnail',
    method: 'post',
    data: data
  })
}

// 修改缩略图
export function updateThumbnail(data) {
  return request({
    url: '/bsapi/thumbnail',
    method: 'put',
    data: data
  })
}

// 删除缩略图
export function delThumbnail(productThumbnailId) {
  return request({
    url: '/bsapi/thumbnail/' + productThumbnailId,
    method: 'delete'
  })
}
