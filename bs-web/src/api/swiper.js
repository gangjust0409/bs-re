import request from '@/utils/request'

export function swiperList() {
    return request({
        url: '/menu/swipers',
        method: 'get'
    })
}

