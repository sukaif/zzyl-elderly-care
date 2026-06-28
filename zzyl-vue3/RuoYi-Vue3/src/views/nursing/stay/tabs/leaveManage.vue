<template>
  <div>
    <el-form :inline="true" class="mb8">
      <el-form-item label="老人姓名"><el-input v-model="query.elderName" placeholder="请输入" clearable @keyup.enter="loadData" /></el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.status" placeholder="全部" clearable @change="loadData" style="width:120px">
          <el-option label="待审批" value="0" /><el-option label="已审批" value="1" />
          <el-option label="已销假" value="2" /><el-option label="超时未归" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">搜索</el-button><el-button @click="showAdd=true;addForm={}">新增请假</el-button></el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="序号" type="index" width="60" align="center" />
      <el-table-column label="老人" prop="elderName" width="100" />
      <el-table-column label="类型" width="90"><template #default="s">{{ {0:'外出探亲',1:'就医',2:'回家',3:'其他'}[s.row.leaveType] }}</template></el-table-column>
      <el-table-column label="开始时间" prop="startTime" width="150" />
      <el-table-column label="预计返回" prop="expectedReturnTime" width="150" />
      <el-table-column label="天数" prop="leaveDays" width="50" align="center" />
      <el-table-column label="联系人" prop="emergencyContact" width="90" />
      <el-table-column label="状态" width="100" align="center">
        <template #default="s">
          <el-tag :type="{0:'warning',1:'primary',2:'success',3:'danger'}[s.row.status]||'info'" size="small">
            {{ {0:'待审批',1:'已审批',2:'已销假',3:'超时未归'}[s.row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="260" align="center">
        <template #default="s">
          <el-button link type="primary" @click="showDetail(s.row)">详情</el-button>
          <el-button link type="success" @click="handleApprove(s.row)" v-if="s.row.status==='0'">审批</el-button>
          <el-button link type="primary" @click="handleComplete(s.row)" v-if="s.row.status==='1'">销假</el-button>
          <el-button link type="danger" @click="handleOverdue(s.row)" v-if="s.row.status==='1'">超时</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" v-model:page="query.pageNum" v-model:limit="query.pageSize" @pagination="loadData" />

    <!-- 新增弹窗 -->
    <el-dialog title="新增请假" v-model="showAdd" width="600px" append-to-body>
      <el-form ref="formRef" :model="addForm" :rules="rules" label-width="110px">
        <el-row><el-col :span="12"><el-form-item label="老人姓名" prop="elderName"><el-input v-model="addForm.elderName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="老人ID" prop="elderId"><el-input-number v-model="addForm.elderId" :min="1" style="width:100%" /></el-form-item></el-col></el-row>
        <el-row><el-col :span="12"><el-form-item label="入住ID" prop="checkInId"><el-input-number v-model="addForm.checkInId" :min="1" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="请假类型" prop="leaveType"><el-select v-model="addForm.leaveType" style="width:100%"><el-option label="外出探亲" value="0" /><el-option label="就医" value="1" /><el-option label="回家" value="2" /><el-option label="其他" value="3" /></el-select></el-form-item></el-col></el-row>
        <el-row><el-col :span="12"><el-form-item label="开始时间" prop="startTime"><el-date-picker v-model="addForm.startTime" type="datetime" style="width:100%" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="预计返回" prop="expectedReturnTime"><el-date-picker v-model="addForm.expectedReturnTime" type="datetime" style="width:100%" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item></el-col></el-row>
        <el-row><el-col :span="12"><el-form-item label="请假天数" prop="leaveDays"><el-input-number v-model="addForm.leaveDays" :min="1" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="紧急联系人"><el-input v-model="addForm.emergencyContact" /></el-form-item></el-col></el-row>
        <el-row><el-col :span="12"><el-form-item label="联系人电话"><el-input v-model="addForm.emergencyPhone" maxlength="11" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="请假事由"><el-input v-model="addForm.reason" type="textarea" :rows="2" /></el-form-item></el-col></el-row>
      </el-form>
      <template #footer><el-button type="primary" :loading="submitting" @click="submitForm">提交</el-button><el-button @click="showAdd=false">取消</el-button></template>
    </el-dialog>

    <!-- 详情弹窗 -->
    <el-dialog title="请假详情" v-model="showDetailDialog" width="600px" append-to-body>
      <el-descriptions :column="2" border v-if="detail">
        <el-descriptions-item label="老人">{{ detail.elderName }}</el-descriptions-item>
        <el-descriptions-item label="类型">{{ {0:'外出探亲',1:'就医',2:'回家',3:'其他'}[detail.leaveType] }}</el-descriptions-item>
        <el-descriptions-item label="开始">{{ detail.startTime }}</el-descriptions-item>
        <el-descriptions-item label="预计返回">{{ detail.expectedReturnTime }}</el-descriptions-item>
        <el-descriptions-item label="实际返回">{{ detail.actualReturnTime||'--' }}</el-descriptions-item>
        <el-descriptions-item label="天数">{{ detail.leaveDays }}天</el-descriptions-item>
        <el-descriptions-item label="联系人">{{ detail.emergencyContact }}/{{ detail.emergencyPhone }}</el-descriptions-item>
        <el-descriptions-item label="审批人">{{ detail.approver||'--' }}</el-descriptions-item>
        <el-descriptions-item label="状态" :span="2">
          <el-tag :type="{0:'warning',1:'primary',2:'success',3:'danger'}[detail.status]">{{ {0:'待审批',1:'已审批',2:'已销假',3:'超时未归'}[detail.status] }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="事由" :span="2">{{ detail.reason||'--' }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { listLeaveRecord, addLeaveRecord, approveLeave, completeLeave, overdueLeave } from "@/api/nursing/leave"
const { proxy } = getCurrentInstance()

const list = ref<any[]>([])
const total = ref(0)
const loading = ref(false)
const showAdd = ref(false)
const showDetailDialog = ref(false)
const submitting = ref(false)
const detail = ref<any>(null)
const query = ref({ pageNum: 1, pageSize: 10, elderName: '', status: undefined })
const addForm = ref<any>({})
const formRef = ref()
const rules = { elderName:[{required:true,message:'不能为空'}], elderId:[{required:true,message:'不能为空'}], checkInId:[{required:true,message:'不能为空'}], leaveType:[{required:true,message:'请选择'}], startTime:[{required:true,message:'请选择'}], expectedReturnTime:[{required:true,message:'请选择'}] }

function loadData() { loading.value=true; listLeaveRecord(query.value).then(r=>{list.value=r.rows;total.value=r.total;loading.value=false}) }
function showDetail(row:any) { detail.value=row; showDetailDialog.value=true }
function handleApprove(row:any) { proxy.$modal.confirm('确认审批通过？').then(()=>approveLeave(row.id).then(()=>{proxy.$modal.msgSuccess('已审批');loadData()})).catch(()=>{}) }
function handleComplete(row:any) { proxy.$modal.confirm('确认已销假？').then(()=>completeLeave(row.id).then(()=>{proxy.$modal.msgSuccess('已销假');loadData()})).catch(()=>{}) }
function handleOverdue(row:any) { proxy.$modal.confirm('确认超时未归？').then(()=>overdueLeave(row.id).then(()=>{proxy.$modal.msgSuccess('已标记');loadData()})).catch(()=>{}) }
function submitForm() { proxy.$refs.formRef.validate((v:boolean)=>{if(v){submitting.value=true;addLeaveRecord(addForm.value).then(()=>{proxy.$modal.msgSuccess('新增成功');showAdd.value=false;loadData()}).finally(()=>submitting.value=false)}}) }
onMounted(()=>loadData())
</script>
