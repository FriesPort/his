<template>
  <div class="whole">
    <div class="box">
      <div class="header">
        <div class="content">
          <div class="left-section">
            <img :src="image1" alt="描述图片1" />
            <h3>床位分配</h3>
          </div>
          <div class="right-section">
            <h2>院区</h2>
            <select v-model="selectedOption1">
              <option disabled value="">请选择院区</option>
              <option>广州医院区</option>
              <option>珠海院区</option>
              <option>深圳院区</option>
            </select>
            <h2>病区</h2>
            <select v-model="selectedOption2">
              <option disabled value="">请选择病区</option>
              <option value="选项A">内科</option>
              <option value="选项B">外科</option>
            </select>
            <select v-model="selectedOption3">
              <option disabled value="">请选择选项3</option>
              <option
                v-for="option in computedOptionsForSelect3"
                :key="option"
                :value="option"
              >
                {{ option }}
              </option>
            </select>
            <h2>类型</h2>
            <select v-model="selectedOption6">
              <option disabled value="">请选择类型</option>
              <option value="选项A">正床</option>
              <option value="选项B">男床</option>
              <option value="选项C">女床</option>
            </select>
            <h2>房区</h2>
            <select v-model="selectedOption4">
              <option disabled value="">请选择房区</option>
              <option>普通病房</option>
              <option>特殊病房</option>
              <option>ICU</option>
            </select>
            <h2>房间</h2>
            <select v-model="selectedOption5">
              <option disabled value="">请选择选项5</option>
              <option>选项X</option>
              <option>选项Y</option>
              <option>选项Z</option>
            </select>
          </div>
        </div>
      </div>
    </div>
    <div class="xiamian">
      <!-- 引入患者列表 -->
      <PatientList
        :selectedBeds="selectedBeds"
        @update:selectedRow="handleSelectedRow"
        :rowdata="rowdata"
        :cancelID="cancelID"
        :change-time="changeTime"
      />
      <div class="operate-area">
        <div class="shang">
          <div class="bedarea">
            <BedCard
              v-for="index in 8"
              :key="index"
              :index="index"
              :id="'bed-' + (100 + index)"
              :show-checkbox="showCheckboxes[index - 1]"
              @change="
                updateSelectedBeds({ id: 'bed-' + (100 + index), index })
              "
            />
          </div>
          <div class="bedoperate">
            <button class="xuanze" @click="toggleCheckboxes">床位选择</button>
            <button class="shifang">床位释放</button>
          </div>
        </div>
        <div class="xia">
          <div class="selectarea">
            <div
              v-for="selectedBed in selectedBeds"
              :key="selectedBed.id"
              class="selectbed"
            >
              {{ selectedBed.id }}

              <span v-if="selectedBed.rowData">{{
                selectedBed.rowData.name
              }}</span>
            </div>
          </div>
          <div class="selectoperate"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PatientList from "./PatientList.vue";
import BedCard from "./BedCard.vue";
import image1 from "@/assets/病房患者.png";

export default {
  components: {
    PatientList,
    BedCard,
  },

  watch: {
    rowdata: {
      handler(newValue) {
        console.log("rowdata 发生变化:", newValue);
      },
      deep: true, // 监测深层变化
      immediate: true,
    },
  },

  data() {
    return {
      /*       这个数组用于存放分配患者
       */
      changeTime: 0, // 记录更改次数
      cancelID: null, // 用于保存要传递给子组件的 ID
      rowdata: Array(10).fill(null), // 初始化为包含 10 个 null 的数组
      image1,
      selectedOption1: "",
      selectedOption2: "",
      selectedOption3: "",
      selectedOption4: "",
      selectedOption5: "",
      selectedOption6: "",
      // 假设这些是所有可能的选项
      optionsForSelect3: {
        选项A: ["内科1区", "内科2区", "内科3区"],
        选项B: ["外科1区", "外科2区", "外科3区"],
      },
      showCheckboxes: Array(8).fill(false), // 初始状态为不显示
      checkboxVisible: false,
      selectedBeds: [],
    };
  },
  methods: {
    handleSelectedRow(selectedRow) {
      const index = this.rowdata.findIndex(
        (item) => item && item.id === selectedRow.id
      );
      if (index === -1) {
        // 找到第一个空位置
        const emptyIndex = this.rowdata.findIndex((item) => item === null);
        if (emptyIndex !== -1) {
          this.rowdata[emptyIndex] = selectedRow; // 添加新行到空位置
          // 仅更新 selectedBeds 中的 rowData
          this.selectedBeds[emptyIndex].rowData = selectedRow;
        } else {
          console.log("已达到最大选择数量");
        }
      } else {
        // 移除已存在的行
        this.rowdata[index] = null;
        // 更新 selectedBeds 中的对应 rowData 为 null
        this.selectedBeds[index].rowData = null;
      }
      this.selectedBeds = [...this.selectedBeds]; // 强制触发更新
      this.rowdata = [...this.rowdata];
    },

    updateSelectedBeds(bed) {
      const idx = this.selectedBeds.findIndex(
        (selectedBed) => selectedBed.id === bed.id
      );

      if (idx > -1) {
        const removedBed = this.selectedBeds[idx];
        if (removedBed.rowData !== null) {
          console.log("移除的 rowData:", removedBed.rowData); // 输出 rowData
          console.log(removedBed.rowData.id);
          const indexToRemove = this.rowdata.findIndex(
            (item) => item.id === removedBed.rowData.id
          );
          // 如果找到索引，则移除该元素
          if (indexToRemove !== -1) {
            this.cancelID = this.rowdata[indexToRemove].id;
            this.changeTime += 1; // 增加更改次数
            console.log(
              "移除成功，当前 rowData:",
              this.rowdata[indexToRemove].id
            );
            this.rowdata[indexToRemove] = null; // 将元素变为 null
          } else {
            console.log("未找到对应的 id，无法移除。");
          }
        }
        this.selectedBeds.splice(idx, 1);
      } else {
        // 如果没有选中，添加到数组，并将 rowData 默认为 null
        this.selectedBeds.push({
          id: bed.id,
          index: this.selectedBeds.length, // 或者其他合适的索引逻辑
          rowData: null, // 默认为 null
        });
      }
    },

    toggleCheckboxes() {
      // 切换每个 checkbox 的显示状态
      this.showCheckboxes = this.showCheckboxes.map((show) => !show);
    },
  },
  computed: {
    // 根据 selectedOption2 的值动态计算第二个 select 的选项
    computedOptionsForSelect3() {
      // 如果 selectedOption2 没有值，则返回空数组
      if (!this.selectedOption2) return [];

      // 根据 selectedOption2 的值返回对应的子选项
      return this.optionsForSelect3[this.selectedOption2];
    },
  },
};
</script>

