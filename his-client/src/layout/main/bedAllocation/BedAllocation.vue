<template>
  <div class="top">
    <div class="select">
      <div>
        <span>院区</span>
        <a-select v-model:value="selectedCampusName" @change="handleChange" style="width: 120px;">
          <a-select-option v-for="(campusItem, index) in campusData" :key="campusItem.campusId"
            :value="campusItem.campusName">
            <span>{{ campusItem.campusName }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>病区</span>
        <a-select v-model:value="selectedOfficeName" style="width: 100px">
          <a-select-option v-for="office in filteredOfficeList" :key="office.officeId" :value="office.officeName">
            <span>{{ office.officeName }}</span>
          </a-select-option>
        </a-select>
        <a-select v-model:value="selectedWardName" style="width: 120px">
          <a-select-option v-for="ward in filteredWardList" :key="ward.wardId" :value="ward.wardName">
            <span>{{ ward.wardName }}</span>
          </a-select-option>
        </a-select>
      </div>
    </div>
    <div class="statistics">
      <div class="card-view">
        <i class="severeIllness">重</i>重症患者
        <span class="iconfont icon-jizhen">急诊患者</span>
        <div class="bed-type-color1"></div><span>男性病房</span>
        <div class="bed-type-color2"></div><span>女性病房</span>
        <div class="bed-type-color3"></div><span>混合病房</span>
      </div>
      <!-- 内一区共50床，有患者床位45，其中已入院40，待出院5；无患者床位5，其中已留床2，空闲3 -->
    </div>
  </div>
  <div class="content">
    <div class="patients">
      <div class="title">待入院患者</div>
      <div class="edit">
        <button :class="{ 'active': active === true }" @click="recommend()">推荐</button>
        <button :class="{ 'active': active === false }" @click="more()">更多</button>
      </div>
      <div class="patients-content" v-if="sift">
        <Patients v-for="(patient, index) in patients" :key="patient.patientId" :patient="patient"
          :patientName="patient.name" :patientId="patient.patientId" :patientGender="patient.gender" :number="index + 1"
          :VIP="patient.isVip" :openDialog="openDialog" :dialogVisible="dialogVisible[index]"
          @click="openDialog(index)">
          <Dialog :visible="dialogVisible[index]" :closeDialog="closeDialog" :patient="patient" />
        </Patients>
      </div>
      <div class="patients-content" v-else-if="!sift">
        <Patients v-for="(patient, index) in morePatients" :key="patient.patientId" :patient="patient"
          :patientName="patient.name" :patientId="patient.patientId" :patientGender="patient.gender" :number="index + 1"
          :VIP="patient.isVip" :openDialog="openDialog" :dialogVisible="dialogVisible[index]"
          @click="openDialog(index)">
          <Dialog :visible="dialogVisible[index]" :closeDialog="closeDialog" :patient="patient" />
        </Patients>
      </div>
    </div>
    <div class="beds">
      <div class="title">床位调度</div>
        <span>空闲床位</span>
      <div class="bedcontent">
        <div class="freebeds">
          <Beds v-for="(bed, index) in beds" :key="bed.bedId" :bed="bed" :onDrop="handleDrop"
            @removePatient="handleRemovePatient" :handleAllocationPatient="handleAllocationPatient" />
        </div>
      </div>
    </div>
    <div class="bed-overview">
      <!-- <BedOverview /> -->
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, reactive, computed, watch, onMounted } from 'vue';
import Dialog from "./Dialog.vue";
import Beds from "@/layout/main/bedAllocation/Beds.vue";
import Patients from "@/layout/main/bedAllocation/Patients.vue";
import BedOverview from "@/layout/main/bedAllocation/BedOverview.vue";
import type { Bed, Patient } from "./types/Bed"
import type { Ward, Office, Campus } from "@/layout/main/bedview/types/Bed.ts";
import { getBedsData, getWardsData } from '@/api/bedView/bedData.ts';
import { getPatientsData } from '@/api/patientView/patientData'
import axios from 'axios';
import { message } from 'ant-design-vue';
import { bedAllocationRequest, getBedRequest } from '@/api/bedAllocation/bedAllocation';
import { getText } from '@/api/text/text';

