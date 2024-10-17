<template>
  <div class="app-change">
    <a-button @click="toggleTag" :style="{border:'none'}">
      <template #icon>
       <AppstoreOutlined />
      </template>
    </a-button>
    <div class="box" ref="boxRef">
      <transition name="slide">
        <div v-if="showTag" class="change" ref="tagRef">
          <img src="@/assets/logos.png" alt="logo" />
        </div>
      </transition>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted} from 'vue';
const showTag = ref(false);
const tagRef = ref<HTMLElement | null>(null);
const toggleTag = () => {
  showTag.value = !showTag.value;
};

const handleClickOutside = (event: MouseEvent) => {
  const target = event.target as HTMLElement;
  
  if (
    showTag.value &&
    tagRef.value &&
    !tagRef.value.contains(target) &&
    !target.closest('.app-change')
  ) {
    showTag.value = false;
  }
};

onMounted(() => {
  tagRef.value = document.querySelector('.change');
  document.addEventListener('click', handleClickOutside);
});

</script>

<style scoped lang="scss">
.app-change {
  position: relative;
}

.box {
  overflow: hidden;
  position: absolute;
  top: calc(100% + -10px);
  right: calc(100% + -180px);
  width: 320px;
  height: 160px;
  pointer-events: none; /* 防止点击穿透 */
}


.slide-enter-active,
.slide-leave-active {
  transition: all 0.6s;
}

.slide-enter-from, .slide-leave {
  transform: translateY(-100%);
  opacity: 0;
}

.slide-enter, .slide-leave-to {
  transform: translateY(-100%);
  opacity: 0;
}

.change {
  display: flex;
  width: 320px;
  height: 160px;
  border: 1px solid rgba(0, 0, 0, 0.3);
  box-shadow: 0 2px 4px rgba(0.3, 0.3, 0.3, 0.3); 
  background-color: #fff;
}

.change img {
  width: 60px;
  height: 60px;
}
</style>
