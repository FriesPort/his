<template>
  <a-button type="link" @click="showModal">权限分配</a-button>
  <a-modal v-model:visible="visible" title="权限分配" @ok="handleOk">
    <template #footer>
      <a-button key="submit" type="primary" @click="handleOk">确认</a-button>
    </template>
    <a-transfer v-model:target-keys="oriTargetKeys.keys" v-model:selected-keys="selectedKeys" :data-source="mockData"
      :titles="['未拥有权限', '已拥有权限']" :render="(item: any) => item.title" @change="handleChange"  
      @selectChange="handleSelectChange"/>
  </a-modal>
</template>
<script lang="ts" setup>
import { allocationPermission, deletePermission, getRoleListApi, getUserRole, userCustomize } from '@/api/user';
import { message } from 'ant-design-vue';
import { reactive, readonly, ref, watch } from 'vue';

const props = defineProps<{
  user: any
}>()
const visible = ref<boolean>(false);
const roles = reactive([
  {
    roleId: '',
    roleName: '',
    description: ''
  }
])
const showModal = async () => {
  let user_id = props.user.userId
  let params = {
    user_id: props.user.userId
  }
  
  let roleList = await getRoleListApi()
  let userRoleList = await getUserRole(params)
  // console.log('id',user_id)
  // console.log('未有',roleList)
  // console.log('已有',userRoleList)

  roles.splice(0, roles.length, ...roleList.data)
  // console.log('roles',roles)
  // 遍历用户角色列表，填充 userRolesKeys
  userRoleList.data.forEach((userRole: any) => {
    // 检查 roleList 中是否存在对应的 roleId
    if (roleList.data.some((role: any) => role.roleId === userRole.roleid)) {
      // 如果存在，添加到 userRolesKeys 中
      oriTargetKeys.keys.push(userRole.roleid);
    }
  });
  updateMockData()
  visible.value = true;
};
const title = ref([] as any)
import { useTokenStore } from '@/store/token.ts';
import Title from 'ant-design-vue/es/typography/Title';
import { mock } from 'node:test';
const tokenStore = useTokenStore();
const handleOk = async () => {
  visible.value = false;
};

const handleChange = async (nextTargetKeys: string[], direction: string, moveKeys: string[]) => {
  console.log('targetKeys: ', nextTargetKeys);
  console.log('direction: ', direction);
  console.log('moveKeys: ', moveKeys);
  if(direction === 'left') {
    title.value.splice(0,title.value.length)
    let mock = mockData.find(item => item.key === moveKeys[0])
    if (mock) title.value.push(mock.title)
    
    let params = {
      user_id: props.user.userId,
    delete_role:title.value
    // delete_role: ["系统管理员","床位管理员"]
    }
    console.log('删除权限',params)
    await deletePermission(params)
    message.success('删除权限成功')
  }
  if(direction === 'right') {
    // let params = {
    //   token:tokenStore.token,
    //   userId: props.user.userId,
    //   delete_permission:{
    //     "1":"R2",
    //     "2":"R4"
    // }
    // }
    title.value.splice(0,title.value.length)
    let mock = mockData.find(item => item.key === oriTargetKeys.keys[0])
    if (mock) title.value.push(mock.title)
  console.log('mock.title',title.value)
    let params = {
      username: props.user.username,
      roles:title.value
    }
    message.success('分配权限成功')
    console.log('params',params)
    await userCustomize(params)
  }
};

const handleSelectChange = (sourceSelectedKeys: string[], targetSelectedKeys: string[]) => {
  console.log('sourceSelectedKeys: ', sourceSelectedKeys);
  console.log('targetSelectedKeys: ', targetSelectedKeys);
};
// const handleSelectChange = (sourceSelectedKeys: string[], targetSelectedKeys: string[]) => {
//   console.log('sourceSelectedKeys: ', sourceSelectedKeys);
//   console.log('targetSelectedKeys: ', targetSelectedKeys);
// };
// const handleScroll = (direction: string, e: Event) => {
//   console.log('direction:', direction);
//   console.log('target:', e.target);
// };
interface MockData {
  key: string;
  title: string;
  disabled: boolean;
}
const mockData: MockData[] = reactive([
  {
    key: '1',
    title: '系统管理员',
    disabled: false,
  },
  {
    key: '2',
    title: '床位管理员',
    disabled: false,
  },
  {
    key: '3',
    title: '患者管理员',
    disabled: false,
  },
  {
    key: '4',
    title: '总管理员',
    disabled: false,
  },
  {
    key: '5',
    title: '床位分配助理',
    disabled: false,
  },
])
const oriTargetKeys = reactive(
  { keys: [] as any }
)
// 确保 mockData 的长度与 roles 保持一致
const updateMockData = () => {
  mockData.splice(0, mockData.length, ...roles.map(role => ({
    key: role.roleId,
    title: role.roleName,
    disabled: false,
  })));
}
const selectedKeys = ref<string[]>([]);



</script>