const recordCampusId = ref<number>()
const recordOfficeId = ref<number>()
const recordWardId = ref<number>()
const campusData = reactive<Campus[]>([])
// 用于 v-model 的响应式变量，存储选中的 campusName
const selectedCampusName = ref('');
const selectedCampusId = ref<number>();
// 处理选择变化的函数
const handleChange = (value: string) => {
  // 找到匹配选中 campusName 的 campusItem
  const selectedCampus = campusData.find(
    item => item.campusName === value
  );
  // 如果找到了，更新 recordCampusId 的值
  if (selectedCampus) {
    selectedCampusId.value = selectedCampus.campusId;
    selectedCampusName.value = value; // 更新 campusName
  }
};

// 基于 selectedCampusName 过滤出的 officeList
const filteredOfficeList = ref<Office[]>([]);

// 监听 selectedCampusName 的变化，更新 filteredOfficeList
watch(selectedCampusId, (newCampusId) => {
  const campus = campusData.find(c => c.campusId === newCampusId);
  if (campus) {
    filteredOfficeList.value = campus.offices;
    // 假设第一个 office 的名称被设置为默认值
    selectedOfficeId.value = filteredOfficeList.value[0].officeId; // 更新 officeId
    selectedOfficeName.value = filteredOfficeList.value[0].officeName; // 更新 officeName
  } else {
    filteredOfficeList.value = []; // 重置 filteredOfficeList 为空数组
  }
});
const selectedOfficeId = ref<number>();
const selectedOfficeName = ref('');
// 基于 selectedOfficeName 过滤出的 wardList
const filteredWardList = ref<Ward[]>([]);
// 监听 selectedOfficeName 的变化，更新 filteredWardList
watch(selectedOfficeName, (newOfficeName) => {
  const office = filteredOfficeList.value.find(o => o.officeName === newOfficeName);
  if (office) {
    filteredWardList.value = office.wards;
    // 假设第一个 ward 的名称被设置为默认值
    selectedWardId.value = filteredWardList.value[0].wardId; // 更新 wardId
    selectedWardName.value = filteredWardList.value[0].wardName; // 更新 wardName
  } else {
    filteredWardList.value = []; // 如果没有匹配的 office，重置 filteredWardList 为空数组
  }
});
const selectedWardId = ref<number>();
const selectedWardName = ref('');
// 监听 selectedWardId 的变化，更新 recordWardId
watch(selectedWardId, (newWardId) => {
  // 更新 recordWardId
  recordWardId.value = newWardId;
  // 由于 selectedWardId 的变化，更新 params 并重新获取数据
  let params = {
    wardId: newWardId,
    status: '待入院', // 假设您想根据新 wardId 获取状态为 '待入院' 的患者
  };

  // 重新获取患者数据
  getPatients(params);
  getBeds(newWardId as number)
});
// 监听 selectedWardId 的变化，更新 recordWardId
watch(selectedWardName, (newWardName) => {
  // 更新 recordWardId
  const ward = filteredWardList.value.find(item => item.wardName === newWardName)
  recordWardId.value = ward?.wardId;
  let params = {
    wardId: ward?.wardId,
    status: '待入院', // 假设您想根据新 wardId 获取状态为 '待入院' 的患者
  };
  // 重新获取患者数据
  getPatients(params);
  getBeds(ward?.wardId as number)
});
onMounted(async () => {
  // let { data } = await axios.get('http://localhost:3000/beds');
  let { data } = await getWardsData();
  campusData.splice(0, campusData.length, ...data); // 替换现有数据
  filteredOfficeList.value = campusData[0].offices,
    filteredWardList.value = campusData[0].offices[0].wards,
    selectedCampusName.value = campusData[0].campusName;
  selectedOfficeName.value = campusData[0].offices[0].officeName;
  selectedWardName.value = campusData[0].offices[0].wards[0].wardName;
  recordCampusId.value = campusData[0].campusId;
  recordOfficeId.value = campusData[0].offices[0].officeId;
  recordWardId.value = campusData[0].offices[0].wards[0].wardId;
  let params = {
    wardId: campusData[0].offices[0].wards[0].wardId,
    status: '待入院'
  }
  await getPatients(params)
  await getBeds(recordWardId.value)
  // await getText()
})

const getBeds = async (wardId: number) => {
  console.log('分配', wardId)
  let { data } = await getBedRequest(wardId as number);
  beds.splice(0, beds.length, ...data.map((item: any) => ({
    ...item,
    patient: null // 为每个 bed 项设置 patient 为 null
  })));
}
const status = ['待入院', '正床', '男床', '女床'];
const getPatients = async (params: any) => {
  console.log('患者', params)
  let { data } = await getPatientsData(params);
  if (data.length > 10) {
    patients.splice(0, patients.length, ...data.slice(0, 10));
    morePatients.splice(0, morePatients.length, ...data.slice(10));
  } else {
    patients.splice(0, patients.length, ...data);
    morePatients.splice(0, morePatients.length)
  }
}

