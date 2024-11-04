<template>
  <div class="patient-card" :draggable="true" @dragstart="onDragStart" @click="handleClick">
    <span class="number">{{ number }}.</span>
    <span class="name">{{ patientName }}</span>
    <span class="sex">{{ patientGender }}</span>
    <div class="bed-vip" v-if="VIP === 1"></div>
    <span class="vip-text" v-if="VIP === 1">VIP</span>
    <div class="bed-icon">
      <span class="iconfont icon-jizhen" v-if="patient.isEmergency === 1"></span>
      <span v-if="patient.isAcute === 1" class="severeIllness">重</span>
    </div>
    <slot></slot>
  </div>
</template>
<script setup lang="ts">
import type { Patient } from "./types/Bed"
const props = defineProps<{
  patient: Patient;
  number: number;
  patientName: string;
  patientId: number;
  patientGender: string;
  VIP: number;
  openDialog: Function;
}>()

const emit = defineEmits()
const onDragStart = (event: DragEvent) => {
  // 将整个 props 对象转换为 JSON 字符串并设置为拖曳操作中的数据
  event.dataTransfer!.setData('application/json', JSON.stringify(props.patient));

};
const handleClick = () => {
  // 触发父组件传递过来的 openDialog 方法
  props.openDialog();
};

</script>
<style scoped lang="scss">
.patient-card {
  width: 160px;
  height: 40px;
  padding: 8px;
  border: 1px solid rgba($color: #000000, $alpha: 0.5);
  margin-bottom: 8px;
  cursor: pointer;
  position: relative;

  .bed-vip {
    width: 0;
    height: 0;
    border-left: 40px solid transparent;
    /* 左侧边框，可调整大小 */
    border-top: 40px solid rgb(255, 128, 00);
    /* 底部边框，可调整大小和颜色 */
    position: absolute;
    top: 0;
    /* 使盒子位于父级盒子的底部 */
    right: 0;
    /* 使盒子位于父级盒子的右侧 */
    overflow: visible;
    /* 允许文字溢出盒子边界 */
    white-space: nowrap;
    /* 防止文字换行 */
  }

  .vip-text {
    color: white;
    /* 文字颜色 */
    font-size: 14px;
    /* 文字大小 */
    position: absolute;
    top: 0;
    /* 使盒子位于父级盒子的底部 */
    right: 2px;
    /* 使盒子位于父级盒子的右侧 */
  }

  .bed-icon {
    position: absolute;
    bottom: -6px;
    /* 使盒子位于父级盒子的底部 */
    right: 20px;
    /* 使盒子位于父级盒子的右侧 */
    padding-left: 4px;
    margin-top: 6px;
    margin-bottom: 4px;

    .severeIllness {
      padding-bottom: 2px;
      margin-bottom: 6px;
      margin-left: 4px;
      font-weight: 700;
      font-size: 13px;
      color: #fd6700;
    }
  }

  .number,
  .name,
  .sex {
    margin: auto 6px;
  }
}
</style>