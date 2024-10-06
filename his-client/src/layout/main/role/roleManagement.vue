<template>
  <!-- 搜索栏 -->
  <a-form layout="inline" style="margin: 25px 16px;">
    <addDialog :getRoleList="getRoleList"/>
  </a-form>
  <!-- 表格 -->
  <a-table :scroll="{ x: 666, y: '84vh' }" :pagination="page" :dataSource="tableList.list" :columns="columns" bordered
    :style="{ width: '1080px' }">
    <template #bodyCell="{ column, record }">
      <!-- <template v-if="column.key === 'action'">
        <editDialog :user="record" />
        <a-popconfirm title="是否确认删除此用户?" okText="是" cancelText="否" @confirm="deleteBtn(record.id)">
          <a-button type="danger">
            <template #icon>
              <delete-outlined />
            </template>
            删除
          </a-button>
        </a-popconfirm>
        <permissionDialog :user="record" />
      </template> -->
    </template>
  </a-table>
</template>
<script setup lang='ts'>
import { message, TableColumnsType } from 'ant-design-vue';
import { onMounted, reactive, ref, watch } from 'vue';
import addDialog from './addDialog.vue'
import editDialog from './editDialog.vue'
import permissionDialog from './permissionDialog.vue'
import { getRoleListApi } from '@/api/user';

//表格高度
const tableHeight = ref(0)
const columns: TableColumnsType = [
  // { title: '编号', width: 28, dataIndex: 'id', key: 'r1', },
  { title: '角色名', width: 20   , dataIndex: 'roleName', key: 'r2', },
  { title: '角色描述', width: 66, dataIndex: 'description', key: 'r3', },
  // {
  //   title: '角色操作',
  //   key: 'action',
  //   fixed: 'right',
  //   width: 18,
  // },
];
//表格数据
const tableList = reactive({
  list: [
    // {
    //   roleId: 1,
    //   roleName: '老李',
    //   description: '用户管理员，可对用户与角色进行增删改查'
    // },
    // {
    //   roleId: 2,
    //   roleName: '老张',
    //   description: '床位管理员，可对床位进行增删改查'
    // },
    // {
    //   roleId: 3,
    //   roleName: '老李1',
    //   description: '患者管理员，可对患者进行增删改查'
    // },
    // {
    //   roleId: 4,
    //   roleName: '老李2',
    //   description: '床位分配员，可对床位进行分配'
    // },
    // {
    //   roleId: 5,
    //   roleName: '老李3',
    //   description: '用户管理员，可对用户与角色进行增删改查'
    // }
  ]
})

//分页对象
const page = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  pageSizeOptions: ['10', '20', '30', '50'],
  showTotal: (total: number) => `共有${total}条数据`,
  //页数改变时触发
  onChange: (current: number, size: number) => {
    page.current = current;
    page.pageSize = size;
    getRoleList()
  }
})
// 获取表格的数据
const getRoleList = async () => {
  let res = await getRoleListApi()
      //设置表格的数据
      tableList.list = res.data
}

// //删除
// const deleteBtn = async (id: number) => {
//   console.log('id', id)
//   let params = {
//     roleId: id
//   }
//   await roleDeleteApi(params)
//   message.success('角色删除成功')
//   getRoleList()
// }

onMounted(() => {
  getRoleList();
  //计算表格的高度
  tableHeight.value = window.innerHeight - 300
})

</script>
<style scoped lang='scss'></style>