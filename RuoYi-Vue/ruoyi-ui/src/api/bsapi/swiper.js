import request from '@/utils/request'

// 查询轮播图列表
export function listSwiper(query) {
  return request({
    url: '/bsapi/swiper/list',
    method: 'get',
    params: query
  })
}

// 查询轮播图详细
export function getSwiper(swiperId) {
  return request({
    url: '/bsapi/swiper/' + swiperId,
    method: 'get'
  })
}

// 新增轮播图
export function addSwiper(data) {
  return request({
    url: '/bsapi/swiper',
    method: 'post',
    data: data
  })
}

// 修改轮播图
export function updateSwiper(data) {
  return request({
    url: '/bsapi/swiper',
    method: 'put',
    data: data
  })
}

// 删除轮播图
export function delSwiper(swiperId) {
  return request({
    url: '/bsapi/swiper/' + swiperId,
    method: 'delete'
  })
}

//查询轮播图信息
export function swiperList(params) {
  return request({
    url: '/bsapi/swiper/query/list',
    method: 'get',
    params
  })
}
//是否启用轮播图
export function enableSwiper(str) {
  return request({
    url: `/bsapi/swiper/enable/${str}`,
    method: 'get'
  })
}