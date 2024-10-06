<template>
  <a-button type="primary" @click="showModal">
    <template #icon>
      <edit-outlined />
    </template>
    编辑
  </a-button>
  <a-modal v-model:visible="visible" title="编辑用户" @ok="handleOk">
    <template #footer>
      <a-button key="back" @click="handleCancel">取消</a-button>
      <a-button key="submit" type="primary" @click="handleOk">确认</a-button>
    </template>
    <a-form>
      <a-form-item label="姓名：" style="width: 200px;">
        <a-input v-model:value="props.user.name" placeholder="请输入名字">
        </a-input>
      </a-form-item>
      <a-form-item label="用户名：" style="width: 200px;">
        <a-input v-model:value="props.user.username" placeholder="请输入用户名">
        </a-input>
      </a-form-item>
      <a-form-item label="工号：" style="width: 200px;">
        <a-input v-model:value="props.user.employeeNumber" placeholder="请输入工号" :disabled="true">
        </a-input>
      </a-form-item>
      <a-form-item label="手机号：" style="width: 200px;">
        <a-input v-model:value="props.user.phone" placeholder="请输入手机号">
        </a-input>
      </a-form-item>
      <a-form-item label="所属院区：" style="width: 200px;">
        <a-select v-model:value="selectCampusName">
          <a-select-option v-for=" campus in campusData " :key="campus.campusId" :value="campus.campusName">
            <span class="dropdown">{{ campus.campusName }}</span>
          </a-select-option>
        </a-select>
      </a-form-item>
      <!-- <a-form-item label="所属科室：" style="width: 200px;">
        <a-select v-model:value="selectOfficeName">
          <a-select-option v-for=" office in offices " :key="office.officeId" :value="office.officeName">
            <span class="dropdown">{{ office.officeName }}</span>
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="所属病区：" style="width: 200px;">
        <a-select v-model:value="selectWardName">
          <a-select-option v-for=" ward in wards " :key="ward.wardId" :value="ward.wardName">
            <span class="dropdown">{{ ward.wardName }}</span>
          </a-select-option>
        </a-select>
      </a-form-item> -->
      <a-form-item label="账号状态：" style="width: 200px;">
        <a-radio-group v-model:value="props.user.isEnable">
          <a-radio :value="1">是</a-radio>
          <a-radio :value="0">否</a-radio>
        </a-radio-group>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import { onMounted, ref, reactive, watch } from 'vue';
import { getWardsData } from '@/api/bedView/bedData.ts';
import { Campus, Ward, Office, } from '../bedview/types/Bed';
import { addUserApi, editUserApi } from '@/api/user';
import { message } from 'ant-design-vue';

const props = defineProps<{
  user: any,
  getUserList: Function
}>()
const visible = ref<boolean>(false);


const showModal = () => {
  visible.value = true;
};

const handleOk = async () => {
  const phoneNumberRegex = /^\d{11}$/;
  const dangerChars = /[=,"'<>!\/\\?#%&{}[\]() ;]+/
  if (props.user.name === '') {
    message.warning('名字为空')
    return
  }
  if (props.user.username === '') {
    message.warning('用户名为空')
    return
  }
  if (dangerChars.test(props.user.username)) {
    message.warning('用户名包含危险符合，请重新输入')
    return
  }
  if (!phoneNumberRegex.test(props.user.phone)) {
    message.warning('请输入有效电话')
    return
  }
  visible.value = false;
  let params = {
    user_id: props.user.userId,
    key_data:{ campus_id: campusId.value,
    // officeId: officeId.value,
    // wardId: wardId.value,
    name: props.user.name,
    Enable: props.user.isEnable,
    username: props.user.username,
    // employeeNumber: props.user.employeeNumber,
    phone: props.user.phone}
  }
  console.log('bianji', params)
  await editUserApi(params)
  message.success('用户编辑成功')
  props.getUserList()
};

const handleCancel = () => {
  visible.value = false;
};

const campusData: Campus[] = reactive([])
// const offices = ref<Office[]>()
// const wards = ref<Ward[]>()
const selectCampusName = ref('')
// const selectOfficeName = ref('')
// const selectWardName = ref('')
const campusId = ref<number>()
// const officeId = ref<number>()
// const wardId = ref<number>()
watch(() => selectCampusName.value, (newValue) => {
  const campus = campusData.find(item => item.campusName === newValue)
  if (campus) {
    // offices.value = campus.offices
    campusId.value = campus.campusId
    // officeId.value = campusData[0].offices[0].officeId
    // wardId.value = campusData[0].offices[0].wards[0].wardId
    // selectOfficeName.value = campus.offices[0].officeName
  }
})
// watch(() => selectOfficeName.value, (newValue) => {
//   const office = offices.value?.find(item => item.officeName === newValue)
//   if (office) {
//     wards.value = office.wards
//     officeId.value = office.officeId
//     wardId.value = office.wards[0].wardId
//     selectWardName.value = office.wards[0].wardName
//   }
// })
// watch(() => selectWardName.value, (newValue) => {
//   const ward = wards.value?.find(item => item.wardName === newValue)
//   if (ward) {
//     wardId.value = ward.wardId
//   }
// })
// watch(() => campusId.value, (newValue) => {
//   const campus = campusData.find(item => item.campusId === newValue)
//   if (campus) {
//     officeId.value = campus.offices[0].officeId
//     wardId.value = campus.offices[0].wards[0].wardId
//   }
// })
onMounted(async () => {
  // let { data } = await axios.get('http://localhost:3000/beds');
  let { data } = await getWardsData();
  campusData.splice(0, campusData.length, ...data)
  selectCampusName.value = props.user.campusName
  // selectOfficeName.value = props.user.office
  // selectWardName.value = props.user.ward
})
</script>
