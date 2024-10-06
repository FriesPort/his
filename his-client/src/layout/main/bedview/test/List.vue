<template>
  <a-table :columns="columns" :data-source="data" :scroll="{ x: 1500, y: '60vh' }" :pagination="pagination">
    <template #bodyCell="{ column }">
      <template v-if="column.key === 'operation'">
        <button class="edit" @click="showModel">编辑</button>
        <button class="delete" @click="showModel">删除</button>
        <a-modal v-model:visible="visible" title="详情" @ok="handleOk">
          <div v-for="(value, optionIndex) in patient" :key="optionIndex">
            <input type="text" v-model="patient[optionIndex]">

          </div>
        </a-modal>
      </template>
    </template>
  </a-table>
</template>
<script lang="ts" setup>
import { ref, reactive, watch, onMounted } from 'vue';
import { useBedsDataStore } from '@/store/bedData';
import type { TableColumnsType } from 'ant-design-vue';

const bedsDataStore = useBedsDataStore();
const p = []
const patient = {
  patientId: 4,
  patientName: '劳六',
  patientAge: 35,
  patientGender: '女',
  waitDay: 3,
  admissionType: 'Emergency',
  bookType: 'Online',
  telephoneNumber: '123-456-7890',
  roomNumberRequirement: 101,
  roomGenderRequirement: 'Female',
  roomTypeRequirement: 'Single',
  patientStatus: 'Admitted',
  admissionNumber: 'A123',
  diagnosisSituation: 'Fever',
  admissionTime: '2024-04-20 10:00',
  doctor: 'Dr. Smith',
  emergencyAdmission: true,
  severeIllness: false,
  areaName: 'East Wing',
  departmentName: 'Internal Medicine',
  wardName: 'General Ward',
  VIP: true
}
interface Bed {
  id: string;
  number: string;
  bedWardNumber: string;
  campus: string;
  department: string;
  "ward": string;
  "status": string;
  "patient": string;
  "bd": string;
  "admissionType": string;
}

const bedsList: Bed[] = reactive([]);

const columns: TableColumnsType = [
  { title: '姓名', width: 100, dataIndex: 'campus', key: '1' },
  { title: '性别', width: 100, dataIndex: 'department', key: '2', },
  { title: '年龄', dataIndex: 'ward', key: '3', width: 150 },
  { title: '联系电话', dataIndex: 'bedWardNumber', key: '4', width: 150 },
  { title: '住院号', dataIndex: 'status', key: '5', width: 150 },
  { title: '医生', dataIndex: 'patient', key: '6', width: 150 },
  { title: '诊断情况', dataIndex: 'bd', key: '7', width: 150 },
  { title: '等待天数', dataIndex: 'admissionType', key: '8', width: 150 },
  {
    title: 'Action',
    key: 'operation',
    fixed: 'right',
    width: 100,
  },
];

interface DataItem {
  key: number;
  campus: string;
  department: string;
  ward: string;
  bedWardNumber: string;
  status: string;
  patient: string | null;
  bd: string | null;
  admissionType: string
}

const data = ref<DataItem[]>([]);

const pagination = {
  current: parseInt(localStorage.getItem('listPage') || '1'),
  pageSize: parseInt(localStorage.getItem('pageSize') || '10'),
  showQuickJumper: true,
  showLessItems: true,
  total: ref(0),
  showTotal: () => `共 ${pagination.total.value} 个床位，每页${pagination.pageSize}个床位`,
  onChange: (page: number, pageSize: number) => {
    localStorage.setItem('listPage', page.toString());
    localStorage.setItem('pageSize', pageSize.toString());
    pagination.current = page;
    pagination.pageSize = pageSize;

  },
};

onMounted(() => {
  bedsDataStore.getBedsData().then((data) => {
    // 处理获取到的数据
    bedsList.push(...data);
    pagination.total.value = bedsList.length
  });

})
watch(bedsList, (newBedsList) => {
  data.value = newBedsList.map((bed, index) => {
    // 设置默认值
    let status = bed.status || '空闲';
    let patient = bed.patient || '待入住';
    let bd = bed.bd || '无';

    // 如果bed.patient值为空，则令status等于‘空闲’，patient等于‘待入住’
    if (!bed.patient) {
      status = '空闲';
      patient = '待入住';
    }

    // 如果bed.bd的值为空，则bd等于‘无’
    if (!bed.bd) {
      bd = '无';
    }

    return {
      key: index,
      campus: bed.campus,
      department: bed.department,
      ward: bed.ward,
      bedWardNumber: bed.bedWardNumber,
      status: status,
      patient: patient,
      bd: bd,
      admissionType: bed.admissionType
    };
  });
});

const visible = ref<boolean>(false);
const handleOk = () => {
  visible.value = false
}
const showModel = () => {
  visible.value = true
}

</script>

<style scoped lang="scss">
.campus {
  display: flex;
}

.edit,
.delete {
  line-height: 14px;
  width: 40px;
  font-size: 11px;
  text-align: center;
  padding: 2px;
}

.delete {
  color: brown;
}
</style>