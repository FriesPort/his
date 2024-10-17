<template>
  <div class="ward-container">
    <div class="beds">
      <div class="ward-card" v-for="(patient, patientIndex) in displayedPatients" :key="patientIndex"
        @click="patientCardHandle(patient, patientIndex)">
        <div class="bed-info">
          <!-- 楼层数、病房号、病房类型 -->
          <div class="title" :style="{ backgroundColor: getTitleColorStyle(patient.status) }">
            <span>{{ patient.status }}<span v-if="patient.status === '待入院'">({{ patient.waitDay }}天)</span></span>
          </div>
          <!-- 床位信息 -->
          <div class="bed">
            <div class="base">
              <span class="name">{{ patient.name }}</span>
              <span class="sex">{{ patient.gender }}</span>
              <span class="age">{{ patient.age }}</span>
            </div>
            <div class="admission-number">电话：{{ patient.phone }}</div>
            <div class="diagnosis">主治医生：{{ patient.doctor }}</div>
            <div class="admission-time">诊断情况：{{ patient.illness }}</div>
            <div class="doctor" v-if="patient.status === '已入院'">入院时间：{{ patient.admissionTime }}</div>
            <div class="doctor" v-if="patient.status === '待入院'">登记时间：{{ patient.createTime }}</div>
            <div class="doctor" v-if="patient.status === '已出院'">出院时间：{{ patient.dischargeTime }}</div>
            <div class="bed-vip" v-if="patient.isVip === 1"></div>
            <span class="vip-text" v-if="patient.isVip === 1">VIP</span>
            <div class="bed-icon">
              <span class="room-number" v-if="patient.status === '已入住'">{{ patient.bedCount }}人间</span>
              <span class="room-type" v-if="patient.status === '已入住' && patient.roomType === '隔离病房'">隔</span>
              <span class="room-type" v-if="patient.status === '已入住' && patient.roomType === 'ICU'">ICU</span>
              <span class="room-type1" v-if="patient.status === '已入住' && patient.roomType === '普通病房'">常</span>
              <span class="room-number" v-if="patient.status !== '已入住'"><span
                  v-if="patient.roomNumberRequirement === null">无要求</span><span v-else>{{ patient.roomNumberRequirement
                  }}人间</span></span>
              <span class="room-type" v-if="patient.status !== '已入住' && patient.roomTypeRequirement === '隔离病房'">隔</span>
              <span class="room-type"
                v-if="patient.status !== '已入住' && patient.roomTypeRequirement === 'ICU'">ICU</span>
              <span class="room-type1"
                v-if="patient.status !== '已入住' && patient.roomTypeRequirement === '普通病房'">常</span>
              <span class="iconfont icon-jizhen" v-if="patient.isEmergency === 1"></span>
              <span v-if="patient.isAcute === 1" class="severeIllness">重</span>
            </div>
          </div>
        </div>
      </div>
      <div class="pagination">
        <a-pagination v-model:current="currentPage" :page-size="currentpageSize" :page-size-options="pageSizeOptions"
          :total="totalPatients" show-less-items show-quick-jumper show-size-changer :show-total="showTotal"
          @change="onChange" />
      </div>
    </div>
  </div>
  <a-modal v-model:visible="visible" :title="recordPatient?.name" @ok="handleOk" :afterClose="handleOk" width="420px">
    <template #footer>
      <a-button key="submit" type="primary" @click="handleOk">确定</a-button>
    </template>
    <a-button type="text" @click="patientDetail">
      <template #icon>
        <message-outlined />
      </template>
      患者详情
    </a-button>
    <a-button type="text" @click="openEdit(recordPatient as Patient, recordIndex)"  :disabled="!hasPermission('患者管理')">
      <template #icon>
        <edit-outlined />
      </template>
      患者编辑
    </a-button>
    <a-modal v-model:visible="editVisible[recordIndex]" title="患者编辑" @ok="editOk" @cancel="editCancel" ok-text="确定"
      cancel-text="取消">
      <a-form>
        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item label="姓名：">
              <a-input v-model:value="editPatientName" :placeholder="editPatientName" style="width: 160px;"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="11" :offset="2">
            <a-form-item label="性别：">
              <a-select v-model:value="Gender" style="width: 160px">
                <a-select-option v-for="Gender in Genders" :key="Gender" :value="Gender">
                  <span>{{ Gender }}</span>
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item label="年龄：">
              <a-input v-model:value="editPatientAge" :placeholder="editPatientAge" style="width: 160px;"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="11" :offset="2">
            <a-form-item label="联系电话：">
              <a-input v-model:value="editTelephoneNumber" :placeholder="editTelephoneNumber"
                style="width: 132px;"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item label="医生：">
              <a-input v-model:value="editDoctor" :placeholder="editDoctor" style="width: 160px;"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="11" :offset="2">
            <a-form-item label="诊断情况：">
              <a-input v-model:value="editIllness" :placeholder="editIllness" style="width: 132px;"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item label="入院状态：">
              <span>{{ editPatientStatus }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="11" :offset="2">
            <a-form-item v-if="editPatientStatus === '已入院'" label="住院号：" style="width: 200px;">
              <a-input v-model:value="editAdmissionNumber" :placeholder="editAdmissionNumber"></a-input>
            </a-form-item>
            <a-form-item v-if="editPatientStatus === '待入院'" label="等待天数：">
              <span>{{ editWaitingDay }}</span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item v-if="editPatientStatus === '待入院'" label="病房类型要求：">
              <a-select v-model:value="editRoomTypeRequirement" style="width: 104px">
                <a-select-option v-for="editRoomTypeRequirement in editRoomTypeRequirements"
                  :key="editRoomTypeRequirement" :value="editRoomTypeRequirement">
                  <span>{{ editRoomTypeRequirement }}</span>
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="11" :offset="2">
            <a-form-item v-if="editPatientStatus === '待入院'" label="病房性别要求：">
              <a-select v-model:value="editRoomGenderRequirement" style="width: 104px">
                <a-select-option v-for="editRoomGenderRequirement in editRoomGenderRequirements"
                  :key="editRoomGenderRequirement" :value="editRoomGenderRequirement">
                  <span>{{ editRoomGenderRequirement }}</span>
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item v-if="editPatientStatus === '待入院'" label="病房人数要求：">
              <a-select v-model:value="editRoomNumberRequirement" style="width: 104px">
                <a-select-option v-for="editRoomNumberRequirement in editRoomNumberRequirements"
                  :key="editRoomNumberRequirement" :value="editRoomNumberRequirement">
                  <span>{{ editRoomNumberRequirement }}</span>
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="11" :offset="2">
            <a-form-item v-if="editPatientStatus === '待入院'" label="入院类型：">
              <a-select v-model:value="editAdmissionType" style="width: 132px">
                <a-select-option v-for="editAdmissionType in editAdmissionTypes" :key="editAdmissionType"
                  :value="editAdmissionType">
                  <span>{{ editAdmissionType }}</span>
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item v-if="editPatientStatus === '已入院'" label="入院时间：">
              <a-date-picker v-model:value="editAdmissionTimeValue" :format="dateFormat" />
            </a-form-item>
          </a-col>
          <a-col :span="11" :offset="2">
            <a-form-item v-if="editPatientStatus === '已入院'" label="入院类型：">
              <a-select v-model:value="editAdmissionType" style="width: 132px">
                <a-select-option v-for="editAdmissionType in editAdmissionTypes" :key="editAdmissionType"
                  :value="editAdmissionType">
                  <span>{{ editAdmissionType }}</span>
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item v-if="editAdmissionType === '预约入院'" label="预约方式：">
              <a-select v-model:value="editBookType" style="width: 132px">
                <a-select-option v-for="editBookType in bookTypes" :key="editBookType" :value="editBookType">
                  <span>{{ editBookType }}</span>
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <!-- <a-col :span="11" :offset="2">
                <a-form-item label="是否急诊：" v-if="editAdmissionType === '预约入院'">
                  <a-radio-group v-model:value="emergencyValue">
                    <a-radio :value="1">是</a-radio>
                    <a-radio :value="0">否</a-radio>
                  </a-radio-group>
                </a-form-item>
              </a-col> -->
        </a-row>

        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item label="是否急诊：">
              <a-radio-group v-model:value="emergencyValue">
                <a-radio :value="1">是</a-radio>
                <a-radio :value="0">否</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="11" :offset="2">
            <a-form-item label="是否重症：">
              <a-radio-group v-model:value="cuteValue">
                <a-radio :value="1">是</a-radio>
                <a-radio :value="0">否</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="11">
            <a-form-item label="是否VIP：">
              <a-radio-group v-model:value="VIPValue">
                <a-radio :value="1">是</a-radio>
                <a-radio :value="0">否</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
    <a-popconfirm title="是否确认删除此患者?" okText="是" cancelText="否" @confirm="bedDelete">
      <a-button danger  :disabled="!hasPermission('患者管理')" >
        <template #icon>
          <delete-outlined />
        </template>
        患者删除
      </a-button>
    </a-popconfirm>
  </a-modal>
  <a-modal v-model:visible="pVisible" title="患者详情" @ok="pHandleOk" :afterClose="pHandleOk">
    <template #footer>
      <a-button key="submit" type="primary" @click="pHandleOk">确定</a-button>
    </template>
    <p>姓名：{{ recordPatient?.name }}</p>
    <p>性别：{{ recordPatient?.gender }}</p>
    <p>年龄：{{ recordPatient?.age }}</p>
    <p>联系电话：{{ recordPatient?.phone }}</p>
    <p>主治医生：{{ recordPatient?.doctor }}</p>
    <p>诊断情况：{{ recordPatient?.illness }}</p>
    <p>入院状态：{{ recordPatient?.status }}</p>
    <p>入院类型：{{ recordPatient?.admissionType }}</p>
    <p v-if="recordPatient?.admissionType === '预约入院'">预约方式：{{ recordPatient?.bookType }}</p>
    <p v-if="recordPatient?.status === '已入院'">入院时间：{{ recordPatient?.admissionTime }}</p>
    <p v-if="recordPatient?.status === '已入院'">住院号：{{ recordPatient?.admissionNumber }}</p>
    <p v-if="recordPatient?.status === '已入院'">所在床位：{{ recordPatient?.roomNumber }}{{ recordPatient?.bedNumber }}</p>
    <p v-if="recordPatient?.status === '待入院'">登记时间：{{ recordPatient?.admissionNumber }}</p>
    <p v-if="recordPatient?.status === '待入院'">等待天数：{{ recordPatient?.waitDay }}</p>
    <p>病房类型：{{ recordPatient?.roomTypeRequirement }}</p>
    <p>病房性别：{{ recordPatient?.roomGenderRequirement }}</p>
    <p>病房人数：{{ recordPatient?.roomNumberRequirement }}<span
        v-if="recordPatient?.roomNumberRequirement === null">无特别需求</span></p>
    <p>重症：<span v-if="recordPatient?.isEmergency === 1">是</span><span v-else>否</span></p>
    <p>重症：<span v-if="recordPatient?.isAcute === 1">是</span><span v-else>否</span></p>
    <p>VIP：<span v-if="recordPatient?.isVip === 1">是</span><span v-else>否</span></p>
  </a-modal>
</template>

<script setup lang="ts">
import { reactive, ref, computed, watch, inject } from 'vue';
import type { Patient } from './types/patientViewTypes';
import { AxiosError } from 'axios';
import { message } from 'ant-design-vue';
import dayjs, { Dayjs } from 'dayjs';
import { patientDeleteRequest, patientEditRequest } from '@/api/patientView/patientData';
import { useUserStore } from '@/store/userStore';

const userStore = useUserStore();

const hasPermission = (permission: string) => {
  return userStore.permissions.includes(permission);
};

// 从祖先组件注入 patientsData和handleSearch方法
const patientsData = inject('patientsData') as any;
const handleSearch: Function = inject('handleSearch') as Function
//床位详情方法
const visible = ref<boolean>(false)
const recordPatient = ref<Patient>()
const patientCardHandle = (patient: Patient, index: number) => {
  visible.value = true
  recordPatient.value = patient
  recordIndex.value = index
}

const pVisible = ref<boolean>(false);
const patientDetail = () => {
  pVisible.value = true
}
const pHandleOk = () => {
  pVisible.value = false
}
const handleOk = () => {
  visible.value = false
}

//患者删除
const bedDelete = async () => {
  if (recordPatient.value?.status === '已入院') message.warning('请先从床位上释放此患者')
  else {
    console.log('删除id', recordPatient.value?.patientId)
    let params = recordPatient.value?.patientId as number;
    try {
      // 假设 patientDeleteRequest 是一个返回 Promise 的异步函数
      let result = await patientDeleteRequest(params);
      // 如果需要的话，处理 result

      //删除成功后再一次进行查询请求刷新页面
      await handleSearch()
      message.success('患者删除成功');
      visible.value = false
    } catch (error: any) {
      // 捕获并处理异步操作中的错误
      message.warning('删除患者时发生错误');
      // 可以根据错误类型或内容进行更详细的错误处理
      console.log('删除患者错误：', error.data.msg)
      // 例如，可以设置错误处理的状态，或者给用户展示错误信息
    }
  }
}


// 计算属性，根据 bedType 返回颜色
const getTitleColorStyle = (status: string) => {
  switch (status) {
    case '已入院':
      return '#c9e6fe';
    case '待入院':
      return '#ff9999';
    default:
      return '#feebfe';
  }
};

// 计算患者总数
const totalPatients = ref<number>(patientsData.value.length);
watch(patientsData, () => {
  totalPatients.value = patientsData.value.length
})
// 计算每页显示的患者数据
const currentpageSize = ref(parseInt(localStorage.getItem('patientCardPageSize') || '24')); // 每页显示的患者数量
const currentPage = ref(parseInt(localStorage.getItem('patientCardPage') || '1')); // 当前页码
const pageSizeOptions = ['8', '16', '24', '32', '40'];
// 计算分页器的选项
const showTotal = () => {
  const pagesize = totalPatients.value < currentpageSize.value ? totalPatients.value : currentpageSize.value
  return `共 ${totalPatients.value} 个患者，每页${pagesize}个患者`
}
const onChange = (pageNumber: number, pageSize: number) => {
  localStorage.setItem('patientCardPage', pageNumber.toString());
  localStorage.setItem('patientCardPageSize', pageSize.toString());
  currentPage.value = pageNumber;
  currentpageSize.value = pageSize
};
watch(patientsData, () => {
  if (patientsData.value.length < currentPage.value * currentpageSize.value && parseInt(localStorage.getItem('patientCardPage') || '1') !== 1) {
    const current = currentPage.value - 1
    currentPage.value = current
  }
})

// 计算分页后的患者数据
const displayedPatients = computed(() => {
  const startIndex = (currentPage.value - 1) * currentpageSize.value;
  const endIndex = Math.min(startIndex + currentpageSize.value, totalPatients.value);
  const currentpatients: Patient[] = patientsData.value.slice(startIndex, endIndex);
  return currentpatients;
});

//患者编辑
const editVisible = reactive(Array(patientsData.length).fill(false));
const editPatient = ref<Patient>()
const recordIndex = ref<number>(0)
const openEdit = (patient: Patient, index: number) => {
  if (patient.status === '已出院') { message.warning('已出院患者不可编辑'); return }
  editVisible.fill(false); // 关闭所有弹窗
  editVisible[index] = true
  recordIndex.value = index
  editPatient.value = patient
  editPatientId.value = editPatient.value.patientId
  editPatientName.value = editPatient.value.name
  editPatientAge.value = editPatient.value.age
  editTelephoneNumber.value = editPatient.value.phone
  Gender.value = editPatient.value.gender
  editDoctor.value = editPatient.value.doctor
  editIllness.value = editPatient.value.illness
  editPatientStatus.value = editPatient.value.status
  editAdmissionTimeValue.value = dayjs(editPatient.value.admissionTime === null ? new Date() : editPatient.value.admissionTime)
  editAdmissionNumber.value = editPatient.value?.admissionNumber as string
  editAdmissionType.value = editPatient.value.admissionType
  editBookType.value = editPatient.value.bookType as string
  editWaitingDay.value = editPatient.value.waitDay as number
  editRoomGenderRequirement.value = editPatient.value.roomGenderRequirement as string
  editRoomTypeRequirement.value = editPatient.value.roomTypeRequirement as string
  editRoomNumberRequirement.value = editPatient.value.roomNumberRequirement as number
  emergencyValue.value = editPatient.value.isEmergency
  cuteValue.value = editPatient.value.isAcute
  VIPValue.value = editPatient.value.isVip
}

//定义且用来保存编辑的值
const editPatientId = ref(1)
const editPatientName = ref('张三')
const editPatientAge = ref(20)
const editTelephoneNumber = ref('123456789')
const editDoctor = ref('老李')
const editIllness = ref('高血压')
const editAdmissionNumber = ref('AA123456')
const editWaitingDay = ref(0)
const editRoomTypeRequirements = ['普通病房', '隔离病房', 'ICU']
const editRoomTypeRequirement = ref(editRoomTypeRequirements[0])
const editRoomGenderRequirements = ['男性病房', '女性病房', '混合病房']
const editRoomGenderRequirement = ref(editRoomGenderRequirements[0])
const editRoomNumberRequirements = ['全部', 1, 2, 3, 4, 5, 6, 7]
const editRoomNumberRequirement = ref(editRoomNumberRequirements[0])
const Genders = ['男', '女']
const Gender = ref(Genders[0])
const patientStatuses = ['已入院', '待入院']
const editPatientStatus = ref(patientStatuses[0])
const editAdmissionTypes = ['预约入院', '手术入院', '急诊入院']
const editAdmissionType = ref(editAdmissionTypes[0])
const bookTypes = ['线上预约', '线下预约']
const editBookType = ref(bookTypes[0])
const emergencyValue = ref<number>(0)
const cuteValue = ref<number>(0)
const VIPValue = ref<number>(0)
const dateFormat = 'YYYY/MM/DD'
const editAdmissionTimeValue = ref<Dayjs>(dayjs(new Date()))


//监视入院状态与入院方式
watch(editPatientStatus, (newValue) => {
  if (newValue === '已入院') {
    editWaitingDay.value = -1
    editRoomTypeRequirement.value = ''
    editRoomGenderRequirement.value = ''
    editRoomNumberRequirement.value = '全部'
  } else {
    if (editPatient.value) {
      if (editPatient.value.waitDay) {
        editWaitingDay.value = editPatient.value.waitDay
      } else {
        editWaitingDay.value = 0
      }
      if (editPatient.value.roomTypeRequirement) {
        editRoomTypeRequirement.value = editPatient.value.roomTypeRequirement
      } else {
        editRoomTypeRequirement.value = editRoomTypeRequirements[0]
      }
      if (editPatient.value.roomGenderRequirement) {
        editRoomGenderRequirement.value = editPatient.value.roomGenderRequirement
      } else {
        editRoomGenderRequirement.value = editRoomGenderRequirements[0]
      }
      if (editPatient.value.roomNumberRequirement) {
        editRoomNumberRequirement.value = editPatient.value.roomNumberRequirement
      } else {
        editRoomNumberRequirement.value = editRoomNumberRequirements[0]
      }
    }
  }
})
watch(editAdmissionType, (newValue) => {
  if (newValue !== '预约入院') {
    editBookType.value = ''
  } else {
    if (editPatient.value) {
      if (editPatient.value.bookType) {
        editBookType.value = editPatient.value.bookType
      } else {
        editBookType.value = bookTypes[0]
      }
    }
  }
})


const editCancel = () => {
  editVisible[recordIndex.value] = false
}
const editOk = async () => {
  const phoneNumberRegex = /^\d{11}$/;
  if (editPatientName.value === '') {
    message.warning('名字为空')
    return
  }
  if (editPatientAge.value === undefined) {
    message.warning('请输入年龄')
    return
  }
  if (editPatientAge.value < 0) {
    message.warning('请输入有效年龄')
    return
  }
  if (!phoneNumberRegex.test(editTelephoneNumber.value)) {
    message.warning('请输入有效电话')
    return
  }
  if (editDoctor.value === '') {
    message.warning('请输入主治医生')
    return
  }
  if (editIllness.value === '') {
    message.warning('请输入诊断情况')
    return
  }
  if (editPatientStatus.value === '已入院')
    if (editAdmissionNumber.value === '') {
      message.warning('请输入住院号')
      return
    }
  let params = {
    id: editPatientId.value,
    name: editPatientName.value,
    age: editPatientAge.value,
    gender: Gender.value,
    phone: editTelephoneNumber.value,
    bookType: editBookType.value,
    status: editPatientStatus.value,
    roomNumberRequirement: editRoomNumberRequirement.value === '全部' ? null : editRoomNumberRequirement.value,
    roomGenderRequirement: editRoomGenderRequirement.value,
    roomTypeRequirement: editRoomTypeRequirement.value,
    admissionType: editAdmissionType.value,
    admissionTime: editAdmissionTimeValue.value,
    admissionNumber: editAdmissionNumber.value,
    doctor: editDoctor.value,
    illness: editIllness.value,
    isEmergency: emergencyValue.value,
    isAcute: cuteValue.value,
    isVip: VIPValue.value
  }
  try {
    let result = await patientEditRequest(params);
    message.success('编辑患者成功');
    console.log('编辑的数据', params)
    editVisible[recordIndex.value] = false
    visible.value = false
    //编辑成功后再一次进行查询请求刷新页面
    await handleSearch()
  } catch (error: any) {
    // 捕获并处理异步操作中的错误
    message.warning('编辑患者时发生错误');
    // 可以根据错误类型或内容进行更详细的错误处理
    console.log('编辑患者错误：', error.data.msg)
    // 例如，可以设置错误处理的状态，或者给用户展示错误信息
  }
}
</script>

<style lang="scss" scoped>
.ward-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-content: flex-start;
  height: 88%;
  padding: 0 6px;
  position: relative;

  @media (min-width: 1600px) {
    width: 100%;
    /* 视口宽度大于等于1600时，盒子宽度补满剩余宽度 */
  }

  @media (max-width: 1599px) {
    width: 1290px;
    /* 视口宽度小于1600时，盒子宽度等于1320 */
  }
}

