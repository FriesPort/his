
import { fileURLToPath, URL } from 'node:url'
import VueSetupExtend from 'vite-plugin-vue-setup-extend'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'



// https://vitejs.dev/config/
export default defineConfig({
  base: '/',
  build: {
    //publicPath: '/',
    outDir: 'dist',//打包后输出
    assetsDir: 'static',//静态文件输出地址
   },
  plugins: [
    vue(),
    VueSetupExtend()
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    host: "0.0.0.0", // ip
    port: 5173,
    proxy: {
      '/api2': {//获取路径中包含/api的请求
        target: 'http://111.230.250.144:9090',//后台服务所在的源
        changeOrigin: true,//修改源
        rewrite: (path) => path.replace(/^\/api2/, '')//api替代为''
      },
      '/api1': {//获取路径中包含/api的请求
        target: 'http://111.230.250.144:8085',//后台服务所在的源
        changeOrigin: true,//修改源
        rewrite: (path) => path.replace(/^\/api1/, '')//api替代为''
      },
      '/api': {//获取路径中包含/api的请求
        target: 'http://111.230.250.144:8081',//后台服务所在的源
        changeOrigin: true,//修改源
        rewrite: (path) => path.replace(/^\/api/, '')//api替代为''
      }
    }
  }
})
