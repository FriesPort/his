<template>
  <div>
    <a-button type="primary" @click="showModal">
      <template #icon>
        <plus-outlined />
      </template>
      新增用户
    </a-button>
    <a-modal v-model:visible="visible" title="新增用户" @ok="handleOk">
      <template #footer>
        <a-button key="back" @click="handleCancel">取消</a-button>
        <a-button key="submit" type="primary" @click="handleOk">新增</a-button>
      </template>
      <a-form>
        <a-form-item label="用户名：" style="width: 200px;">
          <a-input v-model:value="username" placeholder="请输入用户名">
          </a-input>
        </a-form-item>
        <a-form-item label="密码：" style="width: 200px;">
          <a-input v-model:value="passward" type="passward" placeholder="请输入密码">
          </a-input>
        </a-form-item>
        <a-form-item label="姓名：" style="width: 200px;">
          <a-input v-model:value="name" placeholder="请输入名字">
          </a-input>
        </a-form-item>
        <!-- <a-form-item label="工号：" style="width: 200px;">
          <a-input v-model:value="usernumber" placeholder="请输入工号">
          </a-input>
        </a-form-item> -->
        <a-form-item label="手机号：" style="width: 200px;">
          <a-input v-model:value="phone" placeholder="请输入手机号">
          </a-input>
        </a-form-item>
        <a-form-item label="所属院区：" style="width: 200px;">
          <a-select v-model:value="selectCampusName">
            <a-select-option v-for=" campus in campusData " :key="campus.campusId" :value="campus.campusName">
              <span class="dropdown">{{ campus.campusName }}</span>
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="账号状态：" style="width: 200px;">
          <a-radio-group v-model:value="status">
            <a-radio :value="1">是</a-radio>
            <a-radio :value="0">否</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, reactive, watch } from 'vue';
import { getWardsData } from '@/api/bedView/bedData.ts';
import { Campus, Ward, Office, } from '../bedview/types/Bed';
import { addUserApi } from '@/api/user';
import { message } from 'ant-design-vue';
const props = defineProps<{
  getUserList: Function
}>()
const visible = ref<boolean>(false);

const username = ref('')
const passward = ref('')
const name = ref('')
const usernumber = ref('')
const status = ref<number>(1)
const phone = ref('')
const showModal = () => {
  visible.value = true;
};

const handleOk = async () => {
  const phoneNumberRegex = /^\d{11}$/;
  const dangerChars = /[=,"'<>!\/\\?#%&{}[\]() ;]+/
  if (username.value === '') {
    message.warning('用户名为空')
    return
  }
  if (passward.value === '') {
    message.warning('密码为空')
    return
  }
  if (name.value === '') {
    message.warning('名字为空')
    return
  }
  if (dangerChars.test(username.value)) {
    message.warning('用户名包含危险符合，请重新输入')
    return
  }
  if (!phoneNumberRegex.test(phone.value)) {
    message.warning('请输入有效电话')
    return
  }
  visible.value = false;
  let params = {
    campusId: campusId.value,
    name: name.value,
    Enable: status.value,
    username: username.value,
    password: passward.value,
    // employeeNumber: usernumber.value,
    phone: phone.value
  }
  name.value = ''
  status.value = 1
  username.value = ''
  passward.value = ''
  usernumber.value = ''
  phone.value = ''
  console.log('新增', params)
  await addUserApi(params)
  message.success('用户新增成功')
  props.getUserList()
};

const handleCancel = () => {
  visible.value = false;
};

const campusData: Campus[] = reactive([])
const selectCampusName = ref('')
const campusId = ref<number>()
watch(() => selectCampusName.value, (newValue) => {
  const campus = campusData.find(item => item.campusName === newValue)
  if (campus) {
    campusId.value = campus.campusId
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
  selectCampusName.value = campusData[0].campusName
})
</script>
