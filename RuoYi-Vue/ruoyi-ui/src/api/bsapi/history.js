import request from '@/utils/request'

// 查询我的足迹列表
export function listHistory(query) {
  return request({
    url: '/bsapi/history/list',
    method: 'get',
    params: query
  })
}

// 查询我的足迹详细
export function getHistory(myHistoryId) {
  return request({
    url: '/bsapi/history/' + myHistoryId,
    method: 'get'
  })
}

// 新增我的足迹
export function addHistory(data) {
  return request({
    url: '/bsapi/history',
    method: 'post',
    data: data
  })
}

// 修改我的足迹
export function updateHistory(data) {
  return request({
    url: '/bsapi/history',
    method: 'put',
    data: data
  })
}

// 删除我的足迹
export function delHistory(myHistoryId) {
  return request({
    url: '/bsapi/history/' + myHistoryId,
    method: 'delete'
  })
}
