<template>
  <a-breadcrumb>
    <a-breadcrumb-item v-for="item in tabs">{{ item.meta.title }}</a-breadcrumb-item>
  </a-breadcrumb>
</template>
<script setup lang='ts'>
import { ref, reactive, Ref, onMounted, watch } from 'vue'
import { RouteLocationMatched, useRoute } from 'vue-router';
//当前路由
const route = useRoute()

//定义面包屑导航数据
const tabs: Ref<RouteLocationMatched[]> = ref([])
//获取面包屑导航数据
const getBerd = () => {
  let mached = route.matched.filter(item => item.meta && item.meta.title);
  //设置面包屑导航数据
  tabs.value = mached
}
onMounted(() => {
  getBerd()
})

//监听路由数据，更新面包屑数据
watch(() => route.path, () => {
  getBerd()
})
</script>
<style scoped lang='scss'></style>