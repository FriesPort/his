<template>
  <div class="sider-bg">
    <Collapse></Collapse>
  </div>
</template>
<script setup lang='ts'>
import { reactive, ref } from 'vue';
import Collapse from './Collapse.vue';
</script>
<style scoped lang='scss'>
.sider-bg {
  justify-content: right;
  padding: 20px;
  color: #fff;
  display: flex;
  align-items: center;
  position: absolute;
  bottom: 0;
  right: 0;
}
</style>