<template>
  <div>
    <a-modal v-model:visible="visible" title="患者详情" @ok="handleOk" :afterClose="handleOk">
      <template #footer>
        <a-button key="submit" type="primary" @click="handleOk">确定</a-button>
      </template>
      <p>姓名：{{ props.patient.name }}</p>
      <p>性别：{{ props.patient.gender }}</p>
      <p>年龄：{{ props.patient.age }}</p>
      <p>联系电话：{{ props.patient.phone }}</p>
      <p>住院号：{{ props.patient.admissionNumber }}</p>
      <p>医生：{{ props.patient.doctor }}</p>
      <p>诊断情况：{{ props.patient.illness }}</p>
      <p>入院类型：{{ props.patient.admissionType }}</p>
      <p v-if="patient.admissionType === '预约入院'">预约方式：{{ props.patient.bookType }}</p>
      <p>等待天数：{{ props.patient.waitDay }}天</p>
      <p>病房类型要求：{{ props.patient.roomTypeRequirement }}</p>
      <p>病房性别要求：{{ props.patient.roomGenderRequirement }}</p>
      <p>病房人数要求：{{ props.patient.roomNumberRequirement }}</p>
      <p>急诊：<span v-if="props.patient.isEmergency === 1">是</span><span v-if="props.patient.isEmergency === 0">否</span>
      </p>
      <p>重症：<span v-if="props.patient.isAcute === 1">是</span><span v-if="props.patient.isAcute === 0">否</span></p>
      <p>VIP：<span v-if="props.patient.isVip === 1">是</span><span v-if="props.patient.isVip === 0">否</span></p>
    </a-modal>
  </div>
</template>
<script setup lang="ts">
import { ref, watch } from 'vue';
import type { Patient } from "./types/Bed"

const visible = ref<boolean>(false);
const props = defineProps<{
  visible: boolean;
  closeDialog: Function;
  patient: Patient
}>()
watch(() => props.visible, (newvalue) => {
  if (newvalue) {
    visible.value = true
  }
})
const handleOk = () => {
  visible.value = false;
  props.closeDialog()
};

</script>
