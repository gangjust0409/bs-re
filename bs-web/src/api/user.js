import request from '@/utils/request'

//注册用户
export function registerFun(data) {
    return request({
        url: '/user/register',
        method: 'post',
        data
    })
}

//发送邮箱验证码
export function sendEmail(email) {
    return request({
        url: `/user/email/send`,
        method: 'get',
        params: {
            email
        }
    })
}

//登录
export function clientLogin(data) {
    return request({
        url: '/user/client/login',
        method: 'post',
        data
    })
}
//TODO 以下是需要登录的接口
//获取最新的用户信息
export function userInfo() {
    return request({
        url: '/user/info',
        method: 'get'
    })
}

//验证账号唯一
export function validateAccountUnique(account) {
    return request({
        url: `/user/validate/account/${account}`,
        method: 'get'
    })
}
//修改用户信息
export function editUser(data) {
    return request({
        url: '/user/edit',
        method: "put",
        data
    })
}

//注销
export function loginOut() {
    return request({
        url: '/user/login/out',
        method: 'get'
    })
}

//新增地址
export function addAddress(data) {
    return request({
        url: '/user/save/address',
        method: 'post',
        data
    })
}

//查询当前用户的地址信息
export function queryUserAddress() {
    return request({
        url: '/user/query/user/address',
        method: 'get'
    })
}

//修改地址信息
export function editAddress(data) {
    return request({
        url: '/user/edit/address',
        method: 'put',
        data
    })
}

//删除地址
export function delAddress(addressId) {
    return request({
        url: `/user/del/address/${addressId}`,
        method: 'delete'
    })
}
//改变地址状态
export function startAddress(addressId) {
    return request({
        url: `/user/start/address/${addressId}`,
        method: 'get'
    })
}

//我的历史
export function myHistoryList() {
    return request({
        url: '/user/my/history',
        method: 'get'
    })
}

//点击收藏
export function clickCollect(productId) {
    return request({
        url: '/user/click/collect',
        method: 'get',
        params: {
            productId
        }
    })
}

//收藏列表
export function collectList() {
    return request({
        url: '/user/collect/list',
        method: 'get'
    })
}

//验证邮箱是否存在
export function validateEmailHasExists(email) {
    return request({
        url: `/user/validate/email/${email}`,
        method: 'get'
    })
}


//验证用户
export function validateUser(username) {
    return request({
      url: `/user/validate/username/${username}`,
      method: 'get'
    })
  }
  