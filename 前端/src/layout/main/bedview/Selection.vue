<template>
  <div class="select">
    <div class="selectmain">
      <div>
        <span>院区</span>
        <a-select v-model:value="selectedCampusName" @change="handleChange" style="width: 120px;">
          <a-select-option v-for="(campusItem, index) in campusData" :key="campusItem.campusId"
            :value="campusItem.campusName">
            <span >{{ campusItem.campusName }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>病区</span>
        <a-select v-model:value="selectedOfficeName" style="width: 120px">
          <a-select-option v-for="office in filteredOfficeList" :key="office.officeId" :value="office.officeName">
            <span >{{ office.officeName }}</span>
          </a-select-option>
        </a-select>
        <a-select v-model:value="selectedWardName" style="width: 120px" >
          <a-select-option v-for="ward in filteredWardList" :key="ward.wardId" :value="ward.wardName">
            <span >{{ ward.wardName }}</span>
          </a-select-option>
        </a-select>
      </div>
      <div>
        <span>床位类型</span>
        <a-select v-model:value="bedType" style="width: 90px" >
          <a-select-option v-for="bedType in bedTypeList" :key="bedType" :value="bedType">
            <span >{{ bedType }}</span>
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
        <div class="bed-type-color1"></div><span>男性病房</span>
        <div class="bed-type-color2"></div><span>女性病房</span>
        <div class="bed-type-color3"></div><span>混合病房</span>
      </div>
      <a-button type="primary" @click="bedAdd"  :disabled="!hasPermission('床位管理')">
        <template #icon>
          <plus-outlined />
        </template>新增床位
      </a-button>
      <a-modal v-model:visible="bedAddVisible" title="新增床位" @ok="bedAddOk" @cancel="bedAddCancel" ok-text="增加"
        cancel-text="取消" :afterClose="bedAddCancel" width="320px">
        <a-form>
          <a-form-item label="科室：">
            <a-select v-model:value="selectedOfficeName0" @change="selectRoomNameChange" style="width: 100px"
              size="small">
              <a-select-option v-for="office in filteredOfficeList0" :key="office.officeId" :value="office.officeName">
                <span>{{ office.officeName }}</span>
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="病区：">
            <a-select v-model:value="selectedWardName0" @change="selectRoomNameChange" style="width: 100px"
              size="small">
              <a-select-option v-for="ward in filteredWardList0" :key="ward.wardId" :value="ward.wardName">
                <span>{{ ward.wardName }}</span>
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="病房号：">
            <a-select v-model:value="selectRoomName" @change="selectRoomNameChange" style="width: 100px" size="small">
              <a-select-option v-for="room in roomsData" :key="room.roomId" :value="room.roomNumber">
                <span>{{ room.roomNumber }}</span>
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="床位号：">
            <a-select v-model:value="baseBedNumber" style="width: 100px" size="small">
              <a-select-option v-for="baseBedNumber in currentBedNumber" :key="baseBedNumber" :value="baseBedNumber">
                <span>{{ baseBedNumber }}</span>
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="床位类型：">
            <a-select v-if="currentRoomGender === '混合病房'" v-model:value="baseBedType" style="width: 86px" size="small">
              <a-select-option v-for="baseBedType in baseBedTypes" :key="baseBedType" :value="baseBedType">
                <span>{{ baseBedType }}</span>
              </a-select-option>
            </a-select>
            <a-select v-if="currentRoomGender === '男性病房'" v-model:value="baseBedType1" style="width: 86px" size="small">
              <a-select-option v-for="baseBedType1 in baseBedTypes1" :key="baseBedType1" :value="baseBedType1">
                <span>{{ baseBedType1 }}</span>
              </a-select-option>
            </a-select>
            <a-select v-if="currentRoomGender === '女性病房'" v-model:value="baseBedType2" style="width: 86px" size="small">
              <a-select-option v-for="baseBedType2 in baseBedTypes2" :key="baseBedType2" :value="baseBedType2">
                <span>{{ baseBedType2 }}</span>
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-form>
      </a-modal>
    </div>
  </div>
  <keep-alive>
    <component :is="currentComponent" />
  </keep-alive>

</template>
<script lang="ts" setup>
import { computed, ref, watch, shallowRef, reactive, onMounted, onBeforeMount, provide } from 'vue';
import List from "@/layout/main/bedview/List.vue";
import Card from "@/layout/main/bedview/Card.vue";
import type { Bed, Ward, Office, Campus, Room } from "./types/Bed.ts";
import { getBedsData, getWardsData, getRooms, addBedRequest } from '@/api/bedView/bedData.ts';
import axios from 'axios'
import { message } from 'ant-design-vue';
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
  console.log('wraad', ward)
  recordWardId0.value = ward?.wardId //记录当前wardId，以便后面请求数据时传递
  let result = await getRooms(ward?.wardId as number)
  roomsData.value = result.data
  selectRoomName.value = roomsData.value[0].roomNumber

})

