import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Layout from "../layout/index.vue";
import ExtendedMsgDataDictionary from "@/view/content/ExtendedMsgDataDictionary.vue";
import BedData from "@/view/content/schedulingDataManagement/BedData.vue";
import ExtendedDataManagement from "@/view/content/schedulingDataManagement/ExtendedDataManagement.vue";
import BedView from "@/view/content/BedView.vue";
import PatientScreening from "@/view/content/PatientScreening.vue";
import BedAllocation from "@/view/content/BedAllocation.vue";
import User from "@/view/content/User.vue";
import Role from "@/view/content/Role.vue";
import { useAuthStore } from '@/store/authStore'
import { useUserStore } from '@/store/userStore';
import { message } from 'ant-design-vue';
import { useMenuStore } from '@/store/menuStore';


export const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/view/login/Login.vue'),
  },
  {
    path: '/',
    component: Layout,
    redirect: '/bedView',
    children: [
      {
        path: '/extendedMsgDataDictionary',
        name: 'extendedMsgDataDictionary',
        component: ExtendedMsgDataDictionary,
        meta: {
          title: '床位扩展数据字典',
          requiresAuth: true,
          roles: ["sys:extendedMsgDataDictionary"],
        }
      },
      {
        path: '/schedulingDataManagement',
        name: 'schedulingDataManagement',
        meta: {
          title: '床位调度数据管理',
          requiresAuth: true,
          roles: ["sys:schedulingDataManagement"]
        },
        children: [
          {
            path: '/bedData',
            name: 'bedData',
            component: BedData,
            meta: {
              title: '床位数据',
              requiresAuth: true,
              roles: ["sys:bedData"],
            }
          },
          {
            path: '/extendedDataManagement',
            name: 'extendedDataManagement',
            component: ExtendedDataManagement,
            meta: {
              title: '床位扩展数据管理',
              requiresAuth: true,
              roles: ["sys:extendedDataManagement"],
            }
          }
        ]
      },
      {
        path: '/bedView',
        name: 'bedView',
        component: BedView,
        meta: {
          title: '床位管理',
          requiresAuth: true,
          roles: ["sys:bedView"],
        }
      },
      {
        path: '/patientScreening',
        name: 'patientScreening',
        component: PatientScreening,
        meta: {
          title: '患者管理',
          requiresAuth: true,
          roles: ["sys:patientScreening"],
        }
      },
      {
        path: '/bedAllocation',
        name: 'bedAllocation',
        component: BedAllocation,
        meta: {
          title: '床位分配',
          requiresAuth: true,
          roles: ["sys:bedAllocation"],
        }
      },
      {
        path: '/user',
        name: 'user',
        component: User,
        beforeEnter: (to, from, next) => {
          const userStore = useUserStore();
          const menuStore = useMenuStore();
          if (userStore.permissions.includes('用户管理')) {
            next();
          } else {
            message.success('权限不足')
            // 直接更新 Pinia store 的状态
             menuStore.openKeys = ['/bedView'];
            next('/bedView'); // 重定向到未授权页面
          }
        },
        meta: {
          title: '用户管理',
          requiresAuth: true,
          roles: ["sys:user"],
        }
      },
      {
        path: '/role',
        name: 'role',
        component: Role,
        beforeEnter: (to, from, next) => {
          const userStore = useUserStore();
          const menuStore = useMenuStore();
          if (userStore.permissions.includes('用户管理')) {
            next();
          } else {
            message.success('权限不足')
            // 直接更新 Pinia store 的状态
            menuStore.openKeys = ['/bedView'];
            next('/bedView'); // 重定向到未授权页面
          }
        },
        meta: {
          title: '用户角色',
          requiresAuth: true,
          roles: ["sys:Role"],
        }
      }
    ]
  }

]
const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
// router.beforeEach((to, from, next) => {
//   //.some() 方法用于检测 to.matched 数组中是否有至少一个路由记录的 meta 属性中的 requiresAuth 属性为 true。如果是这样，requiresAuth 变量将被赋值为 true，表示需要身份验证。
//   const requiresAuth = to.matched.some(route => route.meta.requiresAuth);
//   const isAuthenticated = useAuthStore().isAuthenticated;

//   if (requiresAuth && !isAuthenticated) {
//     // 如果需要登录但用户未登录，重定向到登录页面
//     return next('/login');
//   }

//   next();
// });


export default router