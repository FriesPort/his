<template>
  <div class="bed-card" @drop.prevent="onDrop" @dragover.prevent="onDragOver">
    <!-- 楼层数、病房号、病房类型 -->
    <div class="title" :style="{ backgroundColor: getTitleColorStyle(bed.roomGender) }">
      <span class="titleLeft">{{ bed.roomNumber }}{{ bed.bedNumber }}</span>
      <span class="wardType1" v-if="bed.roomType === '隔离病房'">隔</span>
      <span class="wardType2" v-if="bed.roomType === '普通病房'">常</span>
      <span class="wardType3" v-if="bed.roomType === 'ICU'">ICU</span>
    </div>
    <!-- 床位信息 -->
    <div class="bed-info">
      <div :class="{ 'bed': bedStatus === true, 'free': bedStatus === false }">
        <div class="bed-count" v-if="!props.bed.patient">{{ props.bed.bedCount }}人间</div>
        <div v-if="props.bed.patient">
          <span>{{ props.bed.patient.name }}</span>
          <span>{{ props.bed.patient.gender }}</span>
          <span v-if="props.bed.patient.isVip === 1">VIP</span>
        </div>
        <button v-if="props.bed.patient" @click="removePatient">取消</button>
        <button class="button-disabled" :disabled="!hasPermission('床位分配')" v-if="props.bed.patient"
          @click="allocation">确认</button>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, reactive, watch, computed } from 'vue';
import { message } from "ant-design-vue";
import type { Bed, Patient } from "./types/Bed"
import { useUserStore } from '@/store/userStore';

const userStore = useUserStore();

const hasPermission = (permission: string) => {
  return userStore.permissions.includes(permission);
};
// 计算属性，根据 roomGender 返回颜色
const getTitleColorStyle = (roomGender: string) => {
  switch (roomGender) {
    case '男性病房':
      return '#c9e6fe';
    case '混合病房':
      return '#e4e4e4';
    default:
      return '#feebfe';
  }
};

const props = defineProps<{
  bed: Bed;
  onDrop: (patient: Patient | null, bedId: number) => void;
  handleAllocationPatient: (bedId: number, patientId: number) => Promise<void>
}>()
const emit = defineEmits();
const onDrop = (event: DragEvent) => {
  // 获取存储的 props 对象
  const propsJson = event.dataTransfer!.getData('application/json');
  const propsData = JSON.parse(propsJson);
  if (!props.bed.patient) {
    props.onDrop(propsData, props.bed.bedId); // 只有当床位没有患者时才进行拖曳操作
  } else {
    message.warning('该床位已分配到患者');
  }
};
const onDragOver = (event: DragEvent) => {
  // 如果床位已经有患者，则阻止拖曳事件
  if (props.bed) {
    event.preventDefault();
  }
};
//取消患者分配
const removePatient = () => {
  if (props.bed.patient) {
    emit('removePatient', props.bed.patient); // 触发事件并传递患者信息
  }
  // 将床位上的患者信息清除
  props.onDrop(null, props.bed.bedId);
};

//确认患者分配
const allocation = () => {
  console.log('分配的id：', props.bed.bedId, props.bed.patient?.patientId as number)
  props.handleAllocationPatient(props.bed.bedId, props.bed.patient?.patientId as number)
}
//判断床位是否有患者从而改变床位信息部分的样式
const bedStatus = ref<boolean>(false);

watch(() => props.bed.patient, (newValue) => {
  if (newValue) {
    bedStatus.value = true;
  } else {
    bedStatus.value = false;
  }
});

</script>
<style scoped lang="scss">
.bed-card {
  width: 120px;
  height: 172px;
  margin: 12px 0 0 20px;

  .title {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 30px;
    font-weight: 700;
    font-size: 14px;
    background-color: #e1dffe;

    .titleLeft {
      margin-left: 4px;
    }

    .wardType1 {
      margin-right: 4px;
      color: #c00f0f;
    }

    .wardType2 {
      margin-right: 4px;
      color: #000;
    }

    .wardType1 {
      margin-right: 4px;
      color: #c00f0f;
    }
  }

  .bed-info {
    height: 142px;
    width: 100%;
    border: 1px solid rgba($color: #8c5ef7, $alpha: .8);

    .bed {
      display: grid;
      /* 使用 Grid 布局 */
      gap: 2px;
      /* 设置子元素之间的间距 */
      text-align: left;
      font-size: 10px;
      overflow: hidden;

      span {
        margin: 12px 6px;
        font-size: 16px;
      }

      .name {
        font-weight: 700;
        font-size: 12px;
      }

      .sex {
        font-size: 10px;
        margin: 0 4px;
        padding: 0 4px;
        border-left: 1px solid #000;
        border-right: 1px solid #000;
      }
    }

    .free {
      width: 100%;
      height: 100%;
      overflow: hidden;
      background: #e4e4e4;
      background-image: url(@/assets/logos.png);
      background-size: 100% auto;
      background-repeat: no-repeat;
      /* 防止背景图片重复 */
      position: relative;

      .bed-count {
        position: absolute;
        // width: 100%;
        text-align: center;
        font-size: 16px;
        color: rgb(107, 47, 249);
        bottom: 10px;
        left: 50%;
        /* 将左边缘设置为父元素的中间位置 */
        transform: translateX(-50%);
        /* 向左移动自身宽度的一半 */
      }
    }
  }
}
</style>