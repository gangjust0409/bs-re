import request from '@/utils/request'

// 查询属性列表
export function listAttrs(query) {
  return request({
    url: '/bsapi/attrs/list',
    method: 'get',
    params: query
  })
}

// 查询属性详细
export function getAttrs(attrId) {
  return request({
    url: '/bsapi/attrs/' + attrId,
    method: 'get'
  })
}

// 新增属性
export function addAttrs(data) {
  return request({
    url: '/bsapi/attrs',
    method: 'post',
    data: data
  })
}

// 修改属性
export function updateAttrs(data) {
  return request({
    url: '/bsapi/attrs',
    method: 'put',
    data: data
  })
}

// 删除属性
export function delAttrs(attrId) {
  return request({
    url: '/bsapi/attrs/' + attrId,
    method: 'delete'
  })
}
//修改属性状态
export function updateAttrStatus(catelogId, json) {
  return request({
    url: '/bsapi/attrs/update/attrs/status/' + catelogId,
    method: 'get',
    params: {
      json
    }
  })
}

//根据分类id获取属性
export function queryAttrsByCatelogId(catelogId, templateProductId) {
  return request({
    url: `/bsapi/attrs/catelog/${catelogId}/${templateProductId}`,
    method: 'get'
  })
}