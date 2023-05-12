import { createWebHistory, createRouter } from 'vue-router'

const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        component: () => import('@/pages/Home.vue')
    },
    {
        path: '/search',
        component: () => import('@/pages/SearchProduct.vue')
    },
    {
        path: '/detail',
        component: () => import('@/pages/DetailProduct.vue')
    },
    {
        path: '/login',
        component: () => import('@/pages/Login.vue')
    },
    {
        path: '/register',
        component: () => import('@/pages/Register.vue')
    },
    {
        path: '/my',
        component: () => import('@/pages/my/Home.vue'),
        "children": [
            {
                path: '/',
                redirect: '/my/user/info'
            },
            {
                path: 'user/info',
                component: () => import('@/pages/my/UserInfo.vue')
            },
            {
                path: 'address',
                component: () => import('@/pages/my/Address.vue')
            },
            {
                path: 'collect',
                component: () => import('@/pages/my/Collect.vue')
            },
            {
                path: 'history',
                component: () => import('@/pages/my/History.vue')
            },
            {
                path: 'buy',
                component: () => import('@/pages/my/Buy.vue')
            },
        ]
    },
    {
        path: '/cart',
        component: () => import('@/pages/Cart.vue')
    },
    {
        path: '/pay/result',
        component: () => import('@/pages/PayResult.vue')
    },
    {
        path: '/pay/success',
        component: () => import('@/pages/PaySuccess.vue')
    },
    {
        path: '/pay/error',
        component: () => import('@/pages/PayError.vue')
    },
];



export default createRouter({
    routes,
    history: createWebHistory(),
})