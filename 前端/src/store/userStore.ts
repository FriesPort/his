// store/userStore.ts
import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  state: () => ({
    userId: null,
    permissions: [] as any,
  }),
  actions: {
    setUser(userId: any) {
      this.userId = userId;
    },
    setPermissions(getPermissions: any) {
      this.permissions = getPermissions;
    },
    // 可以添加更多的actions来处理权限逻辑
  },
});