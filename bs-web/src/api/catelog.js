import request from '@/utils/request'

export function catelogList() {
    return request({
        url: '/menu/catelog',
        method: 'get'
    })
}