const active = ref(true)

const sift = ref(true)

const recommend = () => {
  active.value = true
  sift.value = true
}
const more = () => {
  active.value = false
  sift.value = false
}

const patients = reactive<Patient[]>([
  {
    patientId: 1,
    name: '张三',
    age: 35,
    gender: '男',
    waitDay: 3,
    admissionType: 'Emergency',
    bookType: 'Online',
    phone: '123-456-7890',
    roomNumberRequirement: 1,
    roomGenderRequirement: '男性病房',
    roomTypeRequirement: '普通病房',
    status: 'Admitted',
    admissionNumber: 'A123',
    illness: 'Fever',
    admissionTime: '2024/04/20',
    doctor: 'Dr. Smith',
    isEmergency: 1,
    isAcute: 1,
    isVip: 1,
    createTime: '2024/04/18'
  },
  {
    patientId: 2,
    name: '李四',
    age: 35,
    gender: '女',
    waitDay: 3,
    admissionType: 'Emergency',
    bookType: 'Online',
    phone: '123-456-7890',
    roomNumberRequirement: 1,
    roomGenderRequirement: '女性病房',
    roomTypeRequirement: '普通病房',
    status: 'Admitted',
    admissionNumber: 'A123',
    illness: 'Fever',
    admissionTime: '2024-04-20 10:00',
    doctor: 'Dr. Smith',
    isEmergency: 1,
    isAcute: 0,
    isVip: 1,
    createTime: '2024/04/18'
  },
  {
    patientId: 3,
    name: '王五',
    age: 35,
    gender: '男',
    waitDay: 3,
    admissionType: 'Emergency',
    bookType: 'Online',
    phone: '123-456-7890',
    roomNumberRequirement: 1,
    roomGenderRequirement: '男性病房',
    roomTypeRequirement: '隔离病房',
    status: 'Admitted',
    admissionNumber: 'A123',
    illness: 'Fever',
    admissionTime: '2024-04-20 10:00',
    doctor: 'Dr. Smith',
    isEmergency: 1,
    isAcute: 0,
    isVip: 1,
    createTime: '2024/04/18'
  },
  {
    patientId: 4,
    name: '劳六',
    age: 35,
    gender: '女',
    waitDay: 3,
    admissionType: 'Emergency',
    bookType: 'Online',
    phone: '123-456-7890',
    roomNumberRequirement: 3,
    roomGenderRequirement: '女性病房',
    roomTypeRequirement: '普通病房',
    status: 'Admitted',
    admissionNumber: 'A123',
    illness: 'Fever',
    admissionTime: '2024-04-20 10:00',
    doctor: 'Dr. Smith',
    isEmergency: 1,
    isAcute: 0,
    isVip: 1,
    createTime: '2024/04/18'
  },
]);

