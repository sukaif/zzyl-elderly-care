<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="老人姓名" prop="elderName">
        <el-input v-model="queryParams.elderName" placeholder="请输入" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="老人身份证号" prop="idNumber">
        <el-input v-model="queryParams.idNumber" placeholder="请输入" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button @click="resetQuery">重置</el-button>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-button type="primary" style="margin-bottom: 15px; float:right;" @click="handleAdd">发起入住申请</el-button>

    <el-table v-loading="loading" :data="checkinList" stripe>
      <el-table-column label="序号" align="center" type="index" width="60" />
      <el-table-column label="老人姓名" align="center" prop="elderName" width="120" />
      <el-table-column label="老人身份证号" align="center" prop="idCardNo" width="180" />
      <el-table-column label="入住床位" align="center" prop="bedNumber" width="100" />
      <el-table-column label="护理等级" align="center" prop="nursingLevelName" width="140" />
      <el-table-column label="入住期限" align="center" prop="checkInDate" width="200" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
      <el-table-column label="操作" align="center" width="80">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-descriptions :column="2" border v-if="detailData">
        <el-descriptions-item label="老人姓名" :span="1">{{ detailData.elderName }}</el-descriptions-item>
        <el-descriptions-item label="年龄" :span="1">{{ detailData.age }} 岁</el-descriptions-item>
        <el-descriptions-item label="性别" :span="1">{{ detailData.gender === '0' ? '男' : '女' }}</el-descriptions-item>
        <el-descriptions-item label="身份证号" :span="1">{{ detailData.idCardNo }}</el-descriptions-item>
        <el-descriptions-item label="联系电话" :span="1">{{ detailData.phone }}</el-descriptions-item>
        <el-descriptions-item label="家庭住址" :span="1">{{ detailData.address }}</el-descriptions-item>
        <el-descriptions-item label="护理等级" :span="1">{{ detailData.nursingLevelName }}</el-descriptions-item>
        <el-descriptions-item label="入住日期" :span="1">{{ detailData.checkInDate }}</el-descriptions-item>
        <el-descriptions-item label="楼栋" :span="1">{{ detailData.floorName }}</el-descriptions-item>
        <el-descriptions-item label="房间" :span="1">{{ detailData.roomNumber }}</el-descriptions-item>
        <el-descriptions-item label="床位" :span="1">{{ detailData.bedNumber }}</el-descriptions-item>
        <el-descriptions-item label="状态" :span="1">
          <el-tag :type="detailData.status === '1' ? 'success' : 'danger'">
            {{ detailData.status === '1' ? '已入住' : '已退住' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="护理费用" :span="1">{{ detailData.nursingFee }} 元/月</el-descriptions-item>
        <el-descriptions-item label="床位费用" :span="1">{{ detailData.bedPrice }} 元/月</el-descriptions-item>
        <el-descriptions-item label="总费用" :span="1">{{ detailData.totalCost }} 元/月</el-descriptions-item>
        <el-descriptions-item label="预交费用" :span="1">{{ detailData.prePay }} 元</el-descriptions-item>
        <el-descriptions-item label="合同编号" :span="2">{{ detailData.contract?.contractNo }}</el-descriptions-item>
      </el-descriptions>
      <div v-else style="text-align:center; padding:40px 0; color:#999;">暂无数据</div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="Checkin">
import type { CheckInListVo, CheckInQueryParams, CheckInDetailVo } from "@/types/api/nursing/checkin"
import { listCheckIn, getCheckInDetail, delCheckIn } from "@/api/nursing/checkin"
import { useRouter } from "vue-router"

const { proxy } = getCurrentInstance()
const router = useRouter()

const checkinList = ref<CheckInListVo[]>([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const title = ref("")
const detailData = ref<CheckInDetailVo | null>(null)

const data = reactive({
  form: {} as any,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    elderName: undefined,
    idNumber: undefined,
    status: undefined,
  } as CheckInQueryParams,
})

const { queryParams, form } = toRefs(data)

function getList() {
  loading.value = true
  listCheckIn(queryParams.value).then(response => {
    checkinList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function cancel() {
  open.value = false
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleAdd() {
  router.push('/nursing/checkin/add')
}

function handleDetail(row: CheckInListVo) {
  getCheckInDetail(row.id!).then(response => {
    detailData.value = response.data
    title.value = "入住详情"
    open.value = true
  })
}

function handleDelete(row: CheckInListVo) {
  const _ids = row.id
  proxy.$modal.confirm('是否确认删除"' + row.elderName + '"的入住记录？').then(function() {
    return delCheckIn(_ids!)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

onMounted(() => {
  getList()
})
</script>
