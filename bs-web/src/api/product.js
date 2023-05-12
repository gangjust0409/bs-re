import request from '@/utils/request'

//猜你喜欢
export function productList() {
    return request({
        url: '/menu/love/products',
        method: 'get'
    })
}

//点击详情
export function productDetail(productId) {
    return request({
        url: `/menu/detail/${productId}`,
        method: 'get'
    })
}

//商品检索
export function searchProduct(params) {
    return request({
        url: '/menu/search',
        method: 'get',
        params
    })
}

//添加购物车
export function addCartProduct(data) {
    return request({
        url: '/menu/add/cart',
        method: 'post',
        data
    })
}

//查看当前用户的购物车
export function queryCurrentUserCarts() {
    return request({
        url: '/menu/carts',
        method: 'get'
    })
}

//改变购物车的选中状态和改变购物车的数量
export function changeCartCheckedCount(shopId, params) {
    return request({
        url: `/menu/change/cart/${shopId}`,
        method: 'get',
        params
    })
}

//点击全选或者点击店铺选中
export function checkedAllOrShop(params) {
    return request({
        url: '/menu/checked/all/shop',
        method: 'get',
        params
    })
}

//删除购物车
export function delCart(cartId) {
    return request({
        url: `/menu/del/cart`,
        method: 'delete',
        params: {
            cartId
        }
    })
}

//结算
export function cartResultProducts() {
    return request({
        url: '/menu/cart/result',
        method: 'get'
    })
}

//提交订单
export function submitProduct(params) {
    return request({
        url: '/menu/submit/order',
        method: 'get',
        params
    })
}

//查看我购买的宝贝
export function queryMyBuy(params) {
    return request({
        url: '/menu/my/order/list',
        method: 'get',
        params
    })
}

//删除订单信息
export function deleteOrderData(orderSn) {
    return request({
        url: `/menu/delete/order/${orderSn}`,
        method: 'delete'
    })
}
//支付、提交并支付
export function pay(params) {
    return request({
        url: `/menu/pay/order`,
        method: 'get',
        params
    })
}

