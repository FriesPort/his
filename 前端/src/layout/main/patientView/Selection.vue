<template>
  <div class="select">
    <div class="selectmain">
      <div>
        <span>院区</span>
        <a-select v-model:value="selectedCampusName" @change="handleChange" style="width: 120px;" >
          <a-select-option v-for="(campusItem, index) in campusData" :key="campusItem.campusId"
            :value="campusItem.campusName">
            <span >{{ campusItem.campusName }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>病区</span>
        <a-select v-model:value="selectedOfficeName" style="width: 120px" >
          <a-select-option v-for="office in filteredOfficeList" :key="office.officeId" :value="office.officeName">
            <span >{{ office.officeName }}</span>
          </a-select-option>
        </a-select>
        <a-select v-model:value="selectedWardName" style="width: 80px" >
          <a-select-option v-for="ward in filteredWardList" :key="ward.wardId" :value="ward.wardName">
            <span >{{ ward.wardName }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>姓名</span>
        <a-input v-model:value="patientName" placeholder="请输入" style="width: 120px;" ></a-input>
      </div>
      <div>
        <span>患者性别</span>
        <a-select v-model:value="patientGender" style="width: 90px" >
          <a-select-option v-for="patientGender in patientGenderList" :key="patientGender" :value="patientGender">
            <span >{{ patientGender }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>入住状态</span>
        <a-select v-model:value="patientStatus" style="width: 120px" >
          <a-select-option v-for="patientStatus in patientStatusList" :key="patientStatus" :value="patientStatus">
            <span >{{ patientStatus }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>入院类型</span>
        <a-select v-model:value="admissionType" style="width: 120px" >
          <a-select-option v-for="ADType in admissionTypeList" :key="ADType" :value="ADType">
            <span >{{ ADType }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div v-if="admissionType === '预约入院'">
        <span>预约类型</span>
        <a-select v-model:value="bookType" style="width: 120px" >
          <a-select-option v-for="bookType in bookTypeList" :key="bookType" :value="bookType">
            <span >{{ bookType }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>病房类型</span>
        <a-select v-model:value="wardType" style="width: 120px" >
          <a-select-option v-for="wardType in wardTypeList" :key="wardType" :value="wardType">
            <span >{{ wardType }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>病房人数</span>
        <a-select v-model:value="wardPeopleCount" style="width: 90px" >
          <a-select-option v-for="wardPC in wardPeopleCountList" :key="wardPC" :value="wardPC">
            <span >{{ wardPC }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>病房性别</span>
        <a-select v-model:value="wardSex" style="width: 90px" >
          <a-select-option v-for="wardSex in wardSexList" :key="wardSex" :value="wardSex">
            <span >{{ wardSex }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>等待时间</span>
        <a-select v-model:value="waitingTime" style="width: 90px" >
          <a-select-option v-for="waitingTime in waitingTimeList" :key="waitingTime" :value="waitingTime">
            <span ><i v-if="waitingTime !== '全部'">>=</i>{{ waitingTime }}<i
                v-if="waitingTime !== '全部'">天</i></span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>显示方式</span>
        <a-select v-model:value="viewMode" style="width: 120px" >
          <a-select-option v-for="view in viewModeData" :key="view" :value="view">
            <span >{{ view }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <a-button type="primary" shape="circle" @click="handleSearch">
          <template #icon>
            <SearchOutlined />
          </template>
        </a-button>
      </div>
      <!-- <div class="statistics">全院共200床，有患者床位120，其中已入院100，待出院20；无患者床位80，已留床10，空闲70</div> -->
    </div>
  </div>
  <div class="other-info">
    <div class="campus">
      <h2>{{ selectedCampusName }}</h2>
      <h2>{{ selectedWardName }}</h2>
    </div>
    <div class="other-info-right">
      <div class="card-view" v-if="viewMode === '卡片模式'">
        <i class="severeIllness">重</i>重症患者
        <span class="iconfont icon-jizhen">急诊患者</span>
        <div class="bed-type-color1"></div><span>已入院</span>
        <div class="bed-type-color2"></div><span>待入院</span>
        <div class="bed-type-color3"></div><span>已出院</span>
      </div>
      <a-button type="primary" @click="patientAdd"  :disabled="!hasPermission('患者管理')">
        <template #icon>
          <plus-outlined />
        </template>
        新增患者
      </a-button>
      <a-modal v-model:visible="bedAddVisible" title="新增患者" @ok="patientAddOk" @cancel="patientAddCancel" ok-text="增加"
        cancel-text="取消" :afterClose="patientAddCancel" width="520px">
        <a-form>
          <a-row :gutter="16">
            <a-col :span="11">
              <a-form-item label="科室：" style="width: 200px">
                <a-select v-model:value="selectedOfficeName0">
                  <a-select-option v-for="office in filteredOfficeList0" :key="office.officeId"
                    :value="office.officeName">
                    <span>{{ office.officeName }}</span>
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="11" :offset="2">
              <a-form-item label="病区：" style="width: 200px">
                <a-select v-model:value="selectedWardName0">
                  <a-select-option v-for="ward in filteredWardList0" :key="ward.wardId" :value="ward.wardName">
                    <span>{{ ward.wardName }}</span>
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="11">
              <a-form-item label="姓名：" style="width: 200px;">
                <a-input v-model:value="addPatientName" placeholder="请输入"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="11" :offset="2">
              <a-form-item label="性别：" style="width: 200px">
                <a-select v-model:value="addPatientGender">
                  <a-select-option v-for="addPatientGender in addPatientGenderList" :key="addPatientGender"
                    :value="addPatientGender">
                    <span>{{ addPatientGender }}</span>
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="11">
              <a-form-item label="年龄：" style="width: 200px;">
                <a-input v-model:value="addPatientAge" placeholder="请输入数字"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="11" :offset="2">
              <a-form-item label="联系电话：" style="width: 200px;">
                <a-input v-model:value="addTelephoneNumber" placeholder="请输入"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="11">
              <a-form-item label="医生：" style="width: 200px;">
                <a-input v-model:value="addDoctor" placeholder="请输入"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="11" :offset="2">
              <a-form-item label="诊断情况：" style="width: 200px;">
                <a-input v-model:value="addIllness" placeholder="请输入"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="11">
              <a-form-item label="入院状态：">
                <span>待入院</span>
              </a-form-item>
            </a-col>
            <a-col :span="11" :offset="2">
              <a-form-item label="病房性别要求：">
                <a-select v-model:value="addRoomGenderRequirement" style="width: 104px">
                  <a-select-option v-for="addRoomGenderRequirement in addRoomGenderRequirements"
                    :key="addRoomGenderRequirement" :value="addRoomGenderRequirement">
                    <span>{{ addRoomGenderRequirement }}</span>
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="11">
              <a-form-item label="病房人数要求：">
                <a-select v-model:value="addRoomNumberRequirement" style="width: 104px">
                  <a-select-option v-for="addRoomNumberRequirement in addRoomNumberRequirements"
                    :key="addRoomNumberRequirement" :value="addRoomNumberRequirement">
                    <span>{{ addRoomNumberRequirement }}</span>
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="11" :offset="2">
              <a-form-item label="病房类型要求：">
                <a-select v-model:value="addRoomTypeRequirement" style="width: 104px">
                  <a-select-option v-for="addRoomTypeRequirement in addRoomTypeRequirements"
                    :key="addRoomTypeRequirement" :value="addRoomTypeRequirement">
                    <span>{{ addRoomTypeRequirement }}</span>
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="11">
              <a-form-item label="入院类型：">
                <a-select v-model:value="addAdmissionType" style="width: 132px">
                  <a-select-option v-for="addAdmissionType in addAdmissionTypes" :key="addAdmissionType"
                    :value="addAdmissionType">
                    <span>{{ addAdmissionType }}</span>
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="11" :offset="2">
              <a-form-item v-if="addAdmissionType === '预约入院'" label="预约方式：">
                <a-select v-model:value="addBookType" style="width: 132px">
                  <a-select-option v-for="addBookType in addBookTypes" :key="addBookType" :value="addBookType">
                    <span>{{ addBookType }}</span>
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="11">
              <a-form-item label="是否急诊：">
                <a-radio-group v-model:value="addEmergencyValue">
                  <a-radio :value="1">是</a-radio>
                  <a-radio :value="0">否</a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
            <a-col :span="11" :offset="2">
              <a-form-item label="是否重症：">
                <a-radio-group v-model:value="addCuteValue">
                  <a-radio :value="1">是</a-radio>
                  <a-radio :value="0">否</a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="11">
              <a-form-item label="是否VIP：">
                <a-radio-group v-model:value="addVIPValue">
                  <a-radio :value="1">是</a-radio>
                  <a-radio :value="0">否</a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-modal>
    </div>
  </div>
  <keep-alive>
    <component :is="currentComponent" />
  </keep-alive>

</template>
<script lang="ts" setup>
import { computed, ref, watch, shallowRef, reactive, provide, onMounted } from 'vue';
import List from "@/layout/main/patientView/List.vue";
import Card from "@/layout/main/patientView/Card.vue";
import type { Patient } from './types/patientViewTypes';
import type { Ward, Office, Campus } from "@/layout/main/bedview/types/Bed.ts";
import { getPatientsData, patientAddRequest } from '@/api/patientView/patientData'
import axios from 'axios';
import { getWardsData } from '@/api/bedView/bedData';
import { message } from 'ant-design-vue';
// import dayjs, { Dayjs } from 'dayjs';
import { useUserStore } from '@/store/userStore';

const userStore = useUserStore();

const hasPermission = (permission: string) => {
  return userStore.permissions.includes(permission);
};

const recordCampusId = ref<number>()
const recordOfficeId = ref<number>()
const recordWardId = ref<number>()
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
    recordCampusId.value = selectedCampus.campusId;
  }
};

// 基于 selectedCampusName 过滤出的 officeList
const filteredOfficeList = ref<Office[]>([]);
const filteredOfficeList0 = ref<Office[]>([]);

// 监听 selectedCampusName 的变化，更新 filteredOfficeList
watch(selectedCampusName, (newCampusName) => {
  // 找到匹配 selectedCampusName 的 campus 对象
  const campus = campusData.find(c => c.campusName === newCampusName);
  // 更新 filteredOfficeList 为该 campus 对象中的 offices 数组
  if (campus) {
    // 创建一个 '全部' 选项，确保它具有与数组中其他元素相同的结构
    let allOption: Office = {
      officeId: -1, // 假设 -1 是一个无效的 officeId，用来表示 '全部'
      officeName: '全部',
      wards: [{
        wardId: -1,
        wardName: '全部'
      }]
    };
    // 将 '全部' 选项插入到 filteredOfficeList 的开头
    filteredOfficeList.value = [allOption, ...campus.offices];
    filteredOfficeList0.value = [...campus.offices];
    selectedOfficeName.value = allOption.officeName; // 设置默认选中的 officeName 为 '全部'
    selectedOfficeName0.value = filteredOfficeList0.value[0].officeName;
  } else {
    filteredOfficeList.value = []; // 如果没有匹配的 campus，重置 filteredOfficeList 为空数组
  }
});
const selectedOfficeName = ref('');
const selectedOfficeName0 = ref('');

// 基于 selectedOfficeName 过滤出的 wardList
const filteredWardList = ref<Ward[]>([]);
const filteredWardList0 = ref<Ward[]>([]);
// 监听 selectedOfficeName 的变化，更新 filteredWardList
watch(selectedOfficeName, (newOfficeName) => {
  // 找到匹配 selectedOfficeName 的 office 对象
  const office = filteredOfficeList.value.find(c => c.officeName === newOfficeName);
  recordOfficeId.value = office?.officeId; //记录当前officeId，以便后面请求数据时传递
  // 更新 filteredWardList 为该 office 对象中的 wards 数组
  if (office) {
    let allOption: Ward = {
      wardId: -1, // 假设 -1 是一个无效的 officeId，用来表示 '全部'
      wardName: '全部'
    };
    filteredWardList.value = [allOption, ...office.wards];
    selectedWardName.value = filteredWardList.value[0].wardName; // 设置默认选中的 wardName
  } else {
    filteredOfficeList.value = []; // 如果没有匹配的 office，重置 filteredWardList 为空数组
  }
});
const recordOfficeId0 = ref<number>()
watch(selectedOfficeName0, (newOfficeName) => {
  // 找到匹配 selectedOfficeName 的 office 对象
  const office = filteredOfficeList.value.find(c => c.officeName === newOfficeName);
  recordOfficeId0.value = office?.officeId; //记录当前officeId，以便后面请求数据时传递
  // 更新 filteredWardList 为该 office 对象中的 wards 数组
  if (office) {
    filteredWardList0.value = [...office.wards];
    selectedWardName0.value = filteredWardList0.value[0].wardName; // 设置默认选中的 wardName
  } else {
    filteredOfficeList.value = []; // 如果没有匹配的 office，重置 filteredWardList 为空数组
  }
});
const selectedWardName = ref('');
const selectedWardName0 = ref('');
// 监听 selectedOfficeName 的变化，更新 recordWardId
watch(selectedWardName, (newWardName) => {
  // 找到匹配 selectedWardName 的 ward 对象
  const ward = filteredWardList.value.find(c => c.wardName === newWardName);
  recordWardId.value = ward?.wardId //记录当前wardId，以便后面请求数据时传递
})
const recordWardId0 = ref<number>()
watch(selectedWardName0, async (newWardName) => {
  // 找到匹配 selectedWardName 的 ward 对象
  const ward = filteredWardList0.value.find(c => c.wardName === newWardName);
  recordWardId0.value = ward?.wardId //记录当前wardId，以便后面请求数据时传递

})

const patientName = ref('')
const patientGenderList = ['全部', '男', '女'];
const patientStatusList = ['全部', '已入院', '待入院', '已出院'];
const patientStatus = ref(patientStatusList[0])
const admissionTypeList = ['全部', '急诊入院', '预约入院', '手术入院'];
const bookTypeList = ['全部', '线上预约', '线下预约']
const bookType = ref(bookTypeList[0])
const wardTypeList = ['全部', '普通病房', '隔离病房', 'ICU'];
const wardPeopleCountList = ['全部', '1', '2', '3', '4', '5', '6', '7', '8'];
const wardSexList = ['全部', '男性病房', '女性病房', '混合病房'];
const waitingTimeList = ['全部', 1, 2, 3, 4, 5, 6, 7];
const patientGender = ref(patientGenderList[0]);
const admissionType = ref(admissionTypeList[0]);
const wardType = ref(wardTypeList[0]);
const wardPeopleCount = ref(wardPeopleCountList[0]);
const wardSex = ref(wardSexList[0]);
const waitingTime = ref(waitingTimeList[0]);

const VIEW_MODE_KEY = 'viewMode';
const viewModeData = ['列表模式', '卡片模式'];
const storedViewMode = localStorage.getItem(VIEW_MODE_KEY); // 获取存储的视图模式
const defaultViewMode = storedViewMode || viewModeData[0]; // 如果有存储的模式，则使用该模式；否则默认为第一个模式
const viewMode = ref(defaultViewMode);
const currentComponent = shallowRef(defaultViewMode === viewModeData[0] ? List : Card);

watch(viewMode, (newValue) => {
  if (newValue === viewModeData[0]) {
    currentComponent.value = List;
  } else if (newValue === viewModeData[1]) {
    currentComponent.value = Card;
  }
  // 更新本地存储中的视图模式
  localStorage.setItem(VIEW_MODE_KEY, newValue);
});


// 患者信息
const patientsData = ref<Patient[]>([
  // {
  //   patientId: 1,
  //   name: '齐一',
  //   age: 20,
  //   gender: '男',
  //   phone: '9090909',
  //   bookType: '线下预约',
  //   status: '已入院',
  //   bedId: 10,
  //   roomNumber: '201房',
  //   bedNumber: '01床',
  //   roomType: '普通病房',
  //   roomGender: '混合病房',
  //   bedCount: 6,
  //   roomNumberRequirement: 3,
  //   roomGenderRequirement: '男性病房',
  //   roomTypeRequirement: '普通病房',
  //   waitDay: null,
  //   admissionType: '急诊入院',
  //   admissionTime: '2024/04/01',
  //   createTime: '2024/03/31',
  //   dischargeTime: null,
  //   admissionNumber: '23456A',
  //   doctor: '老陈',
  //   illness: '骨折',
  //   isEmergency: 1,
  //   isAcute: 1,
  //   isVip: 1
  // },
  // {
  //   patientId: 2,
  //   name: '齐二',
  //   age: 19,
  //   gender: '男',
  //   phone: '90909091',
  //   bookType: '线上预约',
  //   status: '已入院',
  //   bedId: 11,
  //   roomNumber: '201房',
  //   bedNumber: '02床',
  //   roomType: '普通病房',
  //   roomGender: '混合病房',
  //   bedCount: 6,
  //   roomNumberRequirement: 3,
  //   roomGenderRequirement: '男性病房',
  //   roomTypeRequirement: '普通病房',
  //   waitDay: null,
  //   admissionType: '预约入院',
  //   admissionTime: '2024/04/01',
  //   createTime: '2024/03/31',
  //   dischargeTime: null,
  //   admissionNumber: '23456B',
  //   doctor: '老赖',
  //   illness: '骨折',
  //   isEmergency: 0,
  //   isAcute: 0,
  //   isVip: 0
  // },
  // {
  //   patientId: 3,
  //   name: '齐三',
  //   age: 18,
  //   gender: '女',
  //   phone: '90909092',
  //   bookType: '线上预约',
  //   status: '待入院',
  //   bedId: null,
  //   roomNumber: null,
  //   bedNumber: null,
  //   roomType: '普通病房',
  //   roomGender: '混合病房',
  //   bedCount: 6,
  //   roomNumberRequirement: 3,
  //   roomGenderRequirement: '女性病房',
  //   roomTypeRequirement: '普通病房',
  //   admissionType: '预约入院',
  //   waitDay: 2,
  //   admissionTime: null,
  //   createTime: '2024/04/01',
  //   dischargeTime: null,
  //   admissionNumber: '',
  //   doctor: '老林',
  //   illness: '公主病',
  //   isEmergency: 0,
  //   isAcute: 0,
  //   isVip: 1
  // },
  // {
  //   patientId: 4,
  //   name: '齐四',
  //   age: 28,
  //   gender: '男',
  //   phone: '90909093',
  //   bookType: '线下预约',
  //   status: '已入院',
  //   bedId: 15,
  //   roomNumber: '207房',
  //   bedNumber: '01床',
  //   roomType: '普通病房',
  //   roomGender: '混合病房',
  //   bedCount: 6,
  //   roomNumberRequirement: 1,
  //   roomGenderRequirement: '男性病房',
  //   roomTypeRequirement: '隔离病房',
  //   waitDay: null,
  //   admissionType: '急诊入院',
  //   admissionTime: '2024/04/01',
  //   createTime: '2024/03/31',
  //   dischargeTime: null,
  //   admissionNumber: '23456D',
  //   doctor: '老程',
  //   illness: '感染性疾病',
  //   isEmergency: 1,
  //   isAcute: 1,
  //   isVip: 0
  // }
]
)
//提供patientsData给后代组件
provide('patientsData', patientsData)
onMounted(async () => {
  // let { data } = await axios.get('http://localhost:3000/beds');
  let { data } = await getWardsData();
  campusData.splice(0, campusData.length, ...data); // 替换现有数据
  selectedCampusName.value = campusData[0].campusName;
  recordCampusId.value = campusData[0].campusId;
  recordOfficeId.value = campusData[0].offices[0].officeId;
  recordWardId.value = campusData[0].offices[0].wards[0].wardId;
  let params = {
    campusId: recordCampusId.value === -1 ? null : recordCampusId.value,
    officeId: null,
    wardId: null,
    name: patientName.value === '' ? null : patientName.value,
    gender: patientGender.value === '全部' ? null : patientGender.value,
    status: patientStatus.value === '全部' ? null : patientStatus.value,
    admissionType: admissionType.value === '全部' ? null : admissionType.value,
    bookType: bookType.value === '全部' ? null : bookType.value,
    roomTypeRequirement: wardType.value === '全部' ? null : wardType.value,
    roomGenderRequirement: wardSex.value === '全部' ? null : wardSex.value,
    roomNumberRequirement: wardPeopleCount.value === '全部' ? null : wardPeopleCount.value,
    waitDay: waitingTime.value === '全部' ? null : waitingTime.value
  }
  console.log('开始', params)
  let result = await getPatientsData(params);
  patientsData.value = result.data;
})

//查询按钮
const handleSearch = async () => {
  let params = {
    // roomWardRequirement: recordWardId.value,
    campusId: recordCampusId.value === -1 ? null : recordCampusId.value,
    officeId: recordOfficeId.value === -1 ? null : recordOfficeId.value,
    wardId: recordWardId.value === -1 ? null : recordWardId.value,
    name: patientName.value === '' ? null : patientName.value,
    gender: patientGender.value === '全部' ? null : patientGender.value,
    status: patientStatus.value === '全部' ? null : patientStatus.value,
    admissionType: admissionType.value === '全部' ? null : admissionType.value,
    bookType: bookType.value === '全部' ? null : bookType.value,
    roomTypeRequirement: wardType.value === '全部' ? null : wardType.value,
    roomGenderRequirement: wardSex.value === '全部' ? null : wardSex.value,
    roomNumberRequirement: wardPeopleCount.value === '全部' ? null : wardPeopleCount.value,
    waitDay: waitingTime.value === '全部' ? null : waitingTime.value
  }
  console.log('患者查询', params)
  let result = await getPatientsData(params);
  patientsData.value = result.data;
}
// 提供 handleSearch 给所有后代组件
provide('handleSearch', handleSearch)


//新增患者
const bedAddVisible = ref<boolean>(false)
const addPatientStatusList = ['已入院', '待入院']
const addPatientStatus = ref(addPatientStatusList[0])
const addAdmissionTypes = ['预约入院', '手术入院', '急诊入院']
const addAdmissionType = ref(addAdmissionTypes[0])
const addBookTypes = ['线上预约', '线下预约']
const addBookType = ref(addBookTypes[0])
const addAdmissionNumber = ref('')
const addRoomTypeRequirements = ['普通病房', '隔离病房', 'ICU']
const addRoomTypeRequirement = ref(addRoomTypeRequirements[0])
const addRoomGenderRequirements = ['男性病房', '女性病房', '混合病房']
const addRoomGenderRequirement = ref(addRoomGenderRequirements[0])
const addRoomNumberRequirements = ['全部', 1, 2, 3, 4, 5, 6, 7]
const addRoomNumberRequirement = ref(addRoomNumberRequirements[0])
const addEmergencyValue = ref<number>(0)
const addCuteValue = ref<number>(0)
const addVIPValue = ref<number>(0)
const addPatientName = ref('')
const addPatientGenderList = ['男', '女']
const addPatientGender = ref(addPatientGenderList[0])
const addPatientAge = ref<number>()
const addTelephoneNumber = ref('')
const addDoctor = ref('')
const addIllness = ref('')
const patientAdd = () => {
  bedAddVisible.value = true
}
//确认新增
const patientAddOk = async () => {
  const phoneNumberRegex = /^\d{11}$/;
  if (addPatientName.value === '') {
    message.warning('名字为空')
    return
  }
  if (addPatientAge.value === undefined) {
    message.warning('请输入年龄')
    return
  }
  if (addPatientAge.value < 0) {
    message.warning('请输入有效年龄')
    return
  }
  if (!phoneNumberRegex.test(addTelephoneNumber.value)) {
    message.warning('请输入有效电话')
    return
  }
  if (addDoctor.value === '') {
    message.warning('请输入主治医生')
    return
  }
  if (addIllness.value === '') {
    message.warning('请输入诊断情况')
    return
  }
  if (addPatientStatus.value === '已入住') {
    if (addAdmissionNumber.value === '') {
      message.warning('请输入住院号')
      return
    }
  }
  else {
    try {
      let params = {
        campusId: recordCampusId.value,
        officeId: recordOfficeId0.value,
        wardId: recordWardId0.value,
        name: addPatientName.value,
        age: addPatientAge.value,
        gender: addPatientGender.value,
        phone: addTelephoneNumber.value,
        bookType: addBookType.value,
        roomNumberRequirement: addRoomNumberRequirement.value === '全部' ? null : addRoomNumberRequirement.value,
        roomGenderRequirement: addRoomGenderRequirement.value,
        roomTypeRequirement: addRoomTypeRequirement.value,
        // roomWardRequirement: recordWardId.value,
        // waitDay: addWaitingDay.value,
        admissionType: addAdmissionType.value,
        // admissionTime: addAdmissionTimeValue.value,
        // admissionNumber: addAdmissionNumber.value,
        doctor: addDoctor.value,
        illness: addIllness.value,
        isEmergency: addEmergencyValue.value,
        isAcute: addCuteValue.value,
        isVip: addVIPValue.value
      }
      let result = await patientAddRequest(params)
      message.warning('新增患者成功')
      //增加成功之后再发一个查询请求更新页面
      await handleSearch()
    } catch (error: any) {
      // 错误处理
      console.log('请求失败:', error);
    };
  }
  bedAddVisible.value = false
  addPatientName.value = ''
  addPatientAge.value = undefined
  addTelephoneNumber.value = ''
  addDoctor.value = ''
  addIllness.value = ''
  addAdmissionNumber.value = ''
}
const patientAddCancel = () => {
  bedAddVisible.value = false
}
</script>

<style scoped lang="scss">
.select {
  display: flex;


  .selectmain {
    display: flex;
    flex: 1;
    flex-wrap: wrap;
    align-items: center;
    margin: 6px 6px 0;
    font-size: 14px;
    z-index: 2;
  }

  .statistics {
    margin-left: auto;
  }
}

.dropdown {
  font-size: 12px;
  margin: 0;
}

.selectmain>div {
  margin: 4px;
}

.selectmain>div>span {
  margin-right: 4px;
}

.other-info {
  display: flex;
  justify-content: space-between;
  width: 100%;

  .campus {
    display: flex;

    h2 {
      margin-left: 20px;
      font-weight: 700;
    }
  }

  .other-info-right {
    display: flex;
    justify-content: center;
    align-items: center;

    .card-view {
      display: flex;
      justify-content: center;
      align-items: center;

      div {
        width: 20px;
        height: 20px;
        margin-left: 10px;
        border: .6px solid rgba($color: #a67cea, $alpha: .5);
      }

      span {
        margin: 0 12px 0 6px;
      }
    }
  }

  .severeIllness {
    margin-right: 2px;
    font-weight: 700;
    font-size: 16px;
    color: #fd6700;
  }

  .bed-type-color1 {
    background-color: #c9e6fe;
  }

  .bed-type-color2 {
    background-color: #e4e4e4;
  }

  .bed-type-color3 {
    background-color: #feebfe;
  }

  button {
    margin: 4px 12px;
  }

  button {
    margin: 4px 12px;
  }
}

.extendedAttributes {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.is-available {
  margin-left: 30px;
}

.is-available1,
.is-available2 {
  margin-right: 36px;
}
</style>
