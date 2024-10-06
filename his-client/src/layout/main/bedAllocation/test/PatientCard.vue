<template>
  <div class="patient-card" :draggable="true" @dragstart="onDragStart">
    {{ name }}
  </div>
</template>

<script setup lang="ts">

const props = defineProps<{
  name: string;
  id: string;
}>()

const onDragStart = (event: DragEvent) => {

  // event.dataTransfer!.setData('text/plain', props.id);
  // 将整个 props 对象转换为 JSON 字符串并设置为拖曳操作中的数据
  event.dataTransfer!.setData('application/json', JSON.stringify(props));

};
</script>

<style scoped lang="scss">
.patient-card {
  cursor: move;
  padding: 10px;
  margin-bottom: 5px;
  background-color: #f0f0f0;
}
</style>
