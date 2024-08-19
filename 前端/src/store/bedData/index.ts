import { defineStore } from "pinia";
import axios from "axios";
import { reactive } from "vue";
import { message } from "ant-design-vue";

export const useBedsDataStore = defineStore('beds', () => {

  const bedsList = reactive([])
  async function getBedsData() {

    try {
      let { data } = await axios.get('http://localhost:3000/bedsData');
      return data;
    } catch (error: any) {
      error.data = {};
      // 请求出错时的处理逻辑
      if (error && error.response) {
        switch (error.response.status) {
          case 400:
            error.data.msg = '错误请求';
            message.error(error.data.msg)
            break
          case 401:
            error.data.msg = '未授权，请重新登录';
            message.error(error.data.msg)
            break
          case 403:
            error.data.msg = '拒绝访问';
            message.error(error.data.msg)
            break
          case 404:
            error.data.msg = '请求错误,未找到该资源';
            message.error(error.data.msg)
            break
          case 405:
            error.data.msg = '请求方法未允许';
            message.error(error.data.msg)
            break
          case 408:
            error.data.msg = '请求超时';
            message.error(error.data.msg)
            break
          case 500:
            error.data.msg = '服务器端出错';
            message.error(error.data.msg)
            break
          case 501:
            error.data.msg = '网络未实现';
            message.error(error.data.msg)
            break
          case 502:
            error.data.msg = '网络错误';
            message.error(error.data.msg)
            break
          case 503:
            error.data.msg = '服务不可用';
            message.error(error.data.msg)
            break
          case 504:
            error.data.msg = '网络超时';
            message.error(error.data.msg)
            break
          case 505:
            error.data.msg = 'http版本不支持该请求';
            message.error(error.data.msg)
            break
          default:
            error.data.msg = `连接错误${error.response.status}`;
            message.error(error.data.msg)
        }
      } else {
        error.data.msg = "连接到服务器失败";
        message.error(error.data.msg)
      }
      return Promise.reject(error)
    }
  }
  return { getBedsData }
});