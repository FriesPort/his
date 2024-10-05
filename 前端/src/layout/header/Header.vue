<template>
  <div class="header-bg">
    <Bred></Bred>
    <div class="header-right">
      <FullScreen />
      <AppChange />
      <Message />
      <User />
    </div>
  </div>
</template>
<script setup lang='ts'>
import { ref, reactive } from 'vue'
import Bred from './Bred.vue'
import FullScreen from "./FullScreen.vue";
import AppChange from "./AppChange.vue";
import Message from "./Message.vue";
import User from "./User.vue";
</script>
<style scoped lang='scss'>
.header-bg {
  color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-right {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  width: 250px;
  height: 40px;
}
</style>