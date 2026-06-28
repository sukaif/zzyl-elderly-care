<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="老人姓名" prop="elderName">
        <el-input v-model="queryParams.elderName" placeholder="请输入老人姓名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请假状态" clearable @change="handleQuery">
          <el-option label="待审批" value="0" />
          <el-option label="已审批" value="1" />
          <el-option label="已销假" value="2" />
          <el-option label="超时未归" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
        <el-button type="success" @click="handleAdd">新增请假</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="leaveList" stripe>
      <el-table-column label="序号" type="index" width="60" align="center" />
      <el-table-column label="老人姓名" prop="elderName" width="120" align="center" />
      <el-table-column label="床位" prop="bedNumber" width="100" align="center" />
      <el-table-column label="请假类型" prop="leaveType" width="110" align="center">
        <template #default="scope">{{ { '0': '外出探亲', '1': '就医', '2': '回家', '3': '其他' }[scope.row.leaveType] || '--' }}</template>
      </el-table-column>
      <el-table-column label="开始时间" prop="startTime" width="150" align="center" />
      <el-table-column label="预计返回" prop="expectedReturnTime" width="150" align="center" />
      <el-table-column label="天数" prop="leaveDays" width="60" align="center" />
      <el-table-column label="紧急联系人" prop="emergencyContact" width="100" align="center" />
      <el-table-column label="状态" prop="status" width="100" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.status === '0' ? 'warning' : scope.row.status === '1' ? 'primary' : scope.row.status === '2' ? 'success' : 'danger'">
            {{ { '0': '待审批', '1': '已审批', '2': '已销假', '3': '超时未归' }[scope.row.status] || '--' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280" align="center" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)">详情</el-button>
          <el-button link type="success" @click="handleApprove(scope.row)" v-if="scope.row.status === '0'">审批</el-button>
          <el-button link type="primary" @click="handleComplete(scope.row)" v-if="scope.row.status === '1'">销假</el-button>
          <el-button link type="danger" @click="handleOverdue(scope.row)" v-if="scope.row.status === '1'">超时未归</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row)" v-if="scope.row.status !== '0'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 新增请假弹窗 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="leaveRef" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="老人姓名" prop="elderName">
              <el-input v-model="form.elderName" placeholder="请输入老人姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入住ID" prop="checkInId">
              <el-input-number v-model="form.checkInId" :min="1" style="width:100%" placeholder="请输入入住ID" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="老人ID" prop="elderId">
              <el-input-number v-model="form.elderId" :min="1" style="width:100%" placeholder="请输入老人ID" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请假类型" prop="leaveType">
              <el-select v-model="form.leaveType" placeholder="请选择" style="width:100%">
                <el-option label="外出探亲" value="0" />
                <el-option label="就医" value="1" />
                <el-option label="回家" value="2" />
                <el-option label="其他" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择日期时间" style="width:100%" value-format="YYYY-MM-DD HH:mm:ss" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="预计返回" prop="expectedReturnTime">
              <el-date-picker v-model="form.expectedReturnTime" type="datetime" placeholder="选择日期时间" style="width:100%" value-format="YYYY-MM-DD HH:mm:ss" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="请假天数" prop="leaveDays">
              <el-input-number v-model="form.leaveDays" :min="1" :max="30" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="紧急联系人" prop="emergencyContact">
              <el-input v-model="form.emergencyContact" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人电话" prop="emergencyPhone">
              <el-input v-model="form.emergencyPhone" placeholder="请输入电话" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请假事由" prop="reason">
              <el-input v-model="form.reason" placeholder="请输入请假事由" type="textarea" :rows="2" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="submitForm" :loading="submitting">提交</el-button>
        <el-button @click="cancel">取消</el-button>
      </template>
    </el-dialog>

    <!-- 详情弹窗 -->
    <el-dialog title="请假详情" v-model="detailOpen" width="600px" append-to-body>
      <el-descriptions :column="2" border v-if="detailData">
        <el-descriptions-item label="老人姓名">{{ detailData.elderName }}</el-descriptions-item>
        <el-descriptions-item label="床位">{{ detailData.bedNumber || '--' }}</el-descriptions-item>
        <el-descriptions-item label="请假类型">{{ { '0': '外出探亲', '1': '就医', '2': '回家', '3': '其他' }[detailData.leaveType] }}</el-descriptions-item>
        <el-descriptions-item label="请假天数">{{ detailData.leaveDays }} 天</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ detailData.startTime }}</el-descriptions-item>
        <el-descriptions-item label="预计返回">{{ detailData.expectedReturnTime }}</el-descriptions-item>
        <el-descriptions-item label="实际返回">{{ detailData.actualReturnTime || '--' }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="detailData.status === '0' ? 'warning' : detailData.status === '1' ? 'primary' : detailData.status === '2' ? 'success' : 'danger'">
            {{ { '0': '待审批', '1': '已审批', '2': '已销假', '3': '超时未归' }[detailData.status] }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="紧急联系人">{{ detailData.emergencyContact }} / {{ detailData.emergencyPhone }}</el-descriptions-item>
        <el-descriptions-item label="审批人">{{ detailData.approver || '--' }}</el-descriptions-item>
        <el-descriptions-item label="请假事由" :span="2">{{ detailData.reason || '--' }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="Leave">
import { listLeaveRecord, getLeaveRecord, addLeaveRecord, delLeaveRecord, approveLeave, completeLeave, overdueLeave } from "@/api/nursing/leave"

const { proxy } = getCurrentInstance()

const leaveList = ref<any[]>([])
const open = ref(false)
const detailOpen = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const title = ref("")
const submitting = ref(false)
const detailData = ref<any>(null)

const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  elderName: undefined,
  status: undefined,
})

const form = ref({
  elderName: '',
  elderId: undefined,
  checkInId: undefined,
  leaveType: '0',
  startTime: '',
  expectedReturnTime: '',
  leaveDays: 1,
  emergencyContact: '',
  emergencyPhone: '',
  reason: '',
})

const rules = {
  elderName: [{ required: true, message: '请输入老人姓名', trigger: 'blur' }],
  elderId: [{ required: true, message: '请输入老人ID', trigger: 'blur' }],
  checkInId: [{ required: true, message: '请输入入住ID', trigger: 'blur' }],
  leaveType: [{ required: true, message: '请选择请假类型', trigger: 'change' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  expectedReturnTime: [{ required: true, message: '请选择预计返回时间', trigger: 'change' }],
}

function getList() {
  loading.value = true
  listLeaveRecord(queryParams.value).then(response => {
    leaveList.value = response.rows
    total.value = response.total
    loading.value = false
  })
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
  form.value = { elderName: '', elderId: undefined, checkInId: undefined, leaveType: '0', startTime: '', expectedReturnTime: '', leaveDays: 1, emergencyContact: '', emergencyPhone: '', reason: '' }
  open.value = true
  title.value = "新增请假"
}

function handleDetail(row: any) {
  getLeaveRecord(row.id).then(response => {
    detailData.value = response.data
    detailOpen.value = true
  })
}

function handleApprove(row: any) {
  proxy.$modal.confirm('确认审批通过"' + row.elderName + '"的请假申请？').then(() => {
    approveLeave(row.id).then(() => {
      proxy.$modal.msgSuccess("审批成功")
      getList()
    })
  }).catch(() => {})
}

function handleComplete(row: any) {
  proxy.$modal.confirm('确认"' + row.elderName + '"已销假返回？').then(() => {
    completeLeave(row.id).then(() => {
      proxy.$modal.msgSuccess("销假成功")
      getList()
    })
  }).catch(() => {})
}

function handleOverdue(row: any) {
  proxy.$modal.confirm('确认标记"' + row.elderName + '"超时未归？').then(() => {
    overdueLeave(row.id).then(() => {
      proxy.$modal.msgSuccess("已标记")
      getList()
    })
  }).catch(() => {})
}

function handleDelete(row: any) {
  proxy.$modal.confirm('确认删除"' + row.elderName + '"的请假记录？').then(() => {
    delLeaveRecord(row.id).then(() => {
      proxy.$modal.msgSuccess("删除成功")
      getList()
    })
  }).catch(() => {})
}

function cancel() {
  open.value = false
}

function submitForm() {
  proxy.$refs["leaveRef"].validate((valid: boolean) => {
    if (valid) {
      submitting.value = true
      addLeaveRecord(form.value).then(() => {
        proxy.$modal.msgSuccess("新增成功")
        open.value = false
        getList()
      }).finally(() => { submitting.value = false })
    }
  })
}

onMounted(() => {
  getList()
})
</script>
