<template>
  <div class="card">
    <div class="leftpart">
      <input
        type="checkbox"
        id="myCheckbox"
        v-model="isChecked"
        @change="handleChange"
        v-if="showCheckbox"
      />
    </div>
    <div class="rightpart"></div>
  </div>
</template>

<script>
export default {
  props: {
    showCheckbox: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      isChecked: this.checked, // 绑定父组件传来的 checked 状态
    };
  },
  watch: {
    checked(newVal) {
      this.isChecked = newVal; // 监听父组件的 checked 变化
    },
  },
  methods: {},
};
</script>

<style scoped>
.bed-card {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 5px;
  cursor: pointer;
}

.occupied {
  background-color: #6a282e;
}
.card {
  border: 1px solid #f8d7da;
  width: 24%;
  height: 45%;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: auto;
}
.leftpart {
  width: 20%;
  height: 100%;
  border: 1px solid #f8d7da;
  padding: 10px;
  display: flex;
  justify-content: center;
}
.rightpart {
  width: 80%;
}
/* #myCheckbox {
  display: none;
} */
</style>
