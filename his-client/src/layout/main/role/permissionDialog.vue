<template>
  <a-button type="link" @click="showModal">权限分配</a-button>
  <a-modal v-model:visible="visible" title="权限分配" @ok="handleOk" :style="{ width: '360px' }">
    <template #footer>
      <a-button key="back" @click="handleCancel">取消</a-button>
      <a-button key="submit" type="primary" @click="handleOk">确认</a-button>
    </template>
    <a-tree v-model:expandedKeys="expandedKeys" v-model:selectedKeys="selectedKeys" v-model:checkedKeys="checkedKeys"
      checkable :tree-data="treeData.treeData">
      <template #title="{ title, key }">
        <span v-if="key === '0-0-1-0'" style="color: #1890ff">{{ title }}</span>
        <template v-else>{{ title }}</template>
      </template>
    </a-tree>
  </a-modal>
</template>
<script lang="ts" setup>
import { computed, reactive, ref, watch } from 'vue';
import type { TransferProps, TreeProps } from 'ant-design-vue';

const props = defineProps<{
  permissionList: any,
  getTitle:any
}>()
const visible = ref<boolean>(false);

const showModal = () => {
  // 执行函数更新 treeData
  fillTreeDataWithPermissions(props.permissionList);
  visible.value = true;
};

const handleOk = () => {
  // 当点击确认按钮时，输出当前勾选的值
  console.log('勾选的节点:', checkedKeys.value);
  // 遍历勾选的 keys，并获取每个节点的 title
  const titles = reactive({titles:[] as any})
  checkedKeys.value.forEach((key) => {
    const title = getTreeNodeTitle(key, treeData.treeData);
    if (title !== undefined) { // 只有当title存在时才添加
      titles.titles.push(title);
      console.log(`节点 ${key} 的标题是: ${title}`);
    }
  });
  console.log('从权限模块输出的titles：',titles.titles)
  props.getTitle(titles.titles)
  visible.value = false;
};
const getTreeNodeTitle = (key: string, treeData: any) => {
  // 遍历树数据
  for (const node of treeData) {
    // 检查当前节点的 key 是否与给定 key 匹配
    if (node.key === key) {
      return node.title; // 如果匹配，返回当前节点的 title
    }
    // 由于只需要最外层的 title，我们不需要递归检查 node.children
  }
  // return undefined; // 如果没有找到匹配的 key，返回 undefined
};
const handleCancel = () => {
  visible.value = false;
};
const treeData = reactive({
  treeData: [
    {
      title: '用户管理',
      key: '0-1',
      children: [
        {
          title: '新增',
          key: '1-1',
          disabled: false,
        },
        {
          title: '编辑',
          key: '1-2',
          disabled: false,
        },
        {
          title: '查看',
          key: '1-3',
          disabled: false,
        },
        {
          title: '删除',
          key: '1-4',
          disabled: false,
        },
      ],
    },
    {
      title: '角色管理',
      key: '0-2',
      children: [
        {
          title: '新增',
          key: '2-1',
          disabled: false,
        },
        {
          title: '编辑',
          key: '2-2',
          disabled: false,
        },
        {
          title: '查看',
          key: '2-3',
          disabled: false,
        },
        {
          title: '删除',
          key: '2-4',
          disabled: false,
        },
      ],
    },
    {
      title: '床位管理',
      key: '0-3',
      children: [
        {
          title: '查看',
          key: '3-1',
          disabled: false,
        },
        {
          title: '新增',
          key: '3-2',
          disabled: false,
        },
        {
          title: '分配',
          key: '3-3',
          disabled: false,
        },
        {
          title: '释放',
          key: '3-4',
          disabled: false,
        },
        {
          title: '删除',
          key: '3-5',
          disabled: false,
        },
      ],
    },
    {
      title: '患者管理',
      key: '0-4',
      children: [
        {
          title: '新增',
          key: '4-1',
          disabled: false,
        },
        {
          title: '编辑',
          key: '4-2',
          disabled: false,
        },
        {
          title: '查看',
          key: '4-3',
          disabled: false,
        },
        {
          title: '删除',
          key: '4-4',
          disabled: false,
        },
      ],
    },
  ]
})

function fillTreeDataWithPermissions(permissions: any) {
  // 清空原始的treeData
  treeData.treeData.splice(0,treeData.treeData.length);

  // 根据permissionList填充treeData
  permissions.forEach((permission: { permissionName: any; permissionType: any; permissionId: any; }) => {
    // 查找是否存在于treeData中的相应父节点
    const parentNode = treeData.treeData.find((node: { title: any; }) => node.title === permission.permissionName);
    if (parentNode) {
      // 如果父节点存在，添加子节点
      parentNode.children.push({
        title: permission.permissionType,
        key: permission.permissionId,
        disabled: false
      });
    } else {
      // 如果父节点不存在，创建新的父节点和子节点
      treeData.treeData.push({
        title: permission.permissionName,
        key: permission.permissionName, // 使用permissionName作为key
        children: [
          {
            title: permission.permissionType,
            key: permission.permissionId,
            disabled: false
          }
        ]
      });
    }
  });
}

const expandedKeys = ref<string[]>([]);
const selectedKeys = ref<string[]>([]);
const checkedKeys = ref<string[]>([]);
watch(expandedKeys, () => {
  console.log('expandedKeys', expandedKeys);
});
watch(selectedKeys, () => {
  console.log('selectedKeys', selectedKeys);
});
watch(checkedKeys, () => {
  console.log('checkedKeys', checkedKeys);
});

</script>
<style scoped>
.tree-transfer .ant-transfer-list:first-child {
  width: 50%;
  flex: none;
}
</style>