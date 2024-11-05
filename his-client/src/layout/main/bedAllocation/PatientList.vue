<template>
  <div class="pabox">
    <div class="list">
      <div class="find">
        <button class="page" @click="handleSearch">{{ buttonText }}</button>
        <div class="group">
          <div class="lg">
            <img :src="image3" alt="描述图片3" />
            <input
              style="width: calc(100vw * 140 / 1920)"
              type="text"
              v-model="searchQuery"
              placeholder="请输入患者姓名"
              @keyup.enter="handleSearch"
            />
          </div>

          <button class="sou" @click="Search">搜索</button>
        </div>
      </div>
      <div class="patients">
        <table>
          <thead>
            <tr>
              <th style="width: 60px; text-align: center; cursor: pointer">
                选择
              </th>
              <th style="width: 100px; text-align: center">姓名</th>
              <th style="width: 80px; text-align: center">信息</th>
              <th style="width: 200px; text-align: center">床位</th>
            </tr>
          </thead>
          <tbody>
            <!-- 创建10行空数据 -->
            <tr v-for="(row, index) in currentPatientData" :key="index">
              <td>
                <input
                  type="checkbox"
                  v-if="row && row.name"
                  v-model="row.checked"
                  :disabled="!isMaxSelected()"
                  :id="'checkbox-' + index"
                  @change="handleCheckboxChange(row)"
                />
              </td>
              <td class="pname" v-if="row">{{ row.name }}</td>
              <td>
                <button
                  class="check"
                  v-if="row && row.name"
                  @click="showInfo(row, index)"
                >
                  信息
                </button>
              </td>
              <td></td>
            </tr>
          </tbody>
        </table>
        <!-- 弹窗 -->
        <div v-if="showModal" class="modal">
          <div class="modal-content">
            <span class="close" @click="closeModal">&times;</span>
            <h2>患者信息</h2>
            <p><strong>姓名:</strong> {{ selectedPatient.name }}</p>
            <p><strong>年龄:</strong> {{ selectedPatient.age }}</p>
            <p><strong>性别:</strong> {{ selectedPatient.gender }}</p>
            <p><strong>电话:</strong> {{ selectedPatient.phone }}</p>
            <p><strong>是否紧急:</strong> {{ selectedPatient.is_emergency }}</p>
            <p><strong>是否VIP:</strong> {{ selectedPatient.is_vip }}</p>
          </div>
        </div>
      </div>
      <div class="turn">
        <img :src="image1" alt="描述图片1" />
        <img :src="image2" alt="描述图片2" />
      </div>
    </div>
    <div class="control">
      <button class="import" @click="handleButton1Click">待入院患者</button>

      <!-- 待入院患者也要在页面加载时就通过接口导入到表格 -->
      <div v-if="dialogVisible" class="dialog-overlay">
        <div class="dialog">
          <h3>待入院患者</h3>
          <table>
            <thead>
              <tr>
                <th>选择</th>
                <th>姓名</th>
                <th>信息</th>
                <th>截止时间</th>
                <th>确认入院</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(patient, index) in ipatients" :key="index">
                <td style="cursor: pointer">
                  <input
                    type="checkbox"
                    v-model="patient.selected"
                    :disabled="!patient.admitted"
                  />
                </td>
                <td>{{ patient.name }}</td>
                <td>
                  <button @click="viewPatientInfo(patient)">查看信息</button>
                </td>
                <td>剩余时间: {{ formatCountdown(patient.deadline) }}</td>
                <td>
                  <button
                    @click="admitPatient(index)"
                    :disabled="patient.admitted || !isCountdownActive(patient)"
                  >
                    确认入院
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
          <button @click="addPatients" class="add">添加</button>
          <button @click="dialogVisible = false" class="close">关闭</button>
        </div>
      </div>
      <button class="allinf" @click="handleButton2Click">分配信息</button>
    </div>
  </div>
</template>
<script>
import image1 from "@/assets/blockWhiteLeft.png"; // 根据实际路径调整
import image2 from "@/assets/blockWhiteRight.png"; // 根据实际路径调整
import image3 from "@/assets/患者查询.png";

import { getpatientsRequest } from "@/api/bedAllocation/bedAllocation"; // 根据你的项目路径导入函数

