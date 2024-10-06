<template>
  <div class="logo">
    <img src="@/assets/logos.png" alt="logo" />
    <span v-show="show" class="title">床位监测管理</span>
  </div>
</template>
<script setup lang="ts">
import { computed, watch, ref } from 'vue'
import { useCollapseStore } from '@/store/collapse/index'
const collapseStore = useCollapseStore()
const isCollapse = computed(() => {
  return collapseStore.getCollapse
})
const show = ref(true)
watch(
  () => isCollapse.value,
  (collapsed: boolean) => {
    if (!collapsed) {
      setTimeout(() => {
        show.value = !collapsed
      }, 250)
    } else {
      show.value = !collapsed
    }
  }
)
</script>
<style scoped lang='scss'>
.logo {
  // background-color: #2b2f3a;
  background-color: #001529;
  height: 50px;
  border: none;
  line-height: 50px;
  display: flex;
  align-items: center;
  padding-left: 18px;
  color: #fff;

  // border-right: 1px solid #e5e5e5;
  img {
    width: 43px;
    height: 43px;
    margin-right: 12px;
    color: #fff;
  }

  .title {
    height: 50px;
    text-align: center;
    color: #fff;
    box-sizing: border-box;
    font-weight: 600;
    line-height: 50px;
    font-size: 20px;
    vertical-align: middle;
  }
}
</style>