<template>
  <MenuLogo />
  <a-menu v-model:openKeys="menuData.openKeys" v-model:selectedKeys="menuData.selectedKeys" :mode="menuData.mode"
    :theme="menuData.theme" :inline-collapsed="menuData.collapsed">
    <!-- <a-menu-item key="/extendedMsgDataDictionary">
      <template #icon>
        <ReadOutlined />
      </template>
<RouterLink to="/extendedMsgDataDictionary">床位扩展数据字典</RouterLink>
</a-menu-item> -->
    <!-- <a-sub-menu key="/schedulingDataManagement">
      <template #icon>
        <AppstoreOutlined />
      </template>
      <template #title>床位调度数据管理</template>
      <a-menu-item key="/bedData">
        <RouterLink to="/bedData">床位数据</RouterLink>
      </a-menu-item>
      <a-menu-item key="/extendedDataManagement">
        <RouterLink to="/extendedDataManagement">床位扩展数据管理</RouterLink>
      </a-menu-item>
    </a-sub-menu> -->
    <a-menu-item key="/bedView">
      <template #icon>
        <tablet-outlined />
      </template>
      <RouterLink to="/bedView">床位管理</RouterLink>
    </a-menu-item>
    <a-menu-item key="/patientScreening">
      <template #icon>
        <meh-outlined />
      </template>
      <RouterLink to="/patientScreening">患者管理</RouterLink>
    </a-menu-item>
    <a-menu-item key="/bedAllocation">
      <template #icon>
        <api-outlined />
      </template>
      <RouterLink to="/bedAllocation">床位分配</RouterLink>
    </a-menu-item>
    <a-menu-item key="/reportStatistics">
      <template #icon>
        <UserOutlined />
      </template>
      <RouterLink to="/user">用户管理</RouterLink>
    </a-menu-item>
    <a-menu-item key="/role">
      <template #icon>
        <solution-outlined />
      </template>
      <RouterLink to="/role">角色管理</RouterLink>
    </a-menu-item>
  </a-menu>
</template>
<script setup lang='ts'>
import { useRoute, RouterLink } from 'vue-router';
import MenuLogo from './MenuLogo.vue';
import { routes } from '@/router';
import { watch, reactive, onMounted } from 'vue'
import type { MenuMode, MenuTheme } from 'ant-design-vue';
import { useMenuStore } from '@/store/menuStore';
const menuStore = useMenuStore();
const route = useRoute()
const menuData = reactive({
  collapsed: false,
  mode: 'inline' as MenuMode,
  theme: 'dark' as MenuTheme,
  selectedKeys: [''],
  openKeys: [''],
})
//设置当前选中的菜单
const selectkey = () => {
  menuData.selectedKeys.splice(0,menuData.selectedKeys.length)
  menuData.selectedKeys.push(route.path)
  console.log('menuData.selectedKeys',menuData.selectedKeys)
}

//解决刷新之后，选中菜单，有上级的时候，打开上级菜单
// const setMenuOpen = (result: any) => {
//   for (let i = 0; i < routes.length; i++) {
//     if (result[i].children) {
//       for (let y = 0; y < result[i].children.length; y++) {
//         if (result[i].children[y].path === route.path) {
//           menuData.openKeys = [result[i].path]
//         }
//       }
//     }
//   }
// };

// SubMenu 展开/关闭的回调,解决一次只能打开一个菜单
// const onOpenChange = (openKeys: string[]) => {
//   if (openKeys.length !== 0) {
//     menuData.openKeys = [openKeys[1]]
//   } else {
//     menuData.openKeys = ['']
//   }
// };

//路由发生变化
watch(() => route.path, (newPath) => {
  selectkey()
  setMenuOpen(newPath);
})
onMounted(() => {
  selectkey()
  setMenuOpen(route.path)
}
)

function setMenuOpen(currentPath:any) {
  // 这里需要根据路由和菜单结构来设置 openKeys
  // 例如，找到与当前路由匹配的菜单项，并展开其父级菜单
  // const pathList = newPath.split('/').filter(Boolean);
  // const openKey = `/${pathList.join('/')}`;
  menuStore.openKeys = [currentPath];
}


</script>

<style scoped lang="scss"></style>
