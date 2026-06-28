<template>
  <div>
    <el-form :model="query" :inline="true" class="mb8">
      <el-form-item label="老人姓名"><el-input v-model="query.elderName" placeholder="请输入" clearable @keyup.enter="loadData" /></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">搜索</el-button><el-button @click="query.elderName='';loadData()">重置</el-button></el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="序号" type="index" width="60" align="center" />
      <el-table-column label="老人姓名" prop="elderName" width="120" />
      <el-table-column label="年龄" prop="age" width="60" align="center" />
      <el-table-column label="性别" width="60" align="center"><template #default="s">{{ s.row.gender==='0'?'男':'女' }}</template></el-table-column>
      <el-table-column label="身份证号" prop="idCardNo" width="180" />
      <el-table-column label="联系电话" prop="phone" width="120" />
      <el-table-column label="护理等级" prop="nursingLevelName" width="120" />
      <el-table-column label="入住日期" prop="checkInDate" width="100" />
      <el-table-column label="操作" width="160" align="center">
        <template #default="s">
          <el-button link type="primary" @click="openDetail(s.row)">查看</el-button>
          <el-button link type="primary" @click="printQr(s.row)">二维码</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" v-model:page="query.pageNum" v-model:limit="query.pageSize" @pagination="loadData" />

    <el-dialog title="入住详情" v-model="detailOpen" width="700px" append-to-body>
      <el-descriptions :column="2" border v-if="detailData">
        <el-descriptions-item label="老人姓名">{{ detailData.elderName }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ detailData.age }}岁</el-descriptions-item>
        <el-descriptions-item label="性别">{{ detailData.gender==='0'?'男':'女' }}</el-descriptions-item>
        <el-descriptions-item label="身份证号">{{ detailData.idCardNo }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ detailData.phone }}</el-descriptions-item>
        <el-descriptions-item label="家庭住址">{{ detailData.address }}</el-descriptions-item>
        <el-descriptions-item label="护理等级">{{ detailData.nursingLevelName }}</el-descriptions-item>
        <el-descriptions-item label="入住日期">{{ detailData.checkInDate }}</el-descriptions-item>
        <el-descriptions-item label="护理费用">¥{{ detailData.nursingFee }}/月</el-descriptions-item>
        <el-descriptions-item label="床位费用">¥{{ detailData.bedPrice }}/月</el-descriptions-item>
        <el-descriptions-item label="总费用">¥{{ detailData.totalCost }}/月</el-descriptions-item>
        <el-descriptions-item label="预交费用">¥{{ detailData.prePay }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { listCheckIn, getCheckInDetail } from "@/api/nursing/checkin"

const { proxy } = getCurrentInstance()
const list = ref<any[]>([])
const total = ref(0)
const loading = ref(false)
const detailOpen = ref(false)
const detailData = ref<any>(null)
const query = ref({ pageNum: 1, pageSize: 10, elderName: '' })

function loadData() {
  loading.value = true
  query.value.status = '1'
  listCheckIn(query.value).then(res => {
    list.value = res.rows
    total.value = res.total
    loading.value = false
  })
}

function openDetail(row: any) {
  getCheckInDetail(row.id).then(res => {
    detailData.value = res.data
    detailOpen.value = true
  })
}

function printQr(row: any) {
  proxy.$modal.msgSuccess("二维码生成中（需对接打印服务）")
}

onMounted(() => loadData())
</script>