.pagination {
  display: flex;
  width: 100%;
  justify-content: flex-end;
  margin: 12px 48px;
}

.beds {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-content: flex-start;
  height: 100%;
  width: 100%;

  .ward-card {
    display: flex;
    flex-direction: column;
    /* 垂直方向上，内容将从上到下排列 */
    height: 180px;
    width: 150px;
    border: 1px solid #ccc;
    margin: 6px 6px 0 0;
    cursor: pointer;

    // @media (min-width: 1600px) {
    //   width: calc(100% / 13);
    //   /* 视口宽度大于等于1600时，盒子宽度补满剩余宽度 */
    // }

    // @media (min-height: 800px) {
    //   height: calc(100% / 4 - 20px);
    //   /* 视口宽度大于730时，盒子高度适当放大 */
    // }

    .bed-info {
      width: 100%;
      height: 100%;

      .title {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 100%;
        height: 30px;
        font-weight: 700;
        font-size: 14px;
        background-color: #e1dffe;
      }

      .free {
        width: 100%;
        height: calc(100% - 30px);
        overflow: hidden;
        border: 1px solid rgba($color: #8c5ef7, $alpha: .8);
        background: #e4e4e4;
        background-image: url(@/assets/logos.png);
        background-size: auto 100%;
        background-repeat: no-repeat;
        /* 防止背景图片重复 */

      }

      .bed {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        height: calc(100% - 30px);
        text-align: left;
        padding-left: 6px;
        padding-bottom: 24px;
        font-size: 13px;
        overflow: hidden;
        position: relative;

        .name {
          font-weight: 700;
          font-size: 12px;
        }

        .sex {
          font-size: 12px;
          margin: 0 12px;
          padding: 0 12px;
          border-left: 1px solid #000;
          border-right: 1px solid #000;
        }

        .bed-status {
          width: 0;
          height: 0;
          border-left: 52px solid transparent;
          /* 左侧边框，可调整大小 */
          border-bottom: 42px solid rgb(71, 109, 220);
          /* 底部边框，可调整大小和颜色 */
          position: absolute;
          bottom: 0;
          /* 使盒子位于父级盒子的底部 */
          right: 0;
          /* 使盒子位于父级盒子的右侧 */
          overflow: visible;
          /* 允许文字溢出盒子边界 */
          white-space: nowrap;
          /* 防止文字换行 */
        }

        .status-text {
          color: white;
          /* 文字颜色 */
          font-size: 10px;
          /* 文字大小 */
          position: absolute;
          bottom: 0;
          /* 使盒子位于父级盒子的底部 */
          right: 0;
          /* 使盒子位于父级盒子的右侧 */
        }

        .bed-vip {
          width: 0;
          height: 0;
          border-left: 34px solid transparent;
          /* 左侧边框，可调整大小 */
          border-top: 34px solid rgb(255, 128, 00);
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
          right: 0;
          /* 使盒子位于父级盒子的右侧 */
        }
      }

      .bed-icon {
        position: absolute;
        bottom: 4px;
        /* 使盒子位于父级盒子的底部 */
        left: 4px;
        /* 使盒子位于父级盒子的右侧 */
        padding-left: 4px;

        .room-number {
          padding: 1px 6px;
          border-radius: 20%;
          background-color: #ff9900;
          margin-right: 12px;
        }

        .room-type {
          padding: 1px 6px;
          border-radius: 50%;
          line-height: 100%;
          color: #fff;
          background-color: #ff0000;
          margin-right: 12px;
        }

        .room-type1 {
          padding: 1px 6px;
          border-radius: 50%;
          line-height: 100%;
          color: #000;
          background-color: #c9e6fe;
          margin-right: 12px;
        }

        .iconfont.icon-jizhen {
          margin-right: 8px;
        }

        .severeIllness {
          font-weight: 700;
          font-size: 14px;
          color: #fd6700;
        }
      }
    }
  }
}
</style>