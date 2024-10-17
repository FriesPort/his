import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import Antd from "ant-design-vue";
import 'ant-design-vue/dist/reset.css';
import { ConfigProvider } from 'ant-design-vue';
import zhCN from 'ant-design-vue/es/locale/zh_CN.js';
import '@/iconfot/iconfont.css';
//引入图标
import * as Icons from "@ant-design/icons-vue";
import router from "./router/index";
import { createPinia } from 'pinia';
import { createPersistedState } from 'pinia-persistedstate-plugin';
import piniaPluginPersist from 'pinia-plugin-persistedstate';
import emitter from "@/utils/emitter";
// import ElementPlus from 'element-plus'
// import locale from 'element-plus/dist/locale/zh-cn.js'
// import 'element-plus/dist/index.css'



const app = createApp(App);

app.config.errorHandler = function (err, vm, info) {
  // 处理错误
  console.error('ErrorHandler:', err, info);
  // 可选: 返回一个消息，它将被用作窗口的异常信息
  return '自定义错误信息';
};
//注册图标组件
Object.keys(Icons).forEach((key) => {
  app.component(key, Icons[key as keyof typeof Icons])
})

const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist);
pinia.use(piniaPluginPersist);
// app.use(ElementPlus, { locale });
app.use(Antd);
app.use(router);
app.use(pinia);

// 设置 ConfigProvider 的全局配置
// app.use(ConfigProvider, {
//   locale: zhCN,
// });
app.mount('#app')

