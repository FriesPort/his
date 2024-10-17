<template>
  <a-button v-if="isFullScreen" @click="exitFullScreen" :style="{ border: 'none' }">
    <template #icon>
      <FullscreenExitOutlined />
    </template>
  </a-button>
  <a-button v-else @click="enterFullScreen" :style="{ border: 'none' }">
    <template #icon>
      <FullscreenOutlined />
    </template>
  </a-button>
</template>

<script setup lang="ts">
import { ref } from "vue";

const isFullScreen = ref(false);

const enterFullScreen = () => {
  isFullScreen.value = true;
  if (document.documentElement.requestFullscreen) {
    document.documentElement.requestFullscreen();
  } else if (document.documentElement.requestFullscreen) {
    document.documentElement.requestFullscreen();
  } else if (document.documentElement.requestFullscreen) {
    document.documentElement.requestFullscreen();
  } else if (document.documentElement.requestFullscreen) {
    document.documentElement.requestFullscreen();
  }
};

const exitFullScreen = () => {
  isFullScreen.value = false;
  if (document.exitFullscreen) {
    document.exitFullscreen();
  } else if ((document as any).mozCancelFullScreen) {
    (document as any).mozCancelFullScreen();
  } else if ((document as any).webkitExitFullscreen) {
    (document as any).webkitExitFullscreen();
  } else if ((document as any).msExitFullscreen) {
    (document as any).msExitFullscreen();
  }
};

</script>