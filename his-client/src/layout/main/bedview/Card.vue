<template>
  <div class="ward-container">
    <div class="beds">
      <div class="ward-card" v-for="(bed, bedIndex) in displayedbeds" :key="bedIndex" @click="bedDetail(bed)">
        <div class="bed-info">
          <!-- 楼层数、病房号、病房类型 -->
          <div class="title" :style="{ backgroundColor: getTitleColorStyle(bed.roomGender) }">
            <span class="titleLeft">{{ bed.roomNumber }}{{ bed.bedNumber }}</span>
            <span class="wardType1" v-if="bed.roomType === '隔离病房'">隔</span>
            <span class="wardType2" v-if="bed.roomType === '普通病房'">常</span>
            <span class="wardType3" v-if="bed.roomType === 'ICU'">ICU</span>
          </div>
          <!-- 床位信息 -->
          <div :class="{ 'free': bed.bedStatus !== '占用' && bed.bedStatus !== '即将出院' }">
          </div>
          <div v-if="bed.bedStatus === '占用' || bed.bedStatus === '即将出院'" class="bed">
            <div class="base">
              <span class="name">{{ bed.patientName }}</span>
              <span class="sex">{{ bed.patientGender }}</span>
              <span class="age">{{ bed.patientAge }}</span>
            </div>
            <div class="admission-number">住院号：{{ bed.admissionNumber }}</div>
            <div class="diagnosis">诊断：{{ bed.illness }}</div>
            <div class="admission-time">入院：{{ bed.admissionTime }}</div>
            <div class="doctor">医生：{{ bed.doctor }}</div>
            <div class="bed-status" v-if="bed.bedStatus === '即将出院'"></div>
            <span class="status-text" v-if="bed.bedStatus === '即将出院'">即将出院</span>
            <div class="bed-vip" v-if="bed.isVip === 1"></div>
            <span class="vip-text" v-if="bed.isVip === 1">VIP</span>
            <div class="bed-icon">
              <span class="iconfont icon-jizhen" v-if="bed.isEmergency === 1"></span>
              <span v-if="bed.isAcute === 1" class="severeIllness">重</span>
            </div>
          </div>
        </div>
      </div>
      <div class="pagination">
        <a-pagination v-model:current="currentPage" :page-size="currentpageSize" :page-size-options="pageSizeOptions"
          :total="totalBeds" show-less-items show-quick-jumper show-size-changer :show-total="showTotal"
          @change="onChange" />
      </div>
    </div>
  </div>
  <a-modal v-model:visible="visible" :title="title" @ok="handleOk" :afterClose="handleOk" width="300px">
    <template #footer>
      <a-button key="submit" type="primary" @click="handleOk">确定</a-button>
    </template>
    <a-button type="text" @click="patientDetail">
      <template #icon>
        <message-outlined />
      </template>
      患者详情
    </a-button>
    <!-- <a-button type="text">床旁设备</a-button>
    <a-button type="text">床位扩展属性</a-button> -->
    <a-popconfirm v-if="recordBed?.bedStatus === '占用' || recordBed?.bedStatus === '即将出院'" title="是否确认释放此床位?" okText="是"
      cancelText="否" @confirm="bedRelease(recordBed?.bedId, recordBed?.patientId as number)">
      <a-button danger  :disabled="!hasPermission('床位分配')">
        <template #icon>
          <api-outlined />
        </template>
        释放床位
      </a-button>
    </a-popconfirm>
    <a-popconfirm v-else title="是否确认删除此床位?"  :disabled="!hasPermission('床位管理')" okText="是" cancelText="否" @confirm="bedDelete">
      <a-button danger>
        <template #icon>
          <delete-outlined />
        </template>
        删除床位
      </a-button>
    </a-popconfirm>
  </a-modal>
  <a-modal v-model:visible="pVisible" title="患者详情" @ok="pHandleOk" :afterClose="pHandleOk">
    <template #footer>
      <a-button key="submit" type="primary" @click="pHandleOk">确定</a-button>
    </template>
    <p>姓名：{{ recordBed?.patientName }}</p>
    <p>性别：{{ recordBed?.patientGender }}</p>
    <p>年龄：{{ recordBed?.patientAge }}</p>
    <p>联系电话：{{ recordBed?.telephoneNumber }}</p>
    <p>住院号：{{ recordBed?.admissionNumber }}</p>
    <p>医生：{{ recordBed?.doctor }}</p>
    <p>诊断情况：{{ recordBed?.illness }}</p>
    <p>入院类型：{{ recordBed?.admissionType }}</p>
    <p v-if="recordBed?.admissionType === '预约入院'">预约方式：{{ recordBed?.bookType }}</p>
    <p>重症：<span v-if="recordBed?.isAcute === 1">是</span><span v-else>否</span></p>
    <p>VIP：<span v-if="recordBed?.isVip === 1">是</span><span v-else>否</span></p>
  </a-modal>
</template>

<script setup lang="ts">
import { reactive, ref, computed, watch, inject } from 'vue';
import type { Bed } from './types/Bed';
import { AxiosError } from 'axios';
import { message } from 'ant-design-vue';
import { bedDeleteRequest, bedReleaseRequest } from '@/api/bedView/bedData';
import { useUserStore } from '@/store/userStore';

const userStore = useUserStore();

const hasPermission = (permission: string) => {
  return userStore.permissions.includes(permission);
};
// 从祖先组件注入 bedsData和handleSearch方法
const bedsData = inject('bedsData') as any
const handleSearch: Function = inject('handleSearch') as Function
//床位详情方法
const visible = ref<boolean>(false)
const recordBed = ref<Bed>()
const title = ref('')
const bedDetail = (bed: Bed) => {
  visible.value = true
  recordBed.value = bed
  title.value = recordBed.value.roomNumber + recordBed.value.bedNumber
}

const pVisible = ref<boolean>(false);
const patientDetail = () => {
  pVisible.value = true
}
const pHandleOk = () => {
  pVisible.value = false
}
const handleOk = () => {
  visible.value = false
}
//床位删除
const bedDelete = async () => {
  if (recordBed.value?.patientName !== null) message.warning('请先释放床位上的患者')
  else if (recordBed.value?.patientName === null || recordBed.value?.patientName === '') {
    console.log('删除id', recordBed.value?.bedId)
    let params = recordBed.value?.bedId;
    try {
      // 假设 bedDeleteRequest 是一个返回 Promise 的异步函数
      let result = await bedDeleteRequest(params);
      // 如果需要的话，处理 result
      console.log('床位删除成功', result);
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
//床位释放
const bedRelease = async (bedId: number, patientId: number) => {
  let params = {
    bedId: bedId,
    patientId: patientId
  }
  await bedReleaseRequest(params)
  message.success('床位释放成功')
  await handleSearch()
}

// 计算属性，根据 bedType 返回颜色
const getTitleColorStyle = (roomGender: string) => {
  switch (roomGender) {
    case '男性病房':
      return '#c9e6fe';
    case '女性病房':
      return '#e4e4e4';
    default:
      return '#feebfe';
  }
};

// 计算床位总数
const totalBeds = ref<number>(bedsData.value.length);
watch(bedsData, () => {
  totalBeds.value = bedsData.value.length
})
// 计算每页显示的床位数据
const currentpageSize = ref(parseInt(localStorage.getItem('cardPageSize') || '30')); // 每页显示的床位数量
const currentPage = ref(parseInt(localStorage.getItem('cardPage') || '1')); // 当前页码
const pageSizeOptions = ['10', '20', '30', '40', '50'];
// 计算分页器的选项
const showTotal = () => {
  const pagesize = totalBeds.value < currentpageSize.value ? totalBeds.value : currentpageSize.value
  return `共 ${totalBeds.value} 个患者，每页${pagesize}个患者`
}
const onChange = (pageNumber: number, pageSize: number) => {
  localStorage.setItem('cardPage', pageNumber.toString());
  localStorage.setItem('cardPageSize', pageSize.toString());
  currentPage.value = pageNumber;
  currentpageSize.value = pageSize
};

watch(bedsData, () => {
  if (bedsData.value.length < currentPage.value * currentpageSize.value && parseInt(localStorage.getItem('cardPage') || '1') !== 1) {
    const current = currentPage.value - 1
    currentPage.value = current
  }
})
// 计算分页后的床位数据
const displayedbeds = computed(() => {
  const startIndex = (currentPage.value - 1) * currentpageSize.value;
  const endIndex = Math.min(startIndex + currentpageSize.value, totalBeds.value);
  const currentbeds: Bed[] = bedsData.value.slice(startIndex, endIndex);
  console.log('currentbeds', currentbeds)
  return currentbeds;
});


</script>

<style lang="scss" scoped>
.ward-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-content: flex-start;
  height: 88%;
  padding: 0 6px;
  position: relative;
  cursor: pointer;

  @media (min-width: 1730px) {
    width: 100%;
    /* 视口宽度大于等于1600时，盒子宽度补满剩余宽度 */
  }

  @media (max-width: 1729px) {
    width: 1450px;
    /* 视口宽度小于1600时，盒子宽度等于1320 */
  }
}

.pagination {
  display: flex;
  width: 100%;
  justify-content: flex-end;
  margin: 12px 200px;
}

.beds {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-content: flex-start;
  height: 100%;
  width: 100%;

  .ward-card {
    display: flex;
    flex-direction: column;
    /* 垂直方向上，内容将从上到下排列 */
    height: 160px;
    width: 136px;
    border: 1px solid #ccc;
    margin: 6px 6px 0 0;

    // @media (min-width: 1600px) {
    //   width: calc(100% / 13);
    //   /* 视口宽度大于等于1600时，盒子宽度补满剩余宽度 */
    // }

    // @media (min-height: 800px) {
    //   height: calc(100% / 4 - 20px);
    //   /* 视口宽度大于730时，盒子高度适当放大 */
    // }

    .bed-info {
      width: 100%;
      height: 100%;

      .title {
        display: flex;
        align-items: center;
        justify-content: space-between;
        width: 100%;
        height: 30px;
        font-weight: 700;
        font-size: 16px;
        background-color: #e1dffe;

        .titleLeft {
          margin-left: 4px;
        }

        .wardType1 {
          margin-right: 4px;
          color: #c00f0f;
        }

        .wardType2 {
          margin-right: 4px;
          color: #000;
        }

        .wardType3 {
          margin-right: 4px;
          color: #c00f0f;
        }
      }

      .free {
        width: 100%;
        height: calc(100% - 30px);
        overflow: hidden;
        border: 1px solid rgba($color: #8c5ef7, $alpha: .8);
        background: #e4e4e4;
        background-image: url(@/assets/logos.png);
        background-size: auto 100%;
        background-repeat: no-repeat;
        /* 防止背景图片重复 */

      }

      .bed {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        height: calc(100% - 30px);
        text-align: left;
        padding-left: 6px;
        padding-bottom: 16px;
        font-size: 12px;
        overflow: hidden;
        position: relative;

        .name {
          font-weight: 700;
          font-size: 14px;
        }

        .sex {
          font-size: 12px;
          margin: 0 6px;
          padding: 0 6px;
          border-left: 1px solid #000;
          border-right: 1px solid #000;
        }

        .bed-status {
          width: 0;
          height: 0;
          border-left: 52px solid transparent;
          /* 左侧边框，可调整大小 */
          border-bottom: 42px solid rgb(71, 109, 220);
          /* 底部边框，可调整大小和颜色 */
          position: absolute;
          bottom: 0;
          /* 使盒子位于父级盒子的底部 */
          right: 0;
          /* 使盒子位于父级盒子的右侧 */
          overflow: visible;
          /* 允许文字溢出盒子边界 */
          white-space: nowrap;
          /* 防止文字换行 */
        }

        .status-text {
          color: white;
          /* 文字颜色 */
          font-size: 10px;
          /* 文字大小 */
          position: absolute;
          bottom: 0;
          /* 使盒子位于父级盒子的底部 */
          right: 0;
          /* 使盒子位于父级盒子的右侧 */
        }

        .bed-vip {
          width: 0;
          height: 0;
          border-left: 34px solid transparent;
          /* 左侧边框，可调整大小 */
          border-top: 34px solid rgb(255, 128, 00);
          /* 底部边框，可调整大小和颜色 */
          position: absolute;
          top: 0;
          /* 使盒子位于父级盒子的底部 */
          right: 0;
          /* 使盒子位于父级盒子的右侧 */
          overflow: visible;
          /* 允许文字溢出盒子边界 */
          white-space: nowrap;
          /* 防止文字换行 */
        }

        .vip-text {
          color: white;
          /* 文字颜色 */
          font-size: 14px;
          /* 文字大小 */
          position: absolute;
          top: 0;
          /* 使盒子位于父级盒子的底部 */
          right: 0;
          /* 使盒子位于父级盒子的右侧 */
        }
      }

      .bed-icon {
        position: absolute;
        bottom: -6px;
        /* 使盒子位于父级盒子的底部 */
        left: 0;
        /* 使盒子位于父级盒子的右侧 */
        padding-left: 4px;
        margin-top: 6px;
        margin-bottom: 4px;

        .iconfont.icon-jizhen {
          margin-right: 8px;
        }

        .severeIllness {
          padding-bottom: 2px;
          margin-bottom: 6px;
          font-weight: 700;
          font-size: 13px;
          color: #fd6700;
        }
      }
    }
  }
}
</style>
