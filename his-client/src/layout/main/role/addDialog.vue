<template>
  <div>
    <a-button type="primary" @click="showModal">
      <template #icon>
        <plus-outlined />
      </template>
      新增角色
    </a-button>
    <a-modal v-model:visible="visible" title="新增角色" @ok="handleOk">
      <template #footer>
        <a-button key="back" @click="handleCancel">取消</a-button>
        <a-button key="submit" type="primary" @click="handleOk">新增</a-button>
      </template>
      <a-form>
        <a-form-item label="角色名：" style="width: 200px;">
          <a-input v-model:value="rolename" placeholder="请输入角色名">
          </a-input>
        </a-form-item>
        <a-form-item label="角色描述：" style="width: 200px;">
          <a-input v-model:value="description" placeholder="请输入角色描述">
          </a-input>
        </a-form-item>
        <a-form-item>
          <PermissionDialog :permissionList = "permissionList.permissionList" :getTitle="getTitle"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { onMounted, ref, reactive, watch } from 'vue';
import { Campus, Ward, Office, } from '../bedview/types/Bed';
import PermissionDialog from './permissionDialog.vue';
import { permissionListApi, roleAdd } from '@/api/user';

const props = defineProps<{
  getRoleList:any
}>()
const visible = ref<boolean>(false);

const rolename = ref('')
const description = ref('')
const permissionList = reactive({
  permissionList:[] as any
})
const showModal = async () => {
  let {data} = await permissionListApi()
  console.log('权限列表',data)
  permissionList.permissionList.splice(0,permissionList.permissionList.length,...data)
  visible.value = true;
};
const titles = ref()
const getTitle = (newTitles:any) => {
  titles.value = newTitles
  console.log('titles.value',titles.value)
  
}
const handleOk = async () => {
  visible.value = false;
  
  let params = {
    roleName: rolename.value,
    description: description.value,
    permissions: titles.value
  }
  console.log('新增', params)
  await roleAdd(params)
  props.getRoleList()
};

const handleCancel = () => {
  visible.value = false;
};

</script>
