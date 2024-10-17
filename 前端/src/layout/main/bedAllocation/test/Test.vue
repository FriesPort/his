<template>
  <div>
    <h2>Patients</h2>
    <PatientCard v-for="patient in patients" :key="patient.id" :name="patient.name" :id="patient.id" />

    <h2>Beds</h2>
    <BedCard v-for="bed in beds" :key="bed.number" :bedNumber="bed.number" :patient="bed.patient" :onDrop="handleDrop"
      @removePatient="handleRemovePatient" />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import PatientCard from './PatientCard.vue';
import BedCard from './BedCard.vue';

interface Patient {
  id: string;
  name: string;
}

const patients = reactive<Patient[]>([
  { id: 'a', name: '张三' },
  { id: 'b', name: '李四' },
  { id: 'c', name: '王五' },
  { id: 'd', name: '老六' }
]);

interface Bed {
  number: string;
  patient: Patient | null;
}

const beds = reactive<Bed[]>([
  { number: '01', patient: null },
  { number: '02', patient: null },
  { number: '03', patient: null }
]);


const handleDrop = (Gpatient: Patient | null, bedNumber: string) => {
  const bedIndex = beds.findIndex(bed => bed.number === bedNumber);

  if (bedIndex !== -1 && Gpatient) {
    // if (beds[bedIndex].patient) {
    //   beds[bedIndex].patient.id = Gpatient.id;
    // }
    // else {
    //   // 如果床位没有患者，则创建新的患者对象并分配给床位
    //   beds[bedIndex].patient = {
    //     id: Gpatient.id,
    //     name: patients.find(patient => patient.id === Gpatient.id)?.name || ''
    //   };
    // }

    //如果床位没有患者，则创建新的患者对象并分配给床位
    beds[bedIndex].patient = {
      id: Gpatient.id,
      name: patients.find(patient => patient.id === Gpatient.id)?.name || '',
    };


    // 从患者列表中移除该患者
    const patientIndex = patients.findIndex(patient => patient.id === Gpatient.id);
    if (patientIndex !== -1) {
      patients.splice(patientIndex, 1);
    }
  } else {
    beds[bedIndex].patient = null
  }
};

const handleRemovePatient = (patient: Patient) => {
  // 将患者添加回患者列表
  patients.push({ id: patient.id, name: patient.name });
};
</script>