const morePatients = reactive<Patient[]>([
  {
    patientId: 101,
    name: 'Alice1',
    age: 35,
    gender: '男',
    waitDay: 3,
    admissionType: 'Emergency',
    bookType: 'Online',
    phone: '123-456-7890',
    roomNumberRequirement: 101,
    roomGenderRequirement: 'Female',
    roomTypeRequirement: 'Single',
    status: 'Admitted',
    admissionNumber: 'A123',
    illness: 'Fever',
    admissionTime: '2024-04-20 10:00',
    doctor: 'Dr. Smith',
    isEmergency: 1,
    isAcute: 0,
    isVip: 1,
    createTime: '2024/04/18'
  },
  {
    patientId: 102,
    name: 'Alice2',
    age: 35,
    gender: '男',
    waitDay: 3,
    admissionType: 'Emergency',
    bookType: 'Online',
    phone: '123-456-7890',
    roomNumberRequirement: 101,
    roomGenderRequirement: 'Female',
    roomTypeRequirement: 'Single',
    status: 'Admitted',
    admissionNumber: 'A123',
    illness: 'Fever',
    admissionTime: '2024-04-20 10:00',
    doctor: 'Dr. Smith',
    isEmergency: 1,
    isAcute: 0,
    isVip: 1,
    createTime: '2024/04/18'
  },
  {
    patientId: 103,
    name: 'Alice3',
    age: 35,
    gender: '女',
    waitDay: 3,
    admissionType: 'Emergency',
    bookType: 'Online',
    phone: '123-456-7890',
    roomNumberRequirement: 101,
    roomGenderRequirement: 'Female',
    roomTypeRequirement: 'Single',
    status: 'Admitted',
    admissionNumber: 'A123',
    illness: 'Fever',
    admissionTime: '2024-04-20 10:00',
    doctor: 'Dr. Smith',
    isEmergency: 0,
    isAcute: 0,
    isVip: 1,
    createTime: '2024/04/18'
  },
  {
    patientId: 104,
    name: 'Alice4',
    age: 35,
    gender: '女',
    waitDay: 3,
    admissionType: 'Emergency',
    bookType: 'Online',
    phone: '123-456-7890',
    roomNumberRequirement: 101,
    roomGenderRequirement: 'Female',
    roomTypeRequirement: 'Single',
    status: 'Admitted',
    admissionNumber: 'A123',
    illness: 'Fever',
    admissionTime: '2024-04-20 10:00',
    doctor: 'Dr. Smith',
    isEmergency: 0,
    isAcute: 0,
    isVip: 0,
    createTime: '2024/04/18'
  },
])

const beds = reactive<Bed[]>([
  {
    bedId: 1,
    roomNumber: '101房',
    bedNumber: '01床',
    bedType: '男床',
    roomType: '隔离病房',
    bedCount: 1,
    roomGender: '男性病房',
    patient: null // Assign a patient from patients array
  },
  {
    bedId: 2,
    roomNumber: '102房',
    bedNumber: '01床',
    bedType: '正床',
    roomType: '普通病房',
    bedCount: 4,
    roomGender: '混合病房',
    patient: null // Assign a patient from patients array
  },
  {
    bedId: 3,
    roomNumber: '102房',
    bedNumber: '02床',
    bedType: '女床',
    roomType: '普通病房',
    bedCount: 3,
    roomGender: '女性病房',
    patient: null // Assign a patient from patients array
  },
]);

const dialogVisible = reactive(Array(patients.length).fill(false));

const openDialog = (index: number) => {
  dialogVisible.fill(false); // 关闭所有弹窗
  // 点击事件处理程序，用于控制弹框的显示与隐藏
  dialogVisible[index] = true;
};

const closeDialog = () => {
  dialogVisible.fill(false); // 关闭所有弹窗
}

const handleDrop = (Gpatient: Patient | null, bedId: number) => {
  if (Gpatient !== null) {
    const bed = beds.find(bed => bed.bedId === bedId);
    if (bed?.bedType === '男床' && Gpatient?.roomGenderRequirement !== '男性病房') {
      message.warning('病房性别不符合')
      return
    }
    if (bed?.bedType === '女床' && Gpatient?.roomGenderRequirement !== '女性病房') {
      message.warning('病房性别不符合')
      return
    }
    if (bed?.bedType === '正床' && Gpatient?.roomGenderRequirement !== '混合病房') {
      message.warning('病房性别不符合')
      return
    }
    if (bed?.roomType === '隔离病房' && Gpatient?.roomTypeRequirement !== '隔离病房') {
      message.warning('病房类型不符合')
      return
    }
    if (bed?.roomType === '普通病房' && Gpatient?.roomTypeRequirement !== '普通病房') {
      message.warning('病房类型不符合')
      return
    }
    if (bed?.roomType === 'ICU' && Gpatient?.roomTypeRequirement !== 'ICU') {
      message.warning('病房类型不符合')
      return
    }
    if(Gpatient?.roomNumberRequirement !== null){
    if (bed?.bedCount !== Gpatient?.roomNumberRequirement) {
      message.warning('病房人数不符合')
      return
    }
    return
  }
    const bedIndex = beds.findIndex(bed => bed.bedId === bedId);
    if (bedIndex !== -1 && Gpatient) {
      //如果床位没有患者，则创建新的患者对象并分配给床位
      //推荐
      if (sift.value) {
        beds[bedIndex].patient = Gpatient
        // 从患者列表中移除该患者
        const patientIndex = patients.findIndex(patient => patient.patientId === Gpatient.patientId);
        patients.splice(patientIndex, 1);
      }
      //更多
      else if (!sift.value) {
        beds[bedIndex].patient = Gpatient;
        // 从患者列表中移除该患者
        const patientIndex = morePatients.findIndex(patient => patient.patientId === Gpatient.patientId);
        if (patientIndex !== -1) {
          morePatients.splice(patientIndex, 1);
        }
      }
    }
  } else {
    const bedIndex = beds.findIndex(bed => bed.bedId === bedId);
    beds[bedIndex].patient = null //从床位中移除患者
  }
};

