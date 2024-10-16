<template>
  <component @click="clickBtn" :is="isCollapse ? MenuUnfoldOutlined :
  MenuFoldOutlined" class="icons" />
  </template>
  <script setup lang="ts">
  import {computed } from 'vue'
  import { MenuUnfoldOutlined, MenuFoldOutlined} from '@ant-design/icons-vue';
  import { useCollapseStore } from '@/store/collapse'
  const collapseStore = useCollapseStore()
  const isCollapse = computed(() => {
  return collapseStore.getCollapse
  })
  const clickBtn = ()=>{
    collapseStore.setCollapse();
  }
  </script>
  <style scoped lang='scss'>
  .icons {
  font-size: 21px;
  }
  </style>

  <!-- <template>
    <component @click="clickBtn" :is="isCollapse ? MenuUnfoldOutlined :
    MenuFoldOutlined" class="icons" />
    </template>
    <script setup lang="ts" name="Collapse">
    import {computed } from 'vue'
    import { MenuUnfoldOutlined, MenuFoldOutlined} from '@ant-design/icons-vue';
    import { collapseStore } from '@/store/collapse/index'
    const store = collapseStore()
    const isCollapse = computed(() => {
    return store.getCollapse
    })
    const clickBtn = ()=>{
    store.setCollapse();
    }
    </script>
    <style scoped lang='scss'>
    .icons {
    font-size: 21px;
    }
    </style> -->