const bedTypeList = ['全部', '正床', '男床', '女床'];
const admissionTypeList = ['全部', '急诊入院', '预约入院', '手术入院'];
const wardTypeList = ['全部', '普通病房', '特殊病房', 'ICU',];
const wardPeopleCountList = ['全部', 1, 2, 3, 4, 5, 6];
const bedType = ref(bedTypeList[0]);
const admissionType = ref(admissionTypeList[0]);
const wardType = ref(wardTypeList[0]);
const wardPeopleCount = ref(wardPeopleCountList[0]);

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


const visible1 = ref<boolean>(false);
const visible2 = ref<boolean>(false);
const showModal1 = () => {
  visible1.value = true;
};

const bedsData = ref<Bed[]>([
//   {
//   bedId: 1,
//   bedNumber: '101房',
//   roomNumber: '01床',
//   bedStatus: '占用',
//   roomType: '隔离病房',
//   bedType: '男床',
//   bedCount: 1,
//   roomGender: '混合病房',
//   patientId: 1,
//   patientName: '张三',
//   patientAge: 30,
//   patientGender: '男',
//   telephoneNumber: '111111',
//   admissionType: '预约入院',
//   admissionTime: '2024/04/20',
//   admissionNumber: 'A1A1A1',
//   bookType: '微信小程序',
//   doctor: '老李',
//   illness: '高血压',
//   isEmergency: 1,
//   isAcute: 1,
//   isVip: 1
// },
// {
//   bedId: 2,
//   bedNumber: '102房',
//   roomNumber: '01床',
//   bedStatus: '空闲',
//   roomType: '普通病房',
//   bedType: '女床',
//   bedCount: 4,
//   patientId: null,
//   roomGender: '混合病房',
//   patientName: null,
//   patientAge: null,
//   patientGender: null,
//   telephoneNumber: null,
//   admissionType: null,
//   admissionTime: null,
//   admissionNumber: null,
//   bookType: null,
//   doctor: null,
//   illness: null,
//   isEmergency: null,
//   isAcute: null,
//   isVip: null
// },
// {
//   bedId: 3,
//   bedNumber: '103房',
//   roomNumber: '01床',
//   bedStatus: '空闲',
//   roomType: '普通病房',
//   bedType: '正床',
//   bedCount: 3,
//   roomGender: '混合病房',
//   patientId: null,
//   patientName: null,
//   patientAge: null,
//   patientGender: null,
//   telephoneNumber: null,
//   admissionType: null,
//   admissionTime: null,
//   admissionNumber: null,
//   bookType: null,
//   doctor: null,
//   illness: null,
//   isEmergency: null,
//   isAcute: null,
//   isVip: null
// },
// {
//   bedId: 4,
//   bedNumber: '104房',
//   roomNumber: '01床',
//   bedStatus: '即将出院',
//   roomType: '隔离病房',
//   bedType: '男床',
//   bedCount: 1,
//   roomGender: '混合病房',
//   patientId: 4,
//   patientName: '李四',
//   patientAge: 30,
//   patientGender: '男',
//   telephoneNumber: '111111',
//   admissionType: '预约入院',
//   admissionTime: '2024/04/20',
//   admissionNumber: 'A1A1A1',
//   bookType: '微信小程序',
//   doctor: '老李',
//   illness: '高血压',
//   isEmergency: 1,
//   isAcute: 1,
//   isVip: 1
// }
]);
onMounted(async () => {
  // let { data } = await axios.get('http://localhost:3000/beds');
  let { data } = await getWardsData();
  campusData.splice(0, campusData.length, ...data)
  selectedCampusName.value = campusData[0].campusName;
  recordCampusId.value = campusData[0].campusId;
  recordWardId.value = campusData[0].offices[0].wards[0].wardId;
  let params = {
    campusId: recordCampusId.value === -1 ? null : recordCampusId.value,
    officeId: null,
    wardId: null,
    bedType: bedType.value === '全部' ? null : bedType.value,
    roomType: wardType.value === '全部' ? null : wardType.value,
    bedCount: wardPeopleCount.value === '全部' ? null : wardPeopleCount.value,
  }
  let result = await getBedsData(params);
  bedsData.value = result.data;
})

