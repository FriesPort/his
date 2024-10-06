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
        <a-input v-model:value="props.user.roleName" placeholder="请输入名字">
        </a-input>
      </a-form-item>
      <a-form-item label="描述：" style="width: 400px;">
        <a-input v-model:value="props.user.description" placeholder="请输入角色描述">
        </a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import { onMounted, ref, reactive, watch } from 'vue';
import { Campus, Ward, Office, } from '../bedview/types/Bed';
// import { roleEditApi } from '@/api/user';
import { message } from 'ant-design-vue';

const props = defineProps<{
  user: any
}>()
const visible = ref<boolean>(false);


const showModal = () => {
  visible.value = true;
};

const handleOk = async () => {
  visible.value = false;
  let params = {
    roleId: props.user.roleId,
    roleName: props.user.roleName,
    description: props.user.description
  }
  console.log('bianji', params)
  // await roleEditApi(params)
  message.success('编辑角色成功')
};

const handleCancel = () => {
  visible.value = false;
};

</script>
