import request from '@/utils/request'

// 查询商品列表
export function listProduct(query) {
  return request({
    url: '/bsapi/product/list',
    method: 'get',
    params: query
  })
}

// 查询商品详细
export function getProduct(productId) {
  return request({
    url: '/bsapi/product/' + productId,
    method: 'get'
  })
}

// 新增商品
export function addProduct(data) {
  return request({
    url: '/bsapi/product',
    method: 'post',
    data: data
  })
}

// 修改商品
export function updateProduct(data) {
  return request({
    url: '/bsapi/product',
    method: 'put',
    data: data
  })
}

// 删除商品
export function delProduct(productId) {
  return request({
    url: '/bsapi/product/' + productId,
    method: 'delete'
  })
}

//添加时展示属性和分类
export function attrsCatelogs(catelogId) {
  return request({
    url: '/bsapi/product/query/attr/catelog/' + catelogId,
    method: 'get'
  })
}

//创建商品
export function createProduct(data) {
  return request({
    url: '/bsapi/product/create',
    method: 'post',
    data
  })
}

//查询商品信息
export function queryProductList(params) {
  return request({
    url: `/bsapi/product/query/list/`,
    method: 'get',
    params
  })
}

//根据商品id获取商品详情
export function detail(productId) {
  return request({
    url: `/bsapi/product/get/${productId}`,
    method: 'get'
  })
}

//修改商品
export function update(data) {
  return request({
    url: '/bsapi/product/edit',
    method: 'put',
    data
  })
}

//删除修改时的图片
export function deleteEditImg(params) {
  return request({
    url: `/bsapi/product/delete/edit/img`,
    method: 'delete',
    params,
  })
}

//店铺点击商品时，展示的数据
export function shopAddProducts() {
  return request({
    url: '/bsapi/product/shop/query',
    method: 'get'
  })
}

//所有的已上架商品
export function upProductList() {
  return request({
    url: '/bsapi/product/up',
    method: 'get'
  })
}

//刷新图片
export function loadSkuImg() {
  return request({
    url: '/product/upload/load/sku/img',
    method: 'get'
  })
}
