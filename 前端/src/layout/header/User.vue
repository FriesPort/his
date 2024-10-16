<template>
  <div>
    <a-button :style="{ border: 'none' }" @click="showPopover">
      <template #icon>
        <UserOutlined />
      </template>
      <span>{{userName}}</span>
      <span class="iconfont icon-RectangleCopy"></span>
    </a-button>
    <a-popover v-model:visible="popoverVisible" placement="bottom" width="auto" trigger="click">
      <template #content>
        <a-button :style="{ display: 'block', width: '100%' }" @click="handleModifyPassword">
          修改密码
        </a-button>
        <a-button :style="{ display: 'block', width: '100%' }" @click="handleLogout">
          退出登录
        </a-button>
      </template>
    </a-popover>
  </div>
</template>

<script setup lang="ts">
import { UserOutlined } from '@ant-design/icons-vue';
import { onMounted, reactive, ref } from 'vue';
import { useAuthStore } from '@/store/authStore';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/userStore';
import { getListApi } from '@/api/user';
const userStore = useUserStore();
const user_id = userStore.userId
const userList = reactive({
  users: [] as any
})
const userName = ref()
onMounted( async () => {
  let params = {
    campusId: 'all',
    name:  'all',
    employeeNumber:  'all'
  }
  let res = await getListApi(params)
  userList.users = res.data
  const user = userList.users.find((item: any) => item.userId === user_id )
  userName.value = user?.username
})
const authStore = useAuthStore();
const router = useRouter();

const popoverVisible = ref(false);

const showPopover = () => {
  popoverVisible.value = true;
};

const handleModifyPassword = () => {
  popoverVisible.value = false;
  // 处理修改密码逻辑
};

const handleLogout = () => {
  popoverVisible.value = false;
  // 处理退出登录逻辑
  // 执行注销动作
  authStore.logout();
  // 重定向到登录页面
  router.push('/login');
};
</script>

<style scoped lang="scss"></style>