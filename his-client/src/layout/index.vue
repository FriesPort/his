<template>
  <a-layout class="layout">
    <a-layout-sider v-model:collapsed="collapsed" :trigger="null" class="sider">
      <MenuBar />
      <Sider />
    </a-layout-sider>
    <a-layout>
      <a-layout-header class="header">
        <Header />
      </a-layout-header>
      <a-layout-content class="content">
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script setup lang="ts">
import { computed } from 'vue';
import MenuBar from "./menu/MenuBar.vue";
import Sider from "./menu/Sider.vue";
import Header from "./header/Header.vue";
import { useCollapseStore } from '@/store/collapse';

const collapseStore = useCollapseStore()

//collapsed
const collapsed = computed(() => {
  return collapseStore.getCollapse
})
</script>
<style scoped lang="scss">
.layout {
  display: flex;
  background-color: #e97e7e;
  width: 100vw;
  height: 100vh;
}

.sider {
  background-color: #001529;
}

.header {
  width: 100%;
  height: 40px;
  background-color: #fff;
  z-index: 3;
}

.content {
  margin: 8px;
  background-color: #fff;
  z-index: 2;
  overflow-y: auto;
  /* 当视图高度小于1080时出现垂直滚动条 */
}

// 根据视图宽度决定是否显示水平滚动条
@media (min-width: 1920px) {
  .layout {
    overflow-x: hidden;
  }
}

@media (min-height: 1080px) {
  .content {
    overflow-y: hidden;
    /* 当视图高度大于等于1080时隐藏垂直滚动条 */
  }
}
</style>