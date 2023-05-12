import request from '@/utils/request'

// 查询分类列表
export function listCatelog(query) {
  return request({
    url: '/bsapi/catelog/list',
    method: 'get',
    params: query
  })
}

// 查询分类详细
export function getCatelog(catelogId) {
  return request({
    url: '/bsapi/catelog/' + catelogId,
    method: 'get'
  })
}

// 新增分类
export function addCatelog(data) {
  return request({
    url: '/bsapi/catelog',
    method: 'post',
    data: data
  })
}

// 修改分类
export function updateCatelog(data) {
  return request({
    url: '/bsapi/catelog',
    method: 'put',
    data: data
  })
}

// 删除分类
export function delCatelog(catelogId) {
  return request({
    url: '/bsapi/catelog/' + catelogId,
    method: 'delete'
  })
}

//分类树形列表
export function catelogTreelist() {
  return request({
    url: '/bsapi/catelog//tree/list',
    method: 'get'
  })
}