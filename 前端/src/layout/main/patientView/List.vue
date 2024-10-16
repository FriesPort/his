<template>
  <a-table :columns="patientColumns" :data-source="patientsData" bordered :scroll="{ x: 1700, y: '60vh' }"
    :style="{ borderBottom: '1px solid #e8e8e8' }" :pagination="pagination">
    <template #bodyCell="{ column, record, index }">
      <template v-if="column.key === 'admissionDetail'">
        <a-button @click="openAdmissionDetail(record)" :style="{ backgroundColor: getTitleColorStyle(record.status) }"
          :disabled="record.status === '已出院'">{{ record.status }}</a-button>
        <a-modal v-model:visible="admissionDetailVisible" title="入院详情" @ok="admissionDetailOk" width="320px">
          <template #footer>
            <a-button key="submit" type="primary" @click="admissionDetailOk">确定</a-button>
          </template>
          <p>入院状态：{{ recordPatient?.status }}</p>
          <p>入院类型：{{ recordPatient?.admissionType }}</p>
          <p v-if="recordPatient?.admissionType === '预约入院'">预约方式：{{ recordPatient?.bookType }}</p>
          <p v-if="recordPatient?.status === '已入院' || recordPatient?.status === '已出院'">入院时间：{{
    recordPatient?.admissionTime }}</p>
          <p v-if="recordPatient?.status === '已入院' || recordPatient?.status === '已出院'">住院号：{{
    recordPatient?.admissionNumber }}
          </p>
          <p v-if="recordPatient?.status === '已入院'">所在床位：{{ recordPatient?.roomNumber }}{{ recordPatient?.bedNumber }}
          </p>
          <p v-if="recordPatient?.status === '已出院'">曾住床位：{{ recordPatient?.roomNumber }}{{ recordPatient?.bedNumber }}
          </p>
          <p v-if="recordPatient?.status === '待入院'">登记时间：{{ recordPatient?.createTime }}</p>
          <p v-if="recordPatient?.status === '待入院'">等待天数：{{ recordPatient?.waitDay }}</p>
          <p v-if="recordPatient?.status === '待入院'">病房类型要求：{{ recordPatient?.roomTypeRequirement }}</p>
          <p v-if="recordPatient?.status === '待入院'">病房性别要求：{{ recordPatient?.roomGenderRequirement }}</p>
          <p v-if="recordPatient?.status === '待入院'">病房人数要求：{{ recordPatient?.roomNumberRequirement }}<span
              v-if="recordPatient.roomNumberRequirement === null">无特别需求</span></p>
        </a-modal>
      </template>
      <template v-if="column.key === 'delete'">
        <a-button text size="small" @click="openEdit(record, index)"  :disabled="!hasPermission('患者管理')">

          <template #icon>
            <edit-outlined />
          </template>
          编辑
        </a-button>
        <a-modal v-model:visible="editVisible[recordIndex]" title="患者编辑" @ok="editOk" @cancel="editCancel" ok-text="确定"
          cancel-text="取消">
          <a-form>
            <a-row :gutter="16">
              <a-col :span="11">
                <a-form-item label="姓名：">
                  <a-input v-model:value="editPatientName" :placeholder="editPatientName"
                    style="width: 160px;"></a-input>
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
        <a-popconfirm title="是否确认删除此患者?" okText="是" cancelText="否"
          @confirm="patientDelete(record.patientId, record.status)">
          <a-button danger size="small"  :disabled="!hasPermission('患者管理')">
            <template #icon>
              <delete-outlined />
            </template>
            删除
          </a-button>
        </a-popconfirm>
      </template>
    </template>
  </a-table>
</template>
<script lang="ts" setup>
import { ref, reactive, watch, onMounted, inject } from 'vue';
import { message, type TableColumnsType } from 'ant-design-vue';
import type { Patient } from './types/patientViewTypes';
import { patientDeleteRequest, patientEditRequest } from '@/api/patientView/patientData'
import dayjs, { Dayjs } from 'dayjs';
import { useUserStore } from '@/store/userStore';

const userStore = useUserStore();

const hasPermission = (permission: string) => {
  return userStore.permissions.includes(permission);
};

// 从祖先组件注入 patientsData与handleSearch方法
const patientsData = inject('patientsData') as any;
const handleSearch: Function = inject('handleSearch') as Function

const patientColumns: TableColumnsType = [
  { title: '姓名', width: 120, dataIndex: 'name', key: 'p1', fixed: 'left', },
  { title: '性别', width: 100, dataIndex: 'gender', key: 'p2', },
  { title: '年龄', dataIndex: 'age', key: 'p3', width: 100 },
  { title: '联系电话', dataIndex: 'phone', key: 'p4', width: 160 },
  { title: '医生', dataIndex: 'doctor', key: 'p6', width: 120 },
  { title: '诊断情况', dataIndex: 'illness', key: 'p7', width: 180 },
  { title: '入院情况', key: 'admissionDetail', width: 150 },
  { title: '是否急诊', dataIndex: 'isEmergency', key: 'p8', width: 150 },
  { title: '是否重症', dataIndex: 'isAcute', key: 'p9', width: 150 },
  { title: '是否VIP', dataIndex: 'isVip', key: 'p10', width: 150 },
  { title: '操作', key: 'delete', width: 172, fixed: 'right', },
];


const pagination = {
  current: parseInt(localStorage.getItem('patientListPage') || '1'),
  pageSize: parseInt(localStorage.getItem('patientPageSize') || '10'),
  showQuickJumper: true,
  showLessItems: true,
  showSizeChanger: true,
  total: ref(patientsData.value.length),
  showTotal: () => {
    const pagesize = pagination.total.value < pagination.pageSize ? pagination.total.value : pagination.pageSize
    return `共 ${pagination.total.value} 个患者，每页${pagesize}个患者`
  },
  onChange: (page: number, pageSize: number) => {
    localStorage.setItem('patientListPage', page.toString());
    localStorage.setItem('patientPageSize', pageSize.toString());
    pagination.current = page;
    pagination.pageSize = pageSize;
  },
};
watch(patientsData, () => {
  pagination.total.value = patientsData.value.length
  if (patientsData.value.length < pagination.current * pagination.pageSize && parseInt(localStorage.getItem('patientListPage') || '1') !== 1) {
    const current = pagination.current - 1
    pagination.onChange(current, pagination.pageSize)
  }
})

// 计算属性，根据 入住情况 返回颜色
const getTitleColorStyle = (patinetStatus: string) => {
  switch (patinetStatus) {
    case '已入院':
      return '#c9e6fe';
    case '已出院':
      return '#fff';
    case '待入院':
      return '#ffb376';
  }
};


const admissionDetailVisible = ref<boolean>(false)
//入院详情弹框按钮，记录下当前选择的患者信息
const recordPatient = ref<Patient>()
const openAdmissionDetail = (record: Patient) => {
  admissionDetailVisible.value = true
  recordPatient.value = record
}
const admissionDetailOk = () => {
  admissionDetailVisible.value = false
}
//患者删除请求
const patientDelete = async (patientId: number, patientStatus: string) => {
  if (patientStatus === '已入院') message.warning('请先从床位上释放此患者')
  else {
    console.log('删除患者id', patientId)
    let params = patientId;
    try {
      // 假设 patientDeleteRequest 是一个返回 Promise 的异步函数
      let result = await patientDeleteRequest(params);
      // 如果需要的话，处理 result
      console.log('患者删除成功', result);
      message.success('患者删除成功');

      //删除成功后再一次进行查询请求刷新页面
      await handleSearch()
    } catch (error: any) {
      // 捕获并处理异步操作中的错误
      message.warning('删除患者时发生错误');
      // 可以根据错误类型或内容进行更详细的错误处理
      console.log('删除患者错误：', error.data.msg)
      // 例如，可以设置错误处理的状态，或者给用户展示错误信息
    }
  }
}
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
    message.warning('请输入有效年龄')
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
    message.warning('编辑患者成功');
    console.log('编辑的数据', params)
    editVisible[recordIndex.value] = false
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

<style scoped lang="scss">
.campus {
  display: flex;
}

.edit {
  line-height: 14px;
  width: 40px;
  font-size: 11px;
  text-align: center;
  padding: 2px;
}

.check-in {
  margin-left: 14px;
}
</style>