export default {
  name: "PatientList",
  props: {
    selectedBeds: {
      type: Array,
      default: () => [], // 设置默认值
    },
    rowdata: {
      type: Array,
      required: true,
    },
    cancelID: {
      type: String, // 修改为字符串类型
      required: false, // 是否必需
    },
    changeTime: {
      type: Number,
      default: 0,
    },
  },
  computed: {
    selectedBeds() {
      // 当前的选中床位数据
      const newBeds = this.selectedBeds;
      // 上一次的选中床位数据
      const oldBeds = this.previousSelectedBeds;
      // 处理逻辑，比如比较新旧数据
      if (JSON.stringify(newBeds) !== JSON.stringify(oldBeds)) {
      }

      // 更新 previousSelectedBeds 为当前的 selectedBeds
      this.previousSelectedBeds = [...newBeds]; // 保持深拷贝以避免引用问题

      return newBeds; // 返回处理后的数据
    },

    processedRowdata() {
      // 当前的 rowdata
      const newData = this.rowdata;
      // 上一次的 rowdata
      const oldData = this.previousRowdata;

      // 处理逻辑，比如比较新旧数据
      if (JSON.stringify(newData) !== JSON.stringify(oldData)) {
        /* console.log("旧数据:", oldData);
        console.log("新数据:", newData); */
        // 在这里可以进行数据转换或其他处理
      }

      // 更新 previousRowdata 为当前的 rowdata
      this.previousRowdata = [...newData]; // 保持深拷贝以避免引用问题

      return newData; // 返回处理后的数据
    },
  },
  watch: {
    changeTime(newChangeTime) {
      if (newChangeTime > 0) {
        this.deselectPatient(this.cancelID);
      }
    },

    selectedBeds: {
      handler() {
        this.selectedBeds;
      },
      deep: true,
      immediate: true, // 深度监听
    },
    rowdata: {
      handler() {
        this.processedRowdata; // 访问计算属性来触发其逻辑
      },
      immediate: true, // 深度监听
    },
  },
  data() {
    return {
      previousRowdata: [], // 用于存储上一次的 rowdata
      previousSelectedBeds: [],
      dialogVisible: false,
      // ipatients是待入院患者数组
      ipatients: [
        {
          id: "102",
          name: "张三",
          age: 11,
          gender: "男",
          phone: "12345678913",
          preassignbed: "0",
          is_emergency: "0",
          is_vip: "1",
          deadline: new Date(Date.now() + 60000),
          admitted: false,
          selected: false,
        }, // 1分钟后到期
        {
          id: "103",
          name: "李四",
          age: 12,
          gender: "男",
          phone: "12345678943",
          preassignbed: "0",
          is_emergency: "0",
          is_vip: "1",
          deadline: new Date(Date.now() + 300000),
          admitted: false,
          selected: false,
        }, // 5分钟后到期
        {
          id: "104",
          name: "王五",
          age: 15,
          gender: "男",
          phone: "12345678713",
          preassignbed: "0",
          is_emergency: "0",
          is_vip: "1",
          deadline: new Date(Date.now() + 120000),
          admitted: false,
          selected: false,
        }, // 2分钟后到期
      ],
      buttonText: "未预分配",
      searchQuery: "", // 用于绑定输入框的值
      image1,
      image2,
      image3,
      showModal: false,
      selectedPatient: {},
      // 初始化10行空数据
      //已分配床位的患者数组
      allocatedPatientData: Array(10)
        .fill()
        .map(() => ({
          preassignbed: "1",
          name: "",
          age: "",
          gender: "",
          phone: "",
          is_emergency: "",
          is_vip: "",
          checked: false,
        })),

      patientdata: Array(10)
        .fill()
        .map(() => ({
          preassignbed: "0",
          name: "",
          age: "",
          gender: "",
          phone: "",
          is_emergency: "",
          is_vip: "",
          checked: false,
        })),

      //这个数组用于
      currentPatientData: [],
    };
  },
  mounted() {
    /*     this.fetchPatients(); // 组件加载时调用获取患者的函数
     */ this.startCountdown();
    // 假设这是从服务器获取的数据，这里直接赋值
    this.patientdata[0] = {
      id: "101",
      name: "詹姆斯",
      age: 11,
      gender: "男",
      phone: "12345678912",
      preassignbed: "0",
      is_emergency: "0",
      is_vip: "0",
      checked: false,
    };
    //在这里，这个currentPatientData数组已经被赋予了十行表格
    //如果换页，patientdata数组接收新的一页数据即可，原本
    this.currentPatientData = this.patientdata;
  },
  created() {
    this.startCountdown();
  },

  methods: {
    deselectPatient(cancelID) {
      const patient = this.currentPatientData.find((p) => p.id === cancelID);
      if (patient) {
        patient.checked = false; // 将 selected 设置为 false，表示取消勾选
      }
    },

    // 判断勾选框是否应该禁用
    isDisabled(patientID) {
      return this.cancelID === patientID;
    },
    removePatient(removedBed) {
      if (removedBed.rowData !== null) {
        this.$emit("update-patient-data", removedBed.rowData); // 触发事件并传递数据
      }
    },

    isMaxSelected() {
      // 如果 selectedBeds 不为空且被选中的患者数量小于床位数量，则返回 true
      return (
        this.selectedBeds.length > 0 &&
        this.currentPatientData.filter((row) => row.checked).length <
          this.selectedBeds.length
      );
    },
    handleUncheckRow(rowId) {
      // 根据 rowId 取消勾选逻辑
      const row = this.currentPatientData.find((r) => r.id === rowId);
      if (row) {
        row.checked = false; // 假设每行都有一个 `checked` 属性
      }
    },
    handleCheckboxChange(row) {
      // Emit the selected row data to the parent
      this.$emit("update:selectedRow", row);
    },

    /* async fetchPatients() {
      try {
        console.log("被调用了");
        const response = await getpatientsRequest({}); // 根据需要传入参数

        this.patients = response.data; // 假设返回的数据在 response.data 中
      } catch (error) {
        console.error("获取患者列表失败", error);
      }
    },
 */
    addPatients() {
      const selectedPatients = this.ipatients.filter(
        (patient) => patient.selected
      );

      // 遍历选中的患者
      for (const patient of selectedPatients) {
        // 查找第一个 name 为空的索引
        const firstEmptyIndex = this.patientdata.findIndex(
          (patient) => patient.name === ""
        );

        // 如果有空位，则添加患者
        if (firstEmptyIndex !== -1) {
          this.patientdata[firstEmptyIndex] = { ...patient };
        } else {
          // 如果没有空位，停止添加
          console.warn("没有空位可以添加患者");
          break;
        }
      }
      // 移除已添加的患者
      this.ipatients = this.ipatients.filter((patient) => !patient.selected);
      /*       this.dialogVisible = false;
       */
    },

    viewPatientInfo(patient) {
      // alert(`查看患者信息: ${patient.name}`);
      alert(
        `ID:${patient.id}\n姓名: ${patient.name}\n年龄: ${patient.age}\n性别: ${patient.gender}\n电话: ${patient.phone}`
      );
    },
    admitPatient(index) {
      const now = new Date();
      if (now >= this.ipatients[index].deadline) {
        alert(`无法确认入院，${this.ipatients[index].name} 的入院时间已过期`);
        return;
      }

      this.ipatients[index].admitted = true;
      alert(`${this.ipatients[index].name} 已确认入院`);
    },

    formatCountdown(deadline) {
      const now = new Date();
      const remainingTime = deadline - now;

      if (remainingTime <= 0) {
        return "已过期";
      }

      const seconds = Math.floor((remainingTime / 1000) % 60);
      const minutes = Math.floor((remainingTime / 1000 / 60) % 60);
      const hours = Math.floor((remainingTime / (1000 * 60 * 60)) % 24);

      return `${hours}小时 ${minutes}分钟 ${seconds}秒`;
    },

    isCountdownActive(patient) {
      return new Date() < patient.deadline;
    },

    startCountdown() {
      setInterval(() => {
        this.ipatients.forEach((patient) => {
          if (!patient.admitted) {
            // 触发 Vue 的响应式更新
            patient.remainingTime = this.formatCountdown(patient.deadline);
          }
        });
      }, 1000); // 每秒更新一次
    },
    handleButton1Click() {
      this.dialogVisible = true;
    },

    handleButton2Click() {
      // 处理按钮 2 的点击事件
      console.log("按钮 2 被点击");
    },
    handleSearch() {
      // 切换显示的病人数据
      if (this.buttonText === "未预分配") {
        this.currentPatientData = this.allocatedPatientData;
        this.buttonText = "已预分配";
      } else {
        this.currentPatientData = this.patientdata;
        this.buttonText = "未预分配";
      }
    },
    showInfo(row) {
      // 显示病人信息的方法
      if (row) {
        const info = `ID:${row.id}\n姓名: ${row.name}\n年龄: ${row.age}\n性别: ${row.gender}\n电话: ${row.phone}`;
        alert(info);
      }
    },
  },
};
</script>
<style scoped>
.pabox {
  width: 35%;
  height: 95%;
  border: 1px solid hsl(206, 46%, 44%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  background-color: #1677ff;
  border-radius: 5px;
}
.find {
  display: flex;
  width: 96%;
  padding: 5px;
  height: 8%;
  justify-content: space-between;
}

.group {
  width: 65%;
  display: flex;
  justify-content: space-between;
}
.group img {
  width: auto;
  height: 100%;
}
.page {
  cursor: pointer;
  width: 30%;
  border: 0.0625rem solid #ccc;
  font-weight: bold;
  font-size: calc(100vw * 17 / 1920);
  box-shadow: 0.25rem 0.25rem 0.625rem rgba(0, 0, 0, 0.5); /* 添加阴影 */
  border-radius: 15px;
  text-align: center;
  align-items: center;
  display: flex;
  justify-content: center;
}

.lg {
  display: flex;
  width: 60%;
  font-size: calc(100vw * 18 / 1920);
}
.sou {
  cursor: pointer;
  width: calc(100vw * 70 / 1920);
  font-size: calc(100vw * 12 / 1920);
  font-weight: bold;
  text-align: center;
  border: 1px solid #a2a7b0;
}
.list {
  width: 100%;
  height: 86%;
  border: 1px solid rgb(247, 248, 247);
  background-color: white;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  align-items: center;
  border-radius: 5px;
}
.turn {
  width: 100%;
  height: 8%;
  display: flex;
  justify-content: space-evenly;
  border: 1px solid #a2a7b0;
  background-color: hsl(0, 0%, 91%);
  align-items: center;
}
.turn img {
  width: 20px;
  height: 20px;
  cursor: pointer;
}
.control {
  width: 100%;
  height: 12%;
  border: 1px solid whitesmoke;
  display: flex;
  justify-content: space-around;
  padding: 10px;
  background-color: whitesmoke;
  border-radius: 10px;
  box-shadow: 0.25rem 0.25rem 0.625rem rgba(0, 0, 0, 0.5); /* 添加阴影 */
}
.import {
  width: 35%;
  border-radius: 20px;
  background-color: #fff;
  border: 0.0625rem solid #ccc;
  cursor: pointer;
  font-weight: bold;
  font-size: calc(100vw * 17 / 1920);
  box-shadow: 0.25rem 0.25rem 0.625rem rgba(0, 0, 0, 0.5); /* 添加阴影 */
}

.allinf {
  width: 35%;
  border-radius: 20px;
  background-color: #fff;
  cursor: pointer;
  border: 0.0625rem solid #ccc;
  font-weight: bold;
  font-size: calc(100vw * 17 / 1920);
  box-shadow: 0.25rem 0.25rem 0.625rem rgba(0, 0, 0, 0.5); /* 添加阴影 */
}

/* 表格 */
.patients {
  width: 96%;
  height: 85vh;
  padding: 5px;
}
table {
  width: 100%; /* 表格宽度为100% */
  height: 100%; /* 占满容器的高度 */
  flex: 1;
  border-collapse: collapse; /* 合并边框 */
}

th,
td {
  border: 1px solid #ccc; /* 边框样式 */
  padding: auto;
  text-align: center;
  height: 34.75px;
  max-height: 34.75px;
}

th {
  background-color: #f0f0f0; /* 表头背景色 */
}
.check {
  max-height: 34.75px;
  height: 30px;
  font-size: 12px;
  width: 40px;
  padding: 0; /* 去掉内边距 */
  border: 1px solid #a2a7b0;
  font-weight: bold;
  background-color: hsl(0, 18%, 96%);
}
.pname {
  font-size: 1vw;
}
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #edecec80;
  display: flex;
  justify-content: center;
  align-items: center;
}
.dialog {
  background: white;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 400px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.add {
  margin: 5px 5px;
}
.close {
  margin: 5px 5px;
}
</style>