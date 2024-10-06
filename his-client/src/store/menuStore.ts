// store/menuStore.js
import { defineStore } from 'pinia';

export const useMenuStore = defineStore('menu', {
  state: () => ({
    openKeys: [''],
  }),
  // 您可以添加 actions 和 getters 根据需要
});