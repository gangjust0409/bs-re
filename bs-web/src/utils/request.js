import axios from 'axios'
import { getAuth } from '@/utils/auth'

const http = axios.create({
    baseURL: 'http://localhost:8001/app',
    timeout: 210000
})

http.interceptors.request.use(config => {
    //是否登录
    if (getAuth()) {
        config.headers['X-Token'] = getAuth();
    }
    return config;
}, error => {
    Promise.reject(error)
})

http.interceptors.response.use(response => {
    if (response.status === 200) {
        return response.data;
    }
}, error => {
    Promise.reject(error)
})


export default http;