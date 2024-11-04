//定制请求的实例

//导入axios  npm install axios
import axios, { AxiosResponse } from 'axios';
import { message } from 'ant-design-vue'
//定义返回值类型
export interface Result<T = any> {
    code: number;
    message: string;
    data: T;
}
//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:9000';
//let baseURL = '/his'
// let baseURL = 'http://8.138.173.253:9090'
//let baseURL =''
// if(import.meta.env.MODE === 'staging') { // 预备模式
//     baseURL = process.env.NODE_ENV === 'development' ? import.meta.env.VITE_DEV_SERVER : import.meta.env.VITE_SERVER
// } else { // development或production模式
//     baseURL = ''
// }
const baseURL = '/api';
const instance = axios.create({
    baseURL: baseURL,
    //withCredentials:true 
})

import { useTokenStore } from '@/store/token.ts';
//添加请求拦截器
instance.interceptors.request.use(
    (config) => {
        //请求前的回调
        //添加token
        const tokenStore = useTokenStore();
        config.headers.userId = 'u1'
        //判断有无token
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token
        } else {
            router.push('/login')
        }
        return config;
    },
    (err) => {
        //请求错误的回调
        Promise.reject(err)
    }
)

// import {useRouter} from 'vue-router'
// const router=useRouter();
import router from '@/router'
//添加响应拦截器
instance.interceptors.response.use(
    (response: AxiosResponse) => {

        // 假设 response.data 是 Result 类型
        const result = response.data as Result<any>;

        // 判断业务状态码
        if (result.code === 200 || result.code === 1) {
            // 创建一个新的 AxiosResponse 对象，将 Result 对象作为 data 属性的值
            return {
                ...response, // 展开其他属性
                data: result.data // 只将 data 属性设置为 Result 对象的 data 属性
            };
        } else {
            // 显示错误消息
            message.error(result.message || result.data.loginstatus || '服务异常');
            // 抛出错误，拒绝 Promise
            return Promise.reject(new Error(result.message || result.data.loginstatus || '服务异常'));
        }

    },
    (error: any) => {
        // error.data = {};
        // if (error && error.response) {
        //     switch (error.response.status) {
        //         case 400:
        //             error.data.message = '错误请求';
        //             // message.error(error.data.message)
        //             break
        //         case 401:
        //             error.data.message = '未授权，请重新登录';
        //             // message.error(error.data.message)
        //             router.push('/login')
        //             break
        //         case 403:
        //             error.data.message = '拒绝访问';
        //             // message.error(error.data.message)
        //             break
        //         case 404:
        //             error.data.message = '请求错误,未找到该资源';
        //             message.error(error.data.message)
        //             break
        //         case 405:
        //             error.data.message = '请求方法未允许';
        //             // message.error(error.data.message)
        //             break
        //         case 408:
        //             error.data.message = '请求超时';
        //             // message.error(error.data.message)
        //             break
        //         case 500:
        //             error.data.message = '服务器端出错';
        //             // message.error(error.data.message)
        //             break
        //         case 501:
        //             error.data.message = '网络未实现';
        //             // message.error(error.data.message)
        //             break
        //         case 502:
        //             error.data.message = '网络错误';
        //             // message.error(error.data.message)
        //             break
        //         case 503:
        //             error.data.message = '服务不可用';
        //             // message.error(error.data.message)
        //             break
        //         case 504:
        //             error.data.message = '网络超时';
        //             // message.error(error.data.message)
        //             break
        //         case 505:
        //             error.data.message = 'http版本不支持该请求';
        //             // message.error(error.data.message)
        //             break
        //         default:
        //             error.data.message = `连接错误${error.response.status}`;
        //         // message.error(error.data.message)
        //     }
        // } else {
        //     // message.error("服务异常")
        // }

        return Promise.reject(error);//异步的状态转化成失败的状态
    }
)

export default instance;

