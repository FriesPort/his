<template>
  <!-- 搜索栏 -->
  <a-form layout="inline" style="margin: 25px 16px;display: flex;justify-content: space-between;">
    <div style="display: flex;">
      <a-form-item>
        <a-input v-model:value="perName" placeholder="请输入权限名称"></a-input>
      </a-form-item>
      <a-button @click="resetBtn" style="margin-right: 15px;">
        <template #icon>
          <close-outlined />
        </template>
        重置
      </a-button>
    </div>
    <div style="display: flex;">
      <addDialog />
    </div>
    <!-- <addDialog :getUserList="getUserList" /> -->
  </a-form>
  <a-table :columns="columns" :data-source="dataSource.List" :style="{ width: '900px' }">
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'is_datarule'">
        <a-switch v-model:checked="record.is_datarule" />
      </template>
      <template v-if="column.key === 'is_use'">
        <a-switch v-model:checked="record.is_use" />
      </template>
      <template v-if="column.key === 'action'">
        <a-button style="margin-right: 8px;" @click="dataRulesEdit(record)">
          <template #icon>
            <DatabaseOutlined />
          </template>
          数据规则
        </a-button>
        <editDialog :record="record" />
        <a-popconfirm title="是否确认删除此角色?" okText="是" cancelText="否" @confirm="deleteBtn(record.id)">
          <a-button type="primary" danger style="margin-left: 8px;">
            <template #icon>
              <delete-outlined />
            </template>
            删除
          </a-button>
        </a-popconfirm>
      </template>
    </template>
  </a-table>
  <!-- 数据规则 -->
  <a-modal v-model:visible="rulesEdit" title="数据规则编辑">
    <template #footer>
      <a-button key="back" @click="rulesCancel">取消</a-button>
      <a-button key="submit" type="primary" @click="rulesOk">确认</a-button>
    </template>
    <a-form>
      <a-form-item label="规则名字：" style="width: 200px;">
        <a-input v-model:value="ruleName" type="text" placeholder="请输入数据规则名字">
        </a-input>
      </a-form-item>
      <a-form-item label="规则列" style="width: 200px;">
        <a-input v-model:value="ruleColumn" type="text" placeholder="请输入数据列名字">
        </a-input>
      </a-form-item>
      <a-form-item label="规则条件：" style="width: 200px;">
        <a-input v-model:value="ruleConditions" placeholder="请输入规则条件">
        </a-input>
      </a-form-item>
      <a-form-item label="规则值：" style="width: 200px;">
        <a-input v-model:value="ruleValue" placeholder="请输入规则值">
        </a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import addDialog from './addDialog.vue'
import editDialog from './editDialog.vue'
import { reactive } from 'vue';
import { ref, onBeforeMount } from 'vue';
import { getPermissionList, permisionRuleEdit, permissionDelete } from '@/api/permission/index';
import { message } from 'ant-design-vue';
const perName = ref('')
//重置
const resetBtn = () => {
  //清空表单
  perName.value = ''
}

onBeforeMount(() => {
  getPermissions()
})


const columns = [
  {
    title: '权限名',
    dataIndex: 'name',
    key: 'name',
    width: 130,
  },
  {
    title: '权限描述',
    dataIndex: 'description',
    key: 'description',
    width: 240,
  },
  // {
  //   title: '启用状态',
  //   dataIndex: 'is_datarule',
  //   key: 'is_datarule',
  //   width: 120,
  // },
  // {
  //   title: '可用状态',
  //   dataIndex: 'is_use',
  //   key: 'is_use',
  //   width: 120,
  // },
  {
    title: '权限操作',
    key: 'action',
    fixed: 'right',
    width: 350,
  },
];

interface DataItem {
  id: string
  name: string;
  description: string;
  // is_datarule: number;
  // is_use: number;
  children?: DataItem[];
}

const dataSource = reactive({ List: [] as DataItem[] })



// const data: DataItem[] = [
//   {
//     key: 1,
//     name: '床位管理',
//     detail: 60,
//     is_datarule: 0,
//     is_use: 1,
//     children: [
//       {
//         key: 14,
//         name: '查看',
//         detail: 72,
//         is_datarule: 0,
//         is_use: 1,
//       },
//       {
//         key: 11,
//         name: '新增',
//         detail: 42,
//         is_datarule: 0,
//         is_use: 1,
//       },
//       {
//         key: 12,
//         name: '编辑',
//         detail: 30,
//         is_datarule: 0,
//         is_use: 1,
//       },
//       {
//         key: 13,
//         name: '删除',
//         detail: 72,
//         is_datarule: 0,
//         is_use: 1,
//       },
//     ],
//   },
//   {
//     key: 2,
//     name: '患者管理',
//     detail: 32,
//     is_datarule: 0,
//     is_use: 1,
//     children: [
//       {
//         key: 14,
//         name: '查看',
//         detail: 72,
//         is_datarule: 0,
//         is_use: 1,
//       },
//       {
//         key: 11,
//         name: '新增',
//         detail: 42,
//         is_datarule: 0,
//         is_use: 1,
//       },
//       {
//         key: 12,
//         name: '编辑',
//         detail: 30,
//         is_datarule: 0,
//         is_use: 1,
//       },
//       {
//         key: 13,
//         name: '删除',
//         detail: 72,
//         is_datarule: 0,
//         is_use: 1,
//       },
//     ],
//   },
//   {
//     key: 3,
//     name: '床位分配',
//     detail: 32,
//     is_datarule: 0,
//     is_use: 1,
//     children: [
//       {
//         key: 14,
//         name: '分配',
//         detail: 72,
//         is_datarule: 0,
//         is_use: 1,
//       },
//       {
//         key: 11,
//         name: '释放',
//         detail: 42,
//         is_datarule: 0,
//         is_use: 1,
//       },
//     ],
//   },
// ];
// dataSource.List.splice(0, dataSource.List.length - 1, ...data)

//数据规则编辑
let rulesEdit = ref(false)
const ruleName = ref('')
const ruleConditions = ref('')
const id = ref('')
const ruleid = ref('')
const ruleColumn = ref('')
const ruleValue = ref('')

const getPermissions = async () => {
  let result = await getPermissionList({})
  console.log(result.data)
  dataSource.List.splice(0, dataSource.List.length - 1, ...result.data)
  console.log('111', dataSource.List);

}
const dataRulesEdit = (record: any) => {
  rulesEdit.value = true
  id.value = record.id
  ruleid.value = record.dataRuleList[0].id
  ruleName.value = record.dataRuleList[0].ruleName
  ruleConditions.value = record.dataRuleList[0].ruleConditions
  ruleColumn.value = record.dataRuleList[0].ruleColumn
  ruleValue.value = record.dataRuleList[0].ruleValue
  console.log(record)

  return

}

const rulesOk = async () => {
  rulesEdit.value = false
  let params = {
    permissionId: id.value,
    id: ruleid.value,
    ruleName: ruleName.value,
    ruleColumn: ruleColumn.value,
    ruleConditions: ruleConditions.value,
    ruleValue: ruleValue.value
  }
  let result = await permisionRuleEdit(params)
  message.success('操作成功')
  return
}

const rulesCancel = () => {
  rulesEdit.value = false
  return
}

const deleteBtn = async (id: string) => {
  let params = {
    id: id
  }
  await permissionDelete(params)
  message.success('角色删除成功')
}

</script>
