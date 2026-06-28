<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-card shadow="never" style="margin-bottom:16px">
      <el-form :model="queryParams" ref="queryRef" :inline="true" label-width="100px">
        <el-form-item label="老人姓名" prop="elderName">
          <el-input v-model="queryParams.elderName" placeholder="请输入" clearable @keyup.enter="handleQuery" style="width:160px" />
        </el-form-item>
        <el-form-item label="老人身份证号" prop="idCardNo">
          <el-input v-model="queryParams.idCardNo" placeholder="请输入" clearable @keyup.enter="handleQuery" style="width:180px" />
        </el-form-item>
        <el-form-item label="退住日期" prop="checkoutDateRange">
          <el-date-picker v-model="queryParams.checkoutDateRange" type="daterange" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" style="width:240px" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">搜索</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" @click="handleAdd">发起退住申请</el-button>
      </el-col>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="checkoutList" stripe border>
      <el-table-column label="序号" align="center" type="index" width="60" />
      <el-table-column label="老人姓名" align="center" prop="elderName" width="120" />
      <el-table-column label="老人身份证号" align="center" prop="idCardNo" width="180" />
      <el-table-column label="退住日期" align="center" prop="checkoutDate" width="120" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="170" />
      <el-table-column label="操作" align="center" width="80" fixed="right">
        <template #default="s">
          <el-button link type="primary" @click="handleDetail(s.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 详情弹窗 -->
    <el-dialog title="退住详情" v-model="open" width="650px" append-to-body>
      <el-descriptions :column="2" border v-if="detailData">
        <el-descriptions-item label="老人姓名">{{ detailData.elderName }}</el-descriptions-item>
        <el-descriptions-item label="身份证号">{{ detailData.idCardNo }}</el-descriptions-item>
        <el-descriptions-item label="退住类型">{{ { '0':'个人原因','1':'病情转院','2':'去世','3':'其他' }[detailData.checkoutType]||'--' }}</el-descriptions-item>
        <el-descriptions-item label="退住日期">{{ detailData.checkoutDate }}</el-descriptions-item>
        <el-descriptions-item label="实际离院日期">{{ detailData.actualLeaveDate||'--' }}</el-descriptions-item>
        <el-descriptions-item label="退住原因" :span="2">{{ detailData.reason||'--' }}</el-descriptions-item>
        <el-divider content-position="center">费用结算</el-divider>
        <el-descriptions-item label="已缴费用">¥{{ detailData.totalCharged||0 }}</el-descriptions-item>
        <el-descriptions-item label="应扣费用">¥{{ detailData.totalDeducted||0 }}</el-descriptions-item>
        <el-descriptions-item label="应退金额"><span style="color:#67c23a;font-weight:bold">¥{{ detailData.refundAmount||0 }}</span></el-descriptions-item>
        <el-descriptions-item label="欠款金额"><span style="color:#f56c6c;font-weight:bold">¥{{ detailData.arrears||0 }}</span></el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="Checkout">
import { useRouter } from "vue-router"
import { listCheckOut, getCheckOut } from "@/api/nursing/checkout"

const router = useRouter()
const { proxy } = getCurrentInstance()

const checkoutList = ref<any[]>([])
const open = ref(false)
const loading = ref(true)
const total = ref(0)
const detailData = ref<any>(null)

const queryParams = ref({
  pageNum: 1, pageSize: 10,
  elderName: undefined,
  idCardNo: undefined,
  checkoutDateRange: undefined,
})

function getList() {
  loading.value = true
  listCheckOut(queryParams.value).then(res => {
    checkoutList.value = res.rows
    total.value = res.total
    loading.value = false
  })
}

function handleQuery() { queryParams.value.pageNum = 1; getList() }
function resetQuery() { proxy.resetForm("queryRef"); handleQuery() }
function handleAdd() { router.push('/nursing/checkout/add') }
function handleDetail(row: any) {
  getCheckOut(row.id).then(res => { detailData.value = res.data; open.value = true })
}

onMounted(() => getList())
</script>
