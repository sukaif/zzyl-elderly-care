<template>
  <div>
    <el-form :inline="true" class="mb8">
      <el-form-item label="开始日期"><el-date-picker v-model="startDate" type="date" placeholder="选择开始" value-format="YYYY-MM-DD" /></el-form-item>
      <el-form-item label="结束日期"><el-date-picker v-model="endDate" type="date" placeholder="选择结束" value-format="YYYY-MM-DD" /></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>

    <el-row :gutter="40" v-if="data">
      <el-col :span="8"><el-card shadow="hover" :body-style="{padding:'30px',textAlign:'center'}"><div style="font-size:36px;font-weight:bold;color:#67c23a">{{ data.checkInCount||0 }}</div><div style="color:#999;margin-top:8px">入住人数</div></el-card></el-col>
      <el-col :span="8"><el-card shadow="hover" :body-style="{padding:'30px',textAlign:'center'}"><div style="font-size:36px;font-weight:bold;color:#f56c6c">{{ data.checkOutCount||0 }}</div><div style="color:#999;margin-top:8px">退住人数</div></el-card></el-col>
      <el-col :span="8"><el-card shadow="hover" :body-style="{padding:'30px',textAlign:'center'}"><div style="font-size:36px;font-weight:bold;color:#e6a23c">{{ data.leaveCount||0 }}</div><div style="color:#999;margin-top:8px">请假人次</div></el-card></el-col>
    </el-row>
    <el-empty v-else description="选择日期范围后查询" />
  </div>
</template>

<script setup lang="ts">
import { getFlowStats } from "@/api/nursing/stay"

const startDate = ref('')
const endDate = ref('')
const data = ref<any>(null)

function loadData() {
  getFlowStats(startDate.value, endDate.value).then(res => {
    data.value = res.data
  })
}
</script>
