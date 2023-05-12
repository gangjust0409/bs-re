import request from '@/utils/request'

// 查询我的收藏列表
export function listCollect(query) {
  return request({
    url: '/bsapi/collect/list',
    method: 'get',
    params: query
  })
}

// 查询我的收藏详细
export function getCollect(myCollectId) {
  return request({
    url: '/bsapi/collect/' + myCollectId,
    method: 'get'
  })
}

// 新增我的收藏
export function addCollect(data) {
  return request({
    url: '/bsapi/collect',
    method: 'post',
    data: data
  })
}

// 修改我的收藏
export function updateCollect(data) {
  return request({
    url: '/bsapi/collect',
    method: 'put',
    data: data
  })
}

// 删除我的收藏
export function delCollect(myCollectId) {
  return request({
    url: '/bsapi/collect/' + myCollectId,
    method: 'delete'
  })
}
