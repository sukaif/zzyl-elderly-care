<template>
  <div>
    <!-- 退住申请表单（原型图风格） -->
    <el-card shadow="never" style="margin-bottom:16px">
      <template #header><b>退住申请</b></template>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="老人姓名" prop="elderName"><el-input v-model="form.elderName" placeholder="输入后自动匹配入住信息" @blur="onElderBlur" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="入住ID" prop="checkInId"><el-input-number v-model="form.checkInId" :min="1" style="width:100%" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="老人ID" prop="elderId"><el-input-number v-model="form.elderId" :min="1" style="width:100%" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="退住类型" prop="checkoutType"><el-select v-model="form.checkoutType" style="width:100%"><el-option label="个人原因" value="0" /><el-option label="病情转院" value="1" /><el-option label="去世" value="2" /><el-option label="其他" value="3" /></el-select></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="申请退住日期" prop="checkoutDate"><el-date-picker v-model="form.checkoutDate" type="date" style="width:100%" value-format="YYYY-MM-DD" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="实际离院日期"><el-date-picker v-model="form.actualLeaveDate" type="date" style="width:100%" value-format="YYYY-MM-DD" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="退住原因" prop="reason"><el-input v-model="form.reason" type="textarea" :rows="2" /></el-form-item>
      </el-form>
    </el-card>

    <!-- 费用结算清单（原型图风格） -->
    <el-card v-if="showSettlement" shadow="never" style="margin-bottom:16px">
      <template #header><b>💰 费用结算清单</b></template>
      <el-row :gutter="40">
        <el-col :span="8">
          <h4 style="color:#409eff">已缴费用</h4>
          <div>押金：<b>¥{{ settlementData.deposit||0 }}</b></div>
          <div>预存款：<b>¥{{ settlementData.prePay||0 }}</b></div>
          <div style="margin-top:8px;font-size:16px">合计：<b>¥{{ settlementData.totalCharged||0 }}</b></div>
        </el-col>
        <el-col :span="8">
          <h4 style="color:#f56c6c">应扣费用</h4>
          <div>本月护理费：<b>¥{{ settlementData.nursingFee||0 }}</b></div>
          <div>本月床位费：<b>¥{{ settlementData.bedPrice||0 }}</b></div>
          <div>其他费用：<b>¥{{ settlementData.otherFee||0 }}</b></div>
          <div style="margin-top:8px;font-size:16px">合计：<b>¥{{ settlementData.totalDeducted||0 }}</b></div>
        </el-col>
        <el-col :span="8">
          <h4 style="color:#67c23a">结算结果</h4>
          <div v-if="settlementData.refundAmount>0" style="color:#67c23a;font-size:18px;font-weight:bold">✅ 应退款：¥{{ settlementData.refundAmount }}</div>
          <div v-else-if="settlementData.arrears>0" style="color:#f56c6c;font-size:18px;font-weight:bold">⚠️ 需补缴：¥{{ settlementData.arrears }}</div>
          <div v-else style="color:#909399">收支平衡</div>
        </el-col>
      </el-row>
    </el-card>

    <div style="text-align:center">
      <el-button type="primary" @click="calcSettlement" :loading="calcLoading">📊 预览费用</el-button>
      <el-button type="success" @click="submitForm" :loading="submitting">✅ 确认退住</el-button>
    </div>

    <el-divider />
    <h4>退住记录</h4>
    <el-table v-loading="listLoading" :data="checkoutList" stripe>
      <el-table-column label="序号" type="index" width="60" align="center" />
      <el-table-column label="老人姓名" prop="elderName" width="100" />
      <el-table-column label="退住类型" width="90"><template #default="s">{{ {0:'个人原因',1:'病情转院',2:'去世',3:'其他'}[s.row.checkoutType] }}</template></el-table-column>
      <el-table-column label="申请日期" prop="checkoutDate" width="100" />
      <el-table-column label="应退金额" width="100"><template #default="s">¥{{ s.row.refundAmount||0 }}</template></el-table-column>
      <el-table-column label="状态" width="90">
        <template #default="s"><el-tag :type="{0:'warning',1:'primary',2:'success'}[s.row.status]" size="small">{{ {0:'待结算',1:'已结算',2:'已完成'}[s.row.status] }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="s"><el-button link type="primary" @click="showDetail(s.row)">详情</el-button></template>
      </el-table-column>
    </el-table>
    <pagination v-show="listTotal>0" :total="listTotal" v-model:page="listQuery.pageNum" v-model:limit="listQuery.pageSize" @pagination="loadCheckoutList" />
  </div>
</template>

<script setup lang="ts">
import { listCheckOut, addCheckOut, calcSettlement } from "@/api/nursing/checkout"

const { proxy } = getCurrentInstance()
const formRef = ref()
const form = ref({ elderName:'', checkInId:undefined, elderId:undefined, checkoutType:'0', checkoutDate:'', actualLeaveDate:'', reason:'' })
const rules = { elderName:[{required:true}], checkInId:[{required:true}], elderId:[{required:true}], checkoutType:[{required:true}], checkoutDate:[{required:true}] }
const showSettlement = ref(false)
const calcLoading = ref(false)
const submitting = ref(false)
const settlementData = ref<any>({})

const checkoutList = ref<any[]>([])
const listLoading = ref(false)
const listTotal = ref(0)
const listQuery = ref({ pageNum:1, pageSize:10 })

function onElderBlur() { /* 可根据姓名自动查询入住信息 */ }
function calcSettlement() {
  if (!form.value.checkInId) { proxy.$modal.msgError('请输入入住ID'); return }
  if (!form.value.checkoutDate) { proxy.$modal.msgError('请选择退住日期'); return }
  calcLoading.value = true
  calcSettlement(form.value.checkInId, form.value.checkoutDate).then(res => {
    if (res.code === 200) {
      settlementData.value = res.data
      showSettlement.value = true
    } else {
      proxy.$modal.msgError(res.msg)
    }
  }).finally(() => calcLoading.value = false)
}
function submitForm() {
  proxy.$refs.formRef.validate((v:boolean)=>{
    if(v) {
      if (!showSettlement.value) { proxy.$modal.msgError('请先预览费用'); return }
      submitting.value = true
      addCheckOut(form.value).then(() => {
        proxy.$modal.msgSuccess('退住申请已提交')
        showSettlement.value = false
        form.value = { elderName:'', checkInId:undefined, elderId:undefined, checkoutType:'0', checkoutDate:'', actualLeaveDate:'', reason:'' }
        loadCheckoutList()
      }).finally(() => submitting.value = false)
    }
  })
}
function showDetail(row:any) { proxy.$modal.msgAlert('退住详情：'+row.elderName+' 应退¥'+(row.refundAmount||0)) }
function loadCheckoutList() { listLoading.value=true; listCheckOut(listQuery.value).then(r=>{checkoutList.value=r.rows;listTotal.value=r.total;listLoading.value=false}) }
onMounted(()=>loadCheckoutList())
</script>
