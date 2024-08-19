<template>
  <div class="notification-container">
    <a-button @click="toggleNotification" :style="{border:'none'}">
      <template #icon>
        <BellOutlined />
      </template>
    </a-button>

    <transition name="slide">
      <div v-if="showNotification" class="notification">
        <div class="top">
          <div class="topLeft">消息提醒</div>
          <div class="topRight">
            <a-button :style="{ border: 'none' }">
              <template #icon>
                <right-circle-filled />
              </template>
            </a-button>
            <a-button :style="{ border: 'none' }">
              <template #icon>
                <right-circle-filled />
              </template>
            </a-button>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const showNotification = ref(false);

const toggleNotification = () => {
  showNotification.value = !showNotification.value;
};
</script>

<style scoped>
.notification-container {
  position: relative;
}



.slide-enter-active, .slide-leave-active {
  transition: transform 0.6s;
}

.slide-enter-from, .slide-leave {
  transform: translateX(100%);
}

.slide-enter, .slide-leave-to {
  transform: translateX(100%);
}

.notification {
  position: absolute;
  top: calc(100% + -10px); /* 计算相对于按钮的正下方 */
  right: calc(100% + -12vw); /* 向右移动一个盒子的宽度 */
  width: 24vw;
  height: 94vh;
  background-color: #fff;
  border: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  color: blue;
  z-index: 3;

  & .top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 60px;
    font-size: 24px;
    border-bottom: 1px solid #000;

    & .topLeft {
      height: 60px;
      line-height: 60px;
      margin: auto 16px;
    }
    & .topRight {
      height: 60px;
      line-height: 60px;
      margin: auto 16px;
    }
  }
}
</style>