<style scoped>
.whole {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

.xiamian {
  width: 95%;
  height: 80%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
.box {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  width: 95%;
  /* 可以根据需要调整宽度 */
  background-color: rgb(237 237 237 / 76%);
}

.header {
  background-color: #f5f5f5;
  padding: 10px;
  text-align: center;
  border-bottom: 1px solid #ccc;
}

.content {
  display: flex;
  align-items: center;
  /* margin-top: 10px; */
  justify-content: space-between;
}

.left-section {
  width: 20%;
  height: 100%;
  margin-right: 10px;
  font-weight: 800;
  font-size: calc(100vw * 30 / 1920);
  display: flex;
  justify-content: space-evenly;
  text-align: center;
  align-items: center;
  flex-direction: row;
  padding: 15px;
  /* 左侧与右侧之间的间距 */
  border: 1px solid #dad2d2;
  box-shadow: 0.25rem 0.25rem 0.625rem rgba(0, 0, 0, 0.5); /* 添加阴影 */
  margin-bottom: 0;
}
.left-section h3 {
  margin: 0; /* 去掉默认边距 */
  text-align: center; /* 水平居中 */
  font-size: calc(100vw * 30 / 1920); /* 设置字体大小 */
  font-weight: 800; /* 设置字体加粗 */
  color: #333; /* 设置字体颜色（可以根据需要修改） */
}
.left-section img {
  width: 50px;
  height: 50px;
}

.right-section {
  display: flex;
  /* 使右侧的选择框也横向排列 */
  justify-content: space-evenly;
  flex-direction: row;
  gap: 2vw;
  width: 80%;
  height: 100%;
  border: 1px solid #dad2d2;
  box-shadow: 0.25rem 0.25rem 0.625rem rgba(0, 0, 0, 0.5); /* 添加阴影 */
  padding: 10px;
  /* padding: 15px; */
  /* 选择框之间的间距 */
}

.right-section h2 {
  font-size: calc(100vw * 30 / 1920);
  margin: 0;
}
select {
  margin: 5px 0;
  width: 15%;
}
.operate-area {
  width: 60%;
  height: 95%;
  display: flex;
  justify-content: space-between;
  /* 水平居中对齐 */
  align-items: center;
  /* 垂直居中对齐 */
  flex-direction: column;
  border-radius: 5px;
  /*   padding: 10px;
 */
}
.shang {
  background-color: #eeeeeec2;
  border: 1px solid #8e8b8b;
  width: 100%;
  height: 60%;
  display: flex;
  justify-content: space-around;
  border-radius: 5px;
  align-items: center;
}
.bedarea {
  background-color: #f5f5f5;
  border-bottom: 1px solid #ccc;
  width: 85%;
  height: 95%;
  display: flex;
  flex-wrap: wrap;
  padding: 10px;
}
.bedoperate {
  border: 5px solid rgb(237 237 237 / 76%);
  box-shadow: 0.25rem 0.25rem 0.625rem rgba(43, 43, 43, 0.5); /* 添加阴影 */

  width: 11%;
  height: 95%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.xuanze {
  width: 80%;
  height: 40%;
  background-color: #1677ff;
  border: 1px solid hwb(128 86% 11%);
  border-radius: 10px;
  cursor: pointer;
  color: white;
  text-align: center;
}
.shifang {
  width: 80%;
  height: 40%;
  background-color: hsl(0, 83%, 61%);
  border: 1px solid #bb3232;
  border-radius: 10px;
  cursor: pointer;
  color: white;
  text-align: center;
}
.xia {
  width: 100%;
  height: 35%;
  border: 5px solid rgb(237 237 237 / 76%);
  border-radius: 5px;

  display: flex;
  justify-content: space-around;
}
.selectarea {
  width: 90%;
  height: 100%;
  border: 5px solid rgb(237 237 237 / 76%);
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  padding: 5px;
  background-color: hsl(0, 0%, 95%);
}
.selectbed {
  height: 100%;
  width: 9%;
  border: 1px solid #e62971;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  cursor: grab;
  text-align: center;
}
.selectbed:active {
  /* 修改这里 */
  cursor: grabbing; /* 拖拽状态 */
}
.selectoperate {
  border: 5px solid rgb(237 237 237 / 76%);
  width: 11%;
  height: 100%;
}
</style>
