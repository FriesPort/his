<template>
  <!-- 搜索栏 -->
  <a-form layout="inline" style="margin: 25px 16px;">
    <a-form-item>
      <span :style="{ marginRight: '10px' }">院区</span>
      <a-select v-model:value="selectedCampusName" @change="handleChange" style="width: 120px;">
        <a-select-option v-for="(campusItem, index) in campusData" :key="campusItem.campusId"
          :value="campusItem.campusName">
          <span class="dropdown">{{ campusItem.campusName }}</span>
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item>
      <a-input v-model:value="listParm.name" placeholder="请输入姓名"></a-input>
    </a-form-item>
    <a-form-item>
      <a-input v-model:value="listParm.employeeNumber" placeholder="请输入工号"></a-input>
    </a-form-item>
    <a-button type="primary" shape="circle" style="margin-right: 15px;" @click="searchBtn">
      <template #icon>
        <SearchOutlined />
      </template>
    </a-button>
    <a-button @click="resetBtn" style="margin-right: 15px;" type="danger">
      <template #icon>
        <close-outlined />
      </template>
      重置
    </a-button>
    <addDialog :getUserList="getUserList" />
  </a-form>
  <!-- 表格 -->
  <a-table :scroll="{ x: 1500, y: '60vh' }" :pagination="page" :dataSource="tableList.list" :columns="columns" bordered>
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'action'">
        <editDialog :user="record" :getUserList="getUserList" />

        <a-popconfirm title="是否确认删除此用户?" okText="是" cancelText="否" @confirm="deleteBtn(record.userId)">
          <a-button type="danger" @click="recordId(record.userId)">
            <template #icon>
              <delete-outlined />
            </template>
            删除
          </a-button>
        </a-popconfirm>
        <permissionDialog :user="record" />
      </template>
    </template>
  </a-table>
</template>
<script setup lang='ts'>
import { message, TableColumnsType } from 'ant-design-vue';
import { onMounted, reactive, ref, watch } from 'vue';
import { getWardsData } from '@/api/bedView/bedData.ts';
import type { Ward, Office, Campus } from "@/layout/main/bedview/types/Bed.ts";
import addDialog from './addDialog.vue'
import editDialog from './editDialog.vue'
import permissionDialog from './permissionDialog.vue'
import { getListApi, deleteUserApi } from '@/api/user';

interface ListParm {
  campusId: number | null,
  employeeNumber: string | null,
  name: string | null
}
//表格高度
const tableHeight = ref(0)
const columns: TableColumnsType = [
  { title: '姓名', width: 28, dataIndex: 'name', key: 'u1', },
  { title: '用户名', width: 36, dataIndex: 'username', key: 'u2', },
  { title: '工号', width: 42, dataIndex: 'employeeNumber', key: 'u3', },
  { title: '所属院区', dataIndex: 'campusName', key: 'u4', width: 28 },
  { title: '手机号码', dataIndex: 'phone', key: 'u6', width: 42 },
  { title: '账号状态', dataIndex: 'isEnable', key: 'u7', width: 32 },
  {
    title: '用户操作',
    key: 'action',
    fixed: 'right',
    width: 60,
  },
];
//表格数据
const tableList = reactive({
  list: [
    // {
    //   userId: '1',
    //   username: '1',
    //   name: '老李',
    //   employeeNumber: '20240404',//工号
    //   phone: '手机号',
    //   campusName: '院本部',
    //   isEnable: 1 //状态
    // },
    // {
    //   userId: '2',
    //   username: '2',
    //   name: '老张',
    //   employeeNumber: '20240405',
    //   phone: '手机号',
    //   campusName: '院本部',
    //   isEnable: 1 //状态
    // },
    // {
    //   userId: '3',
    //   username: '1',
    //   name: '老李1',
    //   employeeNumber: '20240406',
    //   phone: '手机号',
    //   campusName: '院本部',
    //   isEnable: 1 //状态
    // },
    // {
    //   userId: '4',
    //   username: '11',
    //   name: '老李2',
    //   employeeNumber: '20240407',
    //   phone: '手机号',
    //   campusName: '院分部',
    //   isEnable: 1 //状态
    // },
    // {
    //   userId: '5',
    //   username: '12',
    //   name: '老李3',
    //   employeeNumber: '20240408',
    //   phone: '手机号',
    //   campusName: '院分部',
    //   isEnable: 1 //状态
    // }
  ]
})
//表单列表参数
const listParm = reactive<ListParm>({
  campusId: null,
  employeeNumber: null,
  name: null
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
  }
})
// 获取表格的数据
const getUserList = async () => {
  const campusId = ref()
  const name = ref()
  const employeeNumber = ref()
  if(listParm.campusId===null) campusId.value = 'all'
  else campusId.value = listParm.campusId
  if(listParm.employeeNumber===null || listParm.employeeNumber==='') employeeNumber.value = 'all'
  else employeeNumber.value = listParm.employeeNumber
  if(listParm.name===null || listParm.name==='') name.value = 'all'
  else name.value = listParm.name
  let params = {
    campusId: campusId.value,
    name: name.value,
    employeeNumber: employeeNumber.value
  }
  console.log('params',params)
  let res = await getListApi(params)
  tableList.list = res.data

}
//搜索
const searchBtn = () => {
  getUserList()
}
//重置
const resetBtn = () => {
  //清空表单
  listParm.name = ''
  listParm.employeeNumber = ''
}
const recordUserId = ref()
const recordId = (id:string) =>{
  recordUserId.value = id
}
//删除
const deleteBtn = async (id: number) => {
  console.log('id', id)
  let params = {
    user_id: id
  }
  await deleteUserApi(params)
  message.success('用户删除成功')
  getUserList()
}

onMounted(() => {
  getUserList();
  //计算表格的高度
  tableHeight.value = window.innerHeight - 300
})

const campusData = reactive<Campus[]>([])
// 用于 v-model 的响应式变量，存储选中的 campusName
const selectedCampusName = ref('');
// 处理选择变化的函数
const handleChange = (value: string) => {
  // 找到匹配选中 campusName 的 campusItem
  const selectedCampus = campusData.find(
    item => item.campusName === value
  );
  // 如果找到了，更新 recordCampusId 的值
  if (selectedCampus) {
    listParm.campusId = selectedCampus.campusId === -1 ? null : selectedCampus.campusId;
  }
};

onMounted(async () => {
  // let { data } = await axios.get('http://localhost:3000/beds');
  let { data } = await getWardsData();
  let allOption: Campus = {
    campusId: -1,
    campusName: '全部',
    offices: [{
      officeId: -1, // 假设 -1 是一个无效的 officeId，用来表示 '全部'
      officeName: '全部',
      wards: [{
        wardId: -1,
        wardName: '全部'
      }]
    }]
  };
  campusData.splice(0, 0, allOption); // 在 index 0 的位置添加 allOption
  campusData.splice(1, campusData.length - 1, ...data); // 从 index 1 开始替
  listParm.campusId = null;
  selectedCampusName.value = campusData[0].campusName
})
</script>
<style scoped lang='scss'></style>