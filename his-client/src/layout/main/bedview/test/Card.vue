<template>
  <div class="ward-container">
    <div v-for="(ward, index) in displayedWards" :key="index" class="ward-card">
      <div v-for="(bed, bedIndex) in ward.beds" :key="bedIndex" class="bed-info">
        <!-- 楼层数、病房号、病房类型 -->
        <div class="title">
          <span class="titleLeft">{{ bed.roomName }}{{ bed.bedNumber }}床</span>
          <span class="wardType">{{ bed.roomType }}</span>
        </div>
        <!-- 床位信息 -->
        <div :class="{ 'free': bedStatus === false }">
          <div v-if="bedStatus" class="bed">
            <div class="base">
              <span class="name">张三</span>
              <span class="sex">男</span>
              <span class="age">30</span>
            </div>
            <div class="admission-number">住院号：</div>
            <div class="diagnosis">诊断：</div>
            <div class="admission-time">入院：</div>
            <div class="doctor">医生：</div>
            <div>{{ bed.status }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="pagination">
      <a-pagination v-model:current="currentPage" :page-size="pageSize" :page-size-options="pageSizeOptions"
        :total="totalBeds" show-less-items show-quick-jumper :show-total="showTotal" @change="onChange" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, computed } from 'vue';


interface Patient {
  patientId: number,
  patientName: string,
  patientGender: string,
  waitDay: number,
  admissionType: string,
  bookType: string,
  telephoneNumber: string,
  roomNumberRequirement: number,
  roomGenderRequirement: string,
  roomTypeRequirement: string,
  patientStatus: string,
  admissionNumber: string,
  diagnosisSituation: string,
  admissionTime: string,
  dotor: string,
  emergencyAdmission: boolean,
  severeIllness: boolean,
}
interface Bed {
  bedId: number,
  areaName: string,
  departmentName: string,
  wardName: string,
  roomName: string,
  bedNumber: string,
  bedType: string,
  roomType: string,
  roomNumber: number,
  roomGender: string,
  bedEquipment: string,
  status: string,
  patient: Patient | null
}

interface Ward {
  floor: number;
  wardNumber: string;
  wardType: string;
  beds: Bed[];
}

const statuses = ['在床', '已预约', '已留床', '预计入院', '即将出院'];
const roomGender = ['男', '女', '混合病房'];
const roomType = ['常', '隔']
const wards: Ward[] = reactive([
  {
    floor: 1,
    wardNumber: '101',
    wardType: '隔',
    beds: generateBeds()
  },
  {
    floor: 2,
    wardNumber: '201',
    wardType: '隔',
    beds: generateBeds()
  },
  {
    floor: 2,
    wardNumber: '202',
    wardType: '隔',
    beds: generateBeds()
  }
  // 添加更多病房数据
]);

// 添加更多病房数据
for (let i = 3; i <= 100; i++) {
  const currentFloor = Math.ceil(i / 2); // 楼层数
  wards.push({
    floor: currentFloor, // 楼层数
    wardNumber: `${currentFloor}${i < 10 ? '0' + i : i}`, // 病房号修改
    wardType: i % 2 === 0 ? '隔' : '单', // 病房类型，偶数为隔离病房，奇数为单人病房
    beds: generateBeds() // 生成随机床位
  });
}

// 定义随机生成的患者信息或者为null
function generateRandomPatient() {


  // 生成随机患者对象或者返回null
  if (Math.random() > 0.5) {
    return {
      "patientId": 1,
      "patientName": "张三",
      "patientGender": "男",
      "waitDay": 5,
      "admissionType": "门诊",
      "bookType": "网上预约",
      "telephoneNumber": "1111111",
      "roomNumberRequirement": 4,
      "roomGenderRequirement": "男",
      "roomTypeRequirement": "常规病房",
      "patientStatus": "待入院",
      "admissionNumber": "242424",
      "diagnosisSituation": "高血压",
      "admissionTime": "2024/04/11",
      "dotor": "老李",
      "emergencyAdmission": true,
      "severeIllness": true,
    };
  } else {
    return null;
  }
}
//生成床位
function generateBeds(): Bed[] {
  const bedCount = Math.floor(Math.random() * 5) + 1; // 随机生成1到5之间的整数
  const beds: Bed[] = [];
  for (let i = 1; i <= bedCount; i++) {
    beds.push({
      "bedId": i,
      "areaName": "温江院区",
      "departmentName": "内科",
      "wardName": "内一区",
      "roomName": "101房",
      "bedNumber": `0${i}`.slice(-2),
      "bedType": "正床",
      "roomType": roomType[Math.floor(Math.random() * roomType.length)],
      "roomNumber": 6,
      "roomGender": roomGender[Math.floor(Math.random() * roomGender.length)],
      "bedEquipment": "呼吸机",
      "status": statuses[Math.floor(Math.random() * statuses.length)],
      "patient": generateRandomPatient()
    });; // 格式化床位号
  }
  return beds;
}

const bedStatus = ref<boolean>(true)


// 计算总病房数量
const totalWards = ref<number>(wards.length);
// 计算床位总数
const totalBeds = ref<number>(0);
for (let i = 0; i < wards.length; i++) {
  totalBeds.value += wards[i].beds.length;
}
// 计算每页显示的床位数据
let pageSize = ref<number>(48); // 每页显示的床位数量
const currentPage = ref(parseInt(localStorage.getItem('cardPage') || '1')); // 当前页码
const pageSizeOptions = [48];
// 计算分页器的选项
const showTotal = () => `共 ${totalBeds.value} 个床位，每页${pageSize.value}个床位`;

// 计算分页后的床位数据
const displayedWards = computed(() => {
  const rows = Math.ceil(totalBeds.value / 12); // 每行固定12张卡片
  const cols = Math.min(12, Math.ceil(totalBeds.value / pageSize.value)); // 每行固定的卡片数量
  const totalPages = Math.ceil(totalBeds.value / pageSize.value); // 总页数
  const totalRows = Math.ceil(totalPages / 4); // 固定每页四行
  const maxCards = rows * cols; // 最大卡片数量
  const startIndex = (currentPage.value - 1) * pageSize.value;
  const endIndex = Math.min(startIndex + pageSize.value, totalBeds.value);
  const currentWards: Ward[] = [];
  let bedsCount = 0;
  for (let i = 0; i < wards.length; i++) {
    const ward = wards[i];
    for (let j = 0; j < ward.beds.length; j++) {
      if (bedsCount >= startIndex && bedsCount < endIndex) {
        currentWards.push({
          floor: ward.floor,
          wardNumber: ward.wardNumber,
          wardType: ward.wardType,
          beds: [ward.beds[j]]
        });
      }
      bedsCount++;
      if (bedsCount === endIndex) break;
    }
    if (bedsCount === endIndex) break;
  }
  return currentWards;
});
// 分页器改变事件处理函数
const onChange = (pageNumber: number) => {
  localStorage.setItem('cardPage', pageNumber.toString());
  currentPage.value = pageNumber;
};

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

  @media (min-width: 1600px) {
    width: 100%;
    /* 视口宽度大于等于1600时，盒子宽度补满剩余宽度 */
  }

  @media (max-width: 1599px) {
    width: 1320px;
    /* 视口宽度小于1600时，盒子宽度等于1320 */
  }
}

.pagination {
  display: flex;
  width: 100%;
  justify-content: flex-end;
  margin: 8px;
}

.ward-card {
  display: flex;
  flex-direction: column;
  /* 垂直方向上，内容将从上到下排列 */
  height: 140px;
  width: 102px;
  border: 1px solid #ccc;
  margin: 6px 6px 0 0;

  @media (min-width: 1600px) {
    width: calc(100% / 13);
    /* 视口宽度大于等于1600时，盒子宽度补满剩余宽度 */
  }

  @media (min-height: 800px) {
    height: calc(100% / 4 - 20px);
    /* 视口宽度大于730时，盒子高度适当放大 */
  }

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
      font-size: 14px;
      background-color: #e1dffe;

      .titleLeft {
        margin-left: 4px;
      }

      .wardType {
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
      display: grid;
      /* 使用 Grid 布局 */
      gap: 2px;
      /* 设置子元素之间的间距 */
      text-align: left;
      margin: 0 6px;
      font-size: 10px;
      overflow: hidden;

      .name {
        font-weight: 700;
        font-size: 12px;
      }

      .sex {
        font-size: 10px;
        margin: 0 4px;
        padding: 0 4px;
        border-left: 1px solid #000;
        border-right: 1px solid #000;
      }
    }
  }
}
</style>
