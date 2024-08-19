<template>
  <div class="bed-overview">
    <div class="title">
      <span>床位概览</span>
      <span class="data">日期</span>
    </div>
    <div class="content">
      <div class="overview">
        <div class="number">1</div>
        <span class="count">
          空闲：<i>5</i>
          计划出院：<i>2</i>
          留床：<i>2</i>
        </span>
      </div>
      <div class="overview">
        <div class="number">1</div>
        <span class="count">
          空闲：<i>5</i>
          计划出院：<i>2</i>
          留床：<i>2</i>
        </span>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue';
</script>
<style scoped lang="scss">
.bed-overview {
  height: 600px;
  width: 300px;
  border: 1px solid #000;

  .title {
    display: flex;
    height: 36px;
    width: 100%;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    background-color: #b1a6a6df;
  }

  .content {
    display: flex;
    flex-wrap: wrap;
    flex-direction: column; // 将元素在垂直方向上排列
    justify-content: flex-start;
    align-items: center;
    width: 100%;
    height: 100%;

    .overview {
      display: flex;
      width: 260px;
      height: 48px;
      font-size: 12px;
      margin: 12px auto;
      border: 1px solid #973232;

      .number {
        width: 46px;
        height: 46px;
        line-height: 46px;
        text-align: center;
        background-color: #973232;
      }

      .count {
        line-height: 48px;
        padding: 0 6px;

        i {
          margin-right: 12px;
        }
      }
    }
  }
}
</style>