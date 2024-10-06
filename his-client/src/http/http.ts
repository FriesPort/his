import axios from 'axios'
import { message } from 'ant-design-vue';
//创建axios实例
const request = axios.create({
  baseURL: "http://localhost:8089", // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 10000 // request timeout
})
//axios请求发送之前处理
//axios请求返回之后处理
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 600 || res.code === 50012 || res.code === 50014) {
        // to re-login
      }
      return Promise.reject(new Error(res.msg || '服务器出错'))
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    message.error(error.msg || '服务器出错')
    return Promise.reject(error)
  }
)
//导出
export default request