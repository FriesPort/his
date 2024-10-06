<template>
  <div class="bed-card" @drop.prevent="onDrop" @dragover.prevent="onDragOver">
    <p>{{ bedNumber }}</p>
    <p v-if="props.patient">Patient: {{ props.patient.name }}</p>
    <button v-if="props.patient" @click="removePatient">Remove Patient</button>
  </div>
</template>

<script setup lang="ts">
import { message } from "ant-design-vue";
interface Patient {
  id: string;
  name: string;
}
const props = defineProps<{
  bedNumber: string;
  patient: Patient | null;
  onDrop: (patient: Patient | null, bedNumber: string) => void;
}>()
const emit = defineEmits();
const onDrop = (event: DragEvent) => {
  // 获取存储的 props 对象
  const propsJson = event.dataTransfer!.getData('application/json');
  const propsData = JSON.parse(propsJson);

  if (!props.patient) {
    props.onDrop(propsData, props.bedNumber); // 只有当床位没有患者时才进行拖曳操作
  } else {
    message.warning('该床位已分配到患者');
  }
};
const onDragOver = (event: DragEvent) => {
  // 如果床位已经有患者，则阻止拖曳事件
  if (props.patient) {
    event.preventDefault();
  }
};
const removePatient = () => {
  if (props.patient) {
    emit('removePatient', props.patient); // 触发事件并传递患者的id
  }
  // 将床位上的患者信息清除
  props.onDrop(null, props.bedNumber);
};

</script>

<style scoped lang="scss">
.bed-card {
  padding: 10px;
  margin: 5px;
  background-color: #e0e0e0;
  cursor: pointer;
}
</style>