//取消分配患者
const handleRemovePatient = (patient: Patient) => {
  // 将患者添加回患者列表
  if (sift.value) {
    patients.push(patient);
  }
  else if (!sift.value) {
    morePatients.push(patient);
  }
};

//确认分配患者
const handleAllocationPatient = async (bedId: number, patientId: number) => {
  let params = {
    bedId: bedId,
    patientId: patientId
  }
  let result = await bedAllocationRequest(params)
  const bedIndex = beds.findIndex(bed => bed.bedId === bedId);
    beds[bedIndex].patient = null //从床位中移除患者
    message.success('床位分配成功！')
}

</script>
<style scoped lang="scss">
.top {
  display: flex;
  justify-content: space-between;

  .select {
    display: flex;
    margin: 6px 6px 0;
    font-size: 14px;

    div {
      margin: 4px;

      span {
        margin-right: 4px;
      }
    }

    .dropdown {
      font-size: 12px;
    }
  }

  .statistics {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 12px;

    .card-view {
      display: flex;
      justify-content: center;
      align-items: center;

      div {
        width: 20px;
        height: 20px;
        margin-left: 10px;
        border: .6px solid rgba($color: #a67cea, $alpha: .5);
      }

      span {
        margin: 0 12px 0 6px;
      }
    }
  }
}

.bed-type-color1 {
  background-color: #c9e6fe;
}

.bed-type-color2 {
  background-color: #e4e4e4;
}

.bed-type-color3 {
  background-color: #feebfe;
}

.severeIllness {
  margin-right: 2px;
  font-weight: 700;
  font-size: 16px;
  color: #fd6700;
}

.content {
  display: flex;

  .patients {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-certer;
    margin: 12px;
    width: 240px;
    height: 600px;
    border: 1px solid #000;

    .title {
      width: 100%;
      height: 36px;
      text-align: center;
      line-height: 36px;
      border: 1px solid rgba($color: #000000, $alpha: 0);
      background-color: #b1a6a6df;
    }

    .edit {
      display: flex;

      .active {
        background-color: rgb(136, 103, 243);
        /* 亮色 */
      }

      button {
        display: flex;
        width: 119px;
        height: 30px;
        justify-content: center;
        align-items: center;
        border-radius: 0;
        border: 1px solid rgba($color: #000000, $alpha: 0.3);
        margin-bottom: 20px;
        background-color: rgba(0, 0, 0, 1)y;
        /* 暗色 */
      }
    }

    .patients-content {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      align-items: start;
      overflow-y: auto;

      /* 定制滚动条的整体样式 */
      &::-webkit-scrollbar {
        width: 8px;
      }

      /* 定制滚动条轨道的样式 */
      &::-webkit-scrollbar-track {
        background: #f1f1f1;
        /* 轨道的背景颜色 */
      }

      /* 定制滚动条滑块的样式 */
      &::-webkit-scrollbar-thumb {
        background-color: rgba(107, 107, 242, 0.5);
        /* 滑块的背景颜色 */
        border-radius: 10px;
        /* 滑块的圆角 */
        border: 2px solid orange;
        /* 滑块的边框 */
      }
    }

  }

  .beds {
    width: 864px;
    height: 600px;
    margin: 12px;
    border: 1px solid #000;

    .title {
      display: flex;
      width: 100%;
      height: 32px;
      align-items: center;
      padding-left: 24px;
      background-color: #b1a6a6df;
    }
    span {
        display: block;
        text-align: left;
        margin: 24px 20px 0;
      }
    .bedcontent {
      width: 100%;
      height: 100%;
      display: flex;

      .freebeds,
      .todayDischargedBeds {
        display: flex;
        flex-wrap: wrap;
        align-content: flex-start;
        overflow-y: auto;
      }

      .freebed,
      .todayDischargedBed {
        width: 120px;
        height: 188px;
        margin: 12px 0 0 20px;
        border: 1px solid #000;
      }
    }
  }

  .bed-overview {
    margin: 6px;
  }
}
</style>