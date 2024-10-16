<template>
  <a-config-provider :locale="zhcn">
    <div class="app">
      <router-view />
    </div>
  </a-config-provider>
</template>
<script setup lang="ts">
import { ref } from 'vue';
import zhCN from 'ant-design-vue/es/locale/zh_CN';
// 使用 ref 创建一个响应式引用
const zhcn = ref(zhCN);

</script>
<style scoped lang="scss"></style>