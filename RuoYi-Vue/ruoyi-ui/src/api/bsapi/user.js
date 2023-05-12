import request from '@/utils/request'
import qs from 'qs'

// 查询用户列表
export function listUser(query) {
  return request({
    url: '/bsapi/user/list',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getUser(userId) {
  return request({
    url: '/bsapi/user/' + userId,
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/bsapi/user',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/bsapi/user',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: '/bsapi/user/' + userId,
    method: 'delete'
  })
}
//多选删除
export function batchUserDelete(ids) {
  return request({
    url: '/bsapi/user/batch/user/delete',
    method: 'delete',
    params: {
      ids
    },
    paramsSerializer: params => qs.stringify(params, { indices: false })
  })
}