const handleSearch = async () => {
  let params = {
    campusId: recordCampusId.value === -1 ? null : recordCampusId.value,
    officeId: recordOfficeId.value === -1 ? null : recordOfficeId.value,
    wardId: recordWardId.value === -1 ? null : recordWardId.value,
    bedType: bedType.value === '全部' ? null : bedType.value,
    roomType: wardType.value === '全部' ? null : wardType.value,
    bedCount: wardPeopleCount.value === '全部' ? null : wardPeopleCount.value,
  }
  let result = await getBedsData(params);
  bedsData.value = result.data;
}
// 提供 handleSearch 给所有后代组件
provide('handleSearch', handleSearch)

// 提供 bedsData 给所有后代组件
provide('bedsData', bedsData);

const handleOk = (e: MouseEvent) => {
  visible1.value = false;
};
const showModal2 = () => {
  visible2.value = true;
};

const id = ref(7);
const getId = () => {
  id.value++
  return id.value
}


//新增床位
const bedAddVisible = ref<boolean>(false)
const roomsData = ref<Room[]>([
  {
  roomId: 1,
  roomNumber: '101房',
  bedCount: 1,
  roomGender: '男性病房'
}, {
  roomId: 2,
  roomNumber: '102房',
  bedCount: 1,
  roomGender: '男性病房'
}, {
  roomId: 3,
  roomNumber: '103房',
  bedCount: 1,
  roomGender: '男性病房'
}, {
  roomId: 4,
  roomNumber: '104房',
  bedCount: 1,
  roomGender: '男性病房'
}
])
const selectRoomName = ref()
const baseBedTypes = ['正床', '男床', '女床']
const baseBedType = ref(baseBedTypes[0])
const baseBedTypes1 = ['正床', '男床']
const baseBedType1 = ref(baseBedTypes[0])
const baseBedTypes2 = ['正床', '女床']
const baseBedType2 = ref(baseBedTypes[0])
const baseBedNumbers = ['01床', '02床', '03床', '04床', '05床', '06床', '07床', '08床']
const currentBedNumber = ref<string[]>()
const baseBedNumber = ref(currentBedNumber.value !== undefined ? currentBedNumber.value[0] : baseBedNumbers[0])
const currentRoomGender = ref('混合病房')
const bedAdd = async () => {
  //获取当前病区病房
  const campus = campusData.find(c => c.campusId === recordCampusId.value);
  recordOfficeId0.value = campus?.offices[0].officeId
  recordWardId0.value = campus?.offices[0].wards[0].wardId
  console.log('病房0', campus)
  let params = recordWardId.value === null ? 1 : campus?.offices[0].wards[0].wardId as number
  console.log('病房', params)
  let result = await getRooms(params)
  roomsData.value = result.data
  recordRoomId.value = roomsData.value[0].roomId
  selectRoomName.value = roomsData.value[0].roomNumber
  const startCount = roomsData.value[0].bedCount;
  currentBedNumber.value = baseBedNumbers.slice(startCount);
  baseBedNumber.value = currentBedNumber.value[0]
  currentRoomGender.value = roomsData.value[0].roomGender
  bedAddVisible.value = true
}
// 处理选择病房变化的函数，记录选择的病房id
const recordRoomId = ref<number>(roomsData.value[0].roomId)
const selectRoomNameChange = (value: string) => {
  // 找到匹配选中 campusName 的 campusItem
  const selectRoom = roomsData.value.find(
    item => item.roomNumber === value
  );
  // 如果找到了，更新 recordRoomId 的值
  if (selectRoom) {
    if (typeof selectRoom?.roomId === 'number') recordRoomId.value = selectRoom?.roomId;
    const selectedRoom = roomsData.value.find(room => room.roomNumber === selectRoomName.value);
    const startCount = selectedRoom ? selectedRoom.bedCount : 0;
    console.log('startCount', startCount)
    currentBedNumber.value = baseBedNumbers.slice(startCount);
    baseBedNumber.value = currentBedNumber.value[0]
    currentRoomGender.value = selectedRoom?.roomGender as string
  }
};
//确认新增
const bedAddOk = async () => {
  console.log('roomId', recordRoomId.value);
  let currentBedType
  if (currentRoomGender.value === '混合病房') currentBedType = baseBedType.value
  if (currentRoomGender.value === '男性病房') currentBedType = baseBedType1.value
  if (currentRoomGender.value === '女性病房') currentBedType = baseBedType2.value
  try {
    let params = {
      campusId: recordCampusId.value,
      officeId: recordOfficeId0.value,
      wardId: recordWardId0.value,
      roomId: recordRoomId.value,
      bedNumber: baseBedNumber.value,
      bedType: currentBedType
    }
    console.log(params)
    let result = await addBedRequest(params)
    message.success('床位新增成功')
    //增加成功之后再发一个查询请求更新页面
    await handleSearch()
  }
  catch (error: any) {
    // 错误处理
    console.log('请求失败:', error);
  };
  bedAddVisible.value = false
}
const bedAddCancel = () => {
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
  font-size: 14px;
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

.severeIllness {
  margin-right: 2px;
  font-weight: 700;
  font-size: 16px;
  color: #fd6700;
}
</style>
<!-- 
//增加扩展属性
const addVisible = ref<boolean>(false)
const addAttributeName = ref<string>('张三')
const addAttributeValues = ref<string[]>([]);
const addIsAvailable = ref<boolean>(false)
const addAttribute = () => {
  addVisible.value = true
}
const addAttributeOk = () => {
  addVisible.value = false;
  extendedAttributes.push(
    {
      attributeId: getId(),
      attributeName: addAttributeName.value,
      attributeValues: addAttributeValues.value,
      isAvailable: addIsAvailable.value // 是否可用的布尔值
    }
  )
}

const editVisible = ref<boolean>(false);
const editAttributeName = ref<string>();
const editAttributeValues = ref<string[]>([]);
const editIsAvailable = ref<boolean>();
const editIndex = ref<number>(0);
//编辑按钮
const edit = (index: number) => {
  editVisible.value = true;
  editAttributeName.value = extendedAttributes[index].attributeName;
  editAttributeValues.value = extendedAttributes[index].attributeValues;
  editIsAvailable.value = extendedAttributes[index].isAvailable
  editIndex.value = index;
}
//删除值的项
const removeOption = (index: number, optionIndex: number) => {
  extendedAttributes[index].attributeValues.splice(optionIndex, 1)
}
const addOption = (index: number) => {
  extendedAttributes[index].attributeValues.push('请输入');
  console.log(extendedAttributes[index].attributeValues);

}

//删除扩展项按钮
const attributeDelete = (attributeId: number) => {

  const attributeIndex = extendedAttributes.findIndex(attribute => attribute.attributeId === attributeId);
  extendedAttributes.splice(attributeIndex, 1)
}

// 扩展属性信息
const extendedAttributes = reactive([
  {
    attributeId: 1,
    attributeName: '病房类型',
    attributeValues: ['一般病房', '特别病房', 'ICU'],
    isAvailable: true // 是否可用的布尔值
  },
  {
    attributeId: 2,
    attributeName: '床位状态',
    attributeValues: ['空床', '占用床', '清洁床'],
    isAvailable: true
  },
  {
    attributeId: 3,
    attributeName: '床位等级',
    attributeValues: ['普通床位', '豪华床位', 'VIP床位'],
    isAvailable: true
  },
  {
    attributeId: 4,
    attributeName: '医疗设备',
    attributeValues: ['是否配备呼吸机', '是否配备监护仪'],
    isAvailable: true
  },
  {
    attributeId: 5,
    attributeName: '护理级别',
    attributeValues: ['一级护理', '二级护理', '三级护理'],
    isAvailable: true
  },
  {
    attributeId: 6,
    attributeName: '患者信息',
    attributeValues: ['姓名', '年龄', '诊断信息'],
    isAvailable: true
  }

]) -->
<!-- 
       <a-button type="primary" size="small" @click="showModal1">床旁设备</a-button> -->
<!-- <a-modal v-model:visible="visible1" title="床旁设备" @ok="handleOk">
        <p>Some contents...</p>
        <p>Some contents...</p>
        <p>Some contents...</p>
      </a-modal> -->
<!-- <a-button type="primary" size="small" @click="showModal2">床位扩展属性</a-button>
      <a-modal v-model:visible="visible2" title="床位扩展属性" ok-text="增加" cancel-text="取消" @ok="addAttribute">
        <div class="extendedAttributes" v-for="(attribute, index) in extendedAttributes" :key="attribute.attributeId">
          <div>{{ attribute.attributeName }}：
            <select id="ward_type" name="ward_type">
              <option v-for="value in attribute.attributeValues" :key="value" :value="value">{{ value }}</option>
            </select>
          </div>
          <div>
            <span class="is-available">是否可用：</span>
            <span v-if="attribute.isAvailable === true" class="is-available1">是</span>
            <span v-else-if="attribute.isAvailable === false" class="is-available2">否</span>
            <a-button type="link" size="small" @click="edit(index)">编辑</a-button>
            <a-modal v-model:visible="editVisible" title="扩展属性编辑" ok-text="确定" cancel-text="取消" @ok="handleOk">
              <div>
                <div>属性名称</div>
                <input type="text" v-model="editAttributeName">
              </div>
              <div>床旁设备分类：</div>
              <div v-for="(value, optionIndex) in editAttributeValues" :key="optionIndex">
                <input type="text" v-model="editAttributeValues[optionIndex]">
                <a-button type="link" danger @click="removeOption(editIndex, optionIndex)">删除</a-button>
              </div>
              <a-button type="link" @click="addOption(editIndex)">添加选项</a-button>
              <div><label>是否可用：</label>
                <input type="checkbox" id="isAvailable" :value="editIsAvailable">
              </div>
            </a-modal>
            <a-button type="link" danger @click="attributeDelete(attribute.attributeId)">删除</a-button>
          </div>
        </div>
      </a-modal> 
    
    <a-modal v-model:visible="addVisible" title="添加床位扩展属性" @ok="addAttributeOk" ok-text="确定" cancel-text="取消">
      <div>
        <div>属性名称</div>
        <input type="text" :value="addAttributeName">
      </div>
      <div>值列表：</div>
      <div>
        <input type="text" v-model="addAttributeValues">
        <a-button type="link" danger>删除</a-button>
      </div>
      <a-button type="link" @click="addOption">添加选项</a-button>
      <div><label for="isAvailable">是否可用：</label>
        <input type="checkbox" id="isAvailable" :value="addIsAvailable" />
      </div>
    </a-modal>-->