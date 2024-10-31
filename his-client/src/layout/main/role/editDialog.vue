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
      <a-form-item label="角色名：" style="width: 200px;">
        <a-input v-model:value="props.user.name" placeholder="请输入角色名">
        </a-input>
      </a-form-item>
      <a-form-item label="角色描述：" style="width: 400px;">
        <a-input v-model:value="props.user.description" placeholder="请输入角色描述">
        </a-input>
      </a-form-item>
      <a-form-item>
        <PermissionDialog :checkedKeys="havePermission" :permissionList="permissionList.permissionList" :getTitle="getTitle" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import { onMounted, ref, reactive, watch } from 'vue';
import { Campus, Ward, Office, } from '../bedview/types/Bed';
import PermissionDialog from './permissionDialog.vue';
import { roleEditApi } from '@/api/user';
import { message } from 'ant-design-vue';
import { getPermissionList,getRolePermission} from '@/api/permission/index';

const props = defineProps<{
  user: any
}>()
const visible = ref<boolean>(false);
  const permissionList = reactive({
  permissionList: [] as any
})

const havePermission = ref([])
// console.log(props.user);
const showModal = async() => {
  let { data } = await getPermissionList({})
  let rolePermission = await getRolePermission(props.user.id)
  havePermission.value = rolePermission.data.permission
  permissionList.permissionList.splice(0, permissionList.permissionList.length, ...data)
  
  visible.value = true;
  // console.log(data)
  
};

const titles = ref()
const add = ref<string[]>([])
const del = ref<string[]>([])
const getTitle = (newTitles: any,checkedKeys?:string[]) => {
  titles.value = newTitles
  if(checkedKeys){
    // 初始化add和del数组
// 找出array2比array1多的项
add.value.push(...checkedKeys.filter(item => !havePermission.value.includes(item)));

// 找出array2比array1少的项
del.value.push(...havePermission.value.filter((item:any) => !checkedKeys.includes(item)));
  }
}

const handleOk = async () => {
  visible.value = false;
  
  let params = {
    roleId: props.user.id,
    // roleName: props.user.name,
    // description: props.user.description,
    // permissions: titles.value
    add: add.value,
    del: del.value,
  }
  console.log('bianji', params)
  await roleEditApi(params)
  message.success('编辑角色成功')
};

const handleCancel = () => {
  visible.value = false;
};

</script>
