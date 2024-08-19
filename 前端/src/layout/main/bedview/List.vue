<template>
  <a-table :columns="columns" :data-source="bedsData" :scroll="{ x: 1200, y: '62vh' }" :pagination="pagination"
    :style="{ borderBottom: '1px solid #e8e8e8' }" bordered>
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'patientDetail'">
        <a-button class="adpatient" v-if="record.bedStatus === '占用' || record.bedStatus === '即将出院'"
          @click="openPatient(record)">
          {{ record.patientName }}
        </a-button>
        <span class="check-in" v-else>待入住</span>
        <a-modal v-model:visible="pVisible" title="详情" @ok="pHandleOk">
          <template #footer>
            <a-button key="submit" type="primary" @click="pHandleOk">确定</a-button>
          </template>
          <p>姓名：{{ selectBed?.patientName }}</p>
          <p>性别：{{ selectBed?.patientGender }}</p>
          <p>年龄：{{ selectBed?.patientAge }}</p>
          <p>联系电话：{{ selectBed?.telephoneNumber }}</p>
          <p>住院号：{{ selectBed?.admissionNumber }}</p>
          <p>医生：{{ selectBed?.doctor }}</p>
          <p>诊断情况：{{ selectBed?.illness }}</p>
          <p>入院类型：{{ selectBed?.admissionType }}</p>
          <p v-if="selectBed?.admissionType === '预约入院'">预约方式：{{ selectBed?.bookType }}</p>
          <p>急诊：<span v-if="selectBed?.isEmergency === 1">是</span><span v-else>否</span></p>
          <p>重症：<span v-if="selectBed?.isAcute === 1">是</span><span v-else>否</span></p>
          <p>VIP：<span v-if="selectBed?.isVip === 1">是</span><span v-else>否</span></p>
        </a-modal>
      </template>
      <template v-if="column.key === 'operation'">
        <a-popconfirm v-if="record.bedStatus === '占用' || record.bedStatus === '即将出院'" title="是否确认释放此床位?" okText="是"
          cancelText="否" @confirm="bedRelease(record.bedId, record.patientId)">
          <a-button danger  :disabled="!hasPermission('床位分配')">
            <template #icon>
              <api-outlined />
            </template>
            释放床位
          </a-button>
        </a-popconfirm>
        <a-popconfirm v-else title="是否确认删除此床位?" okText="是" cancelText="否"
          @confirm="bedDelete(record.bedId, record.patientName)">
          <a-button danger  :disabled="!hasPermission('床位管理')">
            <template #icon>
              <delete-outlined />
            </template>
            删除床位
          </a-button>
        </a-popconfirm>
      </template>
      <template v-if="column.key === 'bd'">
        <a-button>查看</a-button>
      </template>
      <template v-if="column.key === 'be'">
        <a-button>查看</a-button>
      </template>
    </template>
  </a-table>
</template>
<script lang="ts" setup>
import { ref, inject, watch } from 'vue';
import { message, type TableColumnsType } from 'ant-design-vue';
import type { Bed } from "./types/Bed.ts";
import { bedDeleteRequest, getBedsData, bedReleaseRequest } from '@/api/bedView/bedData'
import { AxiosError } from 'axios';
import { useUserStore } from '@/store/userStore';

const userStore = useUserStore();

const hasPermission = (permission: string) => {
  return userStore.permissions.includes(permission);
};
// 从祖先组件注入 bedsData与handleSearch方法
const bedsData = inject('bedsData') as any;
const handleSearch: Function = inject('handleSearch') as Function


const columns: TableColumnsType = [
  { title: '病房号', width: 60, dataIndex: 'roomNumber', key: '1' },
  { title: '床位号', width: 60, dataIndex: 'bedNumber', key: '2', },
  { title: '床位状态', dataIndex: 'bedStatus', key: '3', width: 60 },
  { title: '病房类型', dataIndex: 'roomType', key: '4', width: 60 },
  { title: '病房性别', dataIndex: 'roomGender', key: '5', width: 60 },
  { title: '病房人数', dataIndex: 'bedCount', key: '6', width: 60 },
  { title: '床位类型', dataIndex: 'bedType', key: '7', width: 60 },
  { title: '患者信息', key: 'patientDetail', width: 60 },
  // { title: '床旁设备', key: 'bd', width: 60 },
  // { title: '床位扩展属性', key: 'bd', width: 60 },
  {
    title: '床位操作',
    key: 'operation',
    fixed: 'right',
    width: 60,
  },
];



const pagination = {
  current: parseInt(localStorage.getItem('listPage') || '1'),
  pageSize: parseInt(localStorage.getItem('pageSize') || '10'),
  showQuickJumper: true,
  showLessItems: true,
  showSizeChanger: true,
  total: ref<number>(bedsData.value.length),
  showTotal: () => {
    const pagesize = pagination.total.value < pagination.pageSize ? pagination.total.value : pagination.pageSize
    return `共 ${pagination.total.value} 个患者，每页${pagesize}个患者`
  },
  onChange: (page: number, pageSize: number) => {
    localStorage.setItem('listPage', page.toString());
    localStorage.setItem('pageSize', pageSize.toString());
    pagination.current = page;
    pagination.pageSize = pageSize;

  },
};

watch(bedsData, () => {
  pagination.total.value = bedsData.value.length
  if (bedsData.value.length < pagination.current * pagination.pageSize && parseInt(localStorage.getItem('listPage') || '1') !== 1) {
    const current = pagination.current - 1
    pagination.onChange(current, pagination.pageSize)
  }
})
const pVisible = ref<boolean>(false);
const selectBed = ref<Bed>()
const openPatient = (recordBed: Bed) => {
  pVisible.value = true
  selectBed.value = recordBed

}
const pHandleOk = () => {
  pVisible.value = false
}

const bedDelete = async (bedId: number, patientName: string | null) => {
  if (patientName !== null) message.warning('请先释放床位上的患者')
  else if (patientName === null || patientName === '') {
    console.log('删除id', bedId)
    let params = bedId;
    try {
      // 假设 bedDeleteRequest 是一个返回 Promise 的异步函数
      let deleteResult = await bedDeleteRequest(params);
      // 如果需要的话，处理 result
      console.log('床位删除成功', deleteResult);
      message.success('床位删除成功')
      //删除成功后再一次进行查询请求刷新页面
      await handleSearch()
    } catch (error: any) {
      // 捕获并处理异步操作中的错误
      message.warning('删除床位时发生错误');
      // 可以根据错误类型或内容进行更详细的错误处理
      console.log('删除床位错误：', error.data.msg)
      // 例如，可以设置错误处理的状态，或者给用户展示错误信息
    }
  }
}

const bedRelease = async (bedId: number, patientId: number) => {
  let params = {
    bedId: bedId,
    patientId: patientId
  }
  await bedReleaseRequest(params)
  message.success('床位释放成功')
  await handleSearch()
}
</script>

<style scoped lang="scss">
.campus {
  display: flex;
}

.edit,
.delete {
  line-height: 14px;
  width: 40px;
  font-size: 11px;
  text-align: center;
  padding: 2px;
}

.delete {
  color: brown;
}

.check-in {
  margin-left: 14px;
}

.adpatient {
  background-color: #c9e6fe;
}
</style>
