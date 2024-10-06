<template>
  <div class="login-container">
    <a-form class="loginForm">
      <a-form-item>
        <span class="loginTitle">医院床位管理平台</span>
      </a-form-item>
      <a-form-item v-bind="validateInfos.username">
        <a-input v-model:value="loginModel.username" placeholder="请输入账户">
          <template #prefix>
            <UserOutlined style="color: rgba(0, 0, 0, 0.25)" />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item v-bind="validateInfos.password">
        <a-input v-model:value="loginModel.password" type="password" placeholder="请输入密码">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)" />
          </template>
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-row :gutter="[16, 16]">
          <a-col :span="12">
            <a-button @click="login" class="mybtn" type="primary">登录</a-button>
          </a-col>
          <a-col :span="12">
            <a-button @click="resetBtn" class="mybtn">重置</a-button>
          </a-col>
        </a-row>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import useForm from "ant-design-vue/es/form/useForm";
import { reactive, ref } from 'vue'
import { useAuthStore } from '@/store/authStore'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
const authStore = useAuthStore();
const router = useRouter();
// const username = ref('')
// const password = ref('') // 这将输出 'undefined' 如果变量未定义

const loginModel = reactive({
  username: "",
  password: "",
});
//定义表单验证规则
const rules = reactive({
  username: [
    {
      required: true,
      trigger: "change",
      message: "请输入账户",
    },
  ],
  password: [
    {
      required: true,
      trigger: "change",
      message: "请输入密码",
    },
  ],
});
//获取表单验证属性
const { validate, validateInfos, clearValidate } = useForm(loginModel, rules);
interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
}
//调用后台接口,完成注册
import { getUserRole, userLoginService } from '@/api/user.ts'
//登录函数
import { useTokenStore } from '@/store/token.ts'
import { useUserStore } from "@/store/userStore";
const dangerChars = /[=,"'<>!\/\\?#%&{}[\]() ;]+/
const containsDangerousChars = (str: any) => {
  return dangerChars.test(str);
}
const tokenStore = useTokenStore();
const userStore = useUserStore();
const login = () => {
  validate().then(async () => {
    if (containsDangerousChars(loginModel.username) || containsDangerousChars(loginModel.password)) {
      message.warning('输入包含危险字符，请重新输入！');
      return;
    }
    try {
      // 调用接口，完成登录
      // let params = {
      //   username: username.value,
      //   password: password.value
      // };
      let result = await userLoginService(loginModel);


      console.log('返回的信息', result);
      message.success(result.data.msg ? result.data.msg : '登录成功');
      // 把得到的 token 存储到 Pinia 中
      tokenStore.setToken(result.data.token);
      console.log('第一个result.data.user_id',result.data.user_id)
      
      let params = {
    user_id: result.data.user_id
  }
  
  console.log('请求params',params)
  let userRoleList = await getUserRole(params)
  console.log('userRoleList.data.permission',userRoleList)
 // 首先，从 userRoleList 中获取 data 属性
const rolesData = userRoleList.data;

// 使用 map 来提取每个角色的 permission 数组，然后再次使用 map 提取每个 permission 的 permissionName
const allPermissionNames = rolesData.map((role:any) => role.permission)
  .flat() // 将嵌套数组展平成一个数组
  .map((permission: any) => permission.permissionName); // 提取 permissionName

// 现在 allPermissionNames 包含了所有权限的 permissionName
console.log('所有权限名称:', allPermissionNames);
    userStore.setUser(params.user_id);
    userStore.setPermissions(allPermissionNames);

      // 登录成功，更新状态
      authStore.login();
      // 跳转到首页
      router.push('/bedView');
    } catch (error: any) {
      // 捕获异步操作中的错误
      message.error(error.data.loginstatus);
    }
  })
}
//重置按钮
const resetBtn = () => {
  loginModel.username = "";
  loginModel.password = "";
  clearValidate();
};
</script>
<style scoped lang="scss">
.login-container {
  height: 100%;
  background: #fff;
  //background-image: url("@/assets/login.png");
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: 100% 100%;

  .loginForm {
    height: 340px;
    width: 450px;
    background: #fff;
    padding: 35px 20px;
    border-radius: 10px;

    .loginTitle {
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 24px;
      font-weight: 600;
      color: #1890ff;
    }

    .mybtn {
      width: 100%;
    }
  }
}
</style>
