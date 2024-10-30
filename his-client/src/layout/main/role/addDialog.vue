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
      <a-form :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-item>
          <a-radio-group v-model:value="addType">
            <a-radio value="1">默认角色</a-radio>
            <a-radio value="2">自定义角色</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="默认角色" style="width: 250px;" :class="defaultClass">
          <a-select placeholder="请选择默认角色">
            <a-select-option v-for="item in defaultRoles" :key="item.id" value="demo">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="角色名：" style="width: 250px;" :class="coustmClass">
          <a-input v-model:value="rolename" placeholder="请输入角色名">
          </a-input>
        </a-form-item>
        <a-form-item label="角色描述：" style="width: 400px;">
          <a-input v-model:value="description" placeholder="请输入角色描述">
          </a-input>
        </a-form-item>
        <a-form-item>
          <PermissionDialog :permissionList="permissionList.permissionList" :getTitle="getTitle" />
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
import { getPermissionList} from '@/api/permission/index';

const labelCol = { style: { width: '80px' } };
const wrapperCol = { span: 14 };

const props = defineProps<{
  getRoleList: any
}>()
const visible = ref<boolean>(false);

//默认角色和自定义角色的选择
const addType = ref('1')
const defaultClass = ref('show')
const coustmClass = ref('hidden')

watch(addType, (newValue: any) => {
  if (newValue == '2') {
    // 隐藏 defaultRole 并展示 coustmRole
    defaultClass.value = 'hidden'
    coustmClass.value = 'show'
  } else {
    // 展示 defaultRole 并隐藏 coustmRole
    defaultClass.value = 'show'
    coustmClass.value = 'hidden'
  }

})
const defaultRoles = ref([
  { id: 1, name: '医生' },
  { id: 2, name: '护士' },
  { id: 3, name: '主治医师' },
  { id: 4, name: '护士长' },
  { id: 5, name: '实习医生' },
])

const rolename = ref('')
const description = ref('')
const permissionList = reactive({
  permissionList: [] as any
})
const showModal = async () => {
  let { data } = await getPermissionList({})
  console.log(data)
  permissionList.permissionList.splice(0, permissionList.permissionList.length, ...data)
  visible.value = true;
};
const titles = ref()
const getTitle = (newTitles: any) => {
  titles.value = newTitles
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
<style scoped lang="scss">
.show {
  display: block;
}

.hidden {
  display: none;
}
</style>