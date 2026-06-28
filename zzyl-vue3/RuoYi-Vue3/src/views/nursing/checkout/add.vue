<template>
  <div class="app-container">
    <!-- 1 基本信息 -->
    <el-card shadow="never" style="margin-bottom:16px">
      <template #header><b>1 基本信息</b></template>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="*老人姓名" prop="elderId">
              <el-select v-model="form.elderId" filterable placeholder="请选择老人" style="width:100%" @change="onElderChange">
                <el-option v-for="e in elderOptions" :key="e.elderId" :label="e.elderName" :value="e.elderId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="老人身份证号">
              <el-input :model-value="form.idCardNo||'--'" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系方式">
              <el-input :model-value="form.phone||'--'" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="家庭住址"><el-input :model-value="form.address||'--'" disabled /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="入住期限"><el-input :model-value="form.checkInDateRange||'--'" disabled /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="费用期限"><el-input :model-value="form.feeDateRange||'--'" disabled /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="护理等级"><el-input :model-value="form.nursingLevelName||'--'" disabled /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="入住床位"><el-input :model-value="form.bedLabel||'--'" disabled /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="退住日期" prop="checkoutDate"><el-date-picker v-model="form.checkoutDate" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="退住原因" prop="checkoutType"><el-select v-model="form.checkoutType" placeholder="请选择" style="width:100%"><el-option v-for="opt in reasonOptions" :key="opt.value" :label="opt.label" :value="opt.value" /></el-select></el-form-item></el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 2 解除协议 -->
    <el-card shadow="never" style="margin-bottom:16px">
      <template #header><b>2 解除协议</b></template>
      <el-form label-width="120px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="*解除日期">
              <el-date-picker v-model="form.terminationDate" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="*解除协议">
              <el-upload :limit="1" :auto-upload="false" accept=".pdf">
                <div class="upload-file"><el-icon><Upload /></el-icon><span>上传文件</span></div>
                <template #tip><div style="color:#999;font-size:12px">请上传pdf文件，大小在60M以内</div></template>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 3 费用清算 -->
    <el-card shadow="never" style="margin-bottom:16px">
      <template #header><b>3 费用清算</b></template>
      <el-row :gutter="16" style="margin-bottom:16px">
        <el-col :span="6"><div style="background:#f0f9eb;padding:14px;border-radius:8px"><div style="color:#67c23a;font-weight:bold;margin-bottom:6px">应退</div><div style="font-size:12px;color:#666">月度账单 2000.00元</div><div style="font-size:12px;color:#666">费用账单 20.00元</div><div style="margin-top:6px;font-weight:bold;color:#67c23a">小计：2020.00 元</div><el-tag size="small" type="success" style="margin-top:4px">待办：3</el-tag></div></el-col>
        <el-col :span="6"><div style="background:#fdf0ef;padding:14px;border-radius:8px"><div style="color:#f56c6c;font-weight:bold;margin-bottom:6px">欠费</div><div style="font-size:12px;color:#666">月度账单 2000.00元</div><div style="font-size:12px;color:#666">费用账单 20.00元</div><div style="margin-top:6px;font-weight:bold;color:#f56c6c">小计：6000.00 元</div><el-tag size="small" type="danger" style="margin-top:4px">待办：3</el-tag></div></el-col>
        <el-col :span="6"><div style="background:#eef5fc;padding:14px;border-radius:8px"><div style="color:#409eff;font-weight:bold;margin-bottom:6px">余额</div><div style="font-size:12px;color:#666">押金：2000.00元</div><div style="font-size:12px;color:#666">预缴款：20.00元</div><div style="margin-top:6px;font-weight:bold;color:#409eff">小计：2020.00 元</div></div></el-col>
        <el-col :span="6"><div style="background:#fdf6ec;padding:14px;border-radius:8px"><div style="color:#e6a23c;font-weight:bold;margin-bottom:6px">未缴</div><div style="font-size:12px;color:#666">月度账单 2000.00元</div><div style="font-size:12px;color:#666">费用账单 20.00元</div><div style="margin-top:6px;font-weight:bold;color:#e6a23c">小计：6000.00 元</div><el-tag size="small" type="warning" style="margin-top:4px">待办：3</el-tag></div></el-col>
      </el-row>
      <div style="background:#f5f7fa;padding:14px;border-radius:8px;text-align:center">
        <span style="font-size:15px;font-weight:bold">最终退款金额 = 应退 - 欠费 + 余额 = <span style="color:#f56c6c;font-size:18px">-6000.00 元</span></span>
        <div style="margin-top:6px"><el-button type="primary" link>上传充值凭证</el-button></div>
      </div>
    </el-card>

    <div style="text-align:center;margin-bottom:30px">
      <el-button type="primary" size="large" :loading="submitting" @click="submitForm">提交</el-button>
      <el-button size="large" @click="goBack">返回</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Upload } from '@element-plus/icons-vue'
import { useRouter } from "vue-router"
import { listCheckIn, getCheckInDetail } from "@/api/nursing/checkin"
import { addCheckOut } from "@/api/nursing/checkout"

const router = useRouter()
const { proxy } = getCurrentInstance()
const submitting = ref(false)
const elderOptions = ref<any[]>([])

const reasonOptions = [
  { label: '老人个人原因', value: '0' }, { label: '护理质量不高', value: 'a' },
  { label: '服务不周', value: 'b' }, { label: '管理混乱', value: 'c' },
  { label: '费用高昂', value: 'd' }, { label: '违规退住', value: 'e' }, { label: '其他', value: '3' },
]

const form = ref({
  elderId: undefined, elderName: '', idCardNo: '', phone: '', address: '',
  checkInDateRange: '', feeDateRange: '', nursingLevelName: '', bedLabel: '',
  checkInId: undefined,
  checkoutDate: '', checkoutType: '',
  terminationDate: '2048-10-10',
  reason: '',
})

const rules: any = {
  elderId: [{ required: true, message: '请选择老人', trigger: 'change' }],
  checkoutDate: [{ required: true, message: '请选择退住日期', trigger: 'change' }],
  checkoutType: [{ required: true, message: '请选择退住原因', trigger: 'change' }],
}

function loadElderOptions() {
  listCheckIn({ pageNum: 1, pageSize: 999, status: '1' }).then(res => {
    elderOptions.value = (res.rows || []).map((r: any) => ({
      elderId: r.elderId, elderName: r.elderName,
      idCardNo: r.idCardNo, phone: r.phone,
      checkInId: r.id, nursingLevelName: r.nursingLevelName,
      bedNumber: r.bedNumber, checkInDate: r.checkInDate,
    }))
  }).catch(() => { proxy.$modal.msgError('加载老人列表失败') })
}

function onElderChange(val: number) {
  const elder = elderOptions.value.find(e => e.elderId === val)
  if (elder) {
    form.value.elderName = elder.elderName
    form.value.idCardNo = elder.idCardNo || '--'
    form.value.phone = elder.phone || '--'
    form.value.checkInId = elder.checkInId
    form.value.nursingLevelName = elder.nursingLevelName || '--'
    form.value.bedLabel = elder.bedNumber || '--'
    form.value.checkInDateRange = elder.checkInDate || '--'
    // 查详情获取地址等信息
    if (elder.checkInId) {
      getCheckInDetail(elder.checkInId).then(res => {
        const d = res.data
        if (d) {
          form.value.address = d.address || '--'
          form.value.idCardNo = d.idCardNo || form.value.idCardNo
          form.value.phone = d.phone || form.value.phone
          form.value.nursingLevelName = d.nursingLevelName || form.value.nursingLevelName
          form.value.bedLabel = [d.floorName, d.roomNumber, d.bedNumber].filter(Boolean).join('/') || form.value.bedLabel
          if (d.contract) {
            form.value.feeDateRange = (d.contract.startDate||'') + ' ~ ' + (d.contract.endDate||'')
          }
        }
      })
    }
  }
}

function submitForm() {
  proxy.$refs.formRef.validate((valid: boolean) => {
    if (!valid) return
    submitting.value = true
    addCheckOut({
      elderName: form.value.elderName, elderId: form.value.elderId,
      checkInId: form.value.checkInId, checkoutDate: form.value.checkoutDate,
      checkoutType: form.value.checkoutType, actualLeaveDate: form.value.terminationDate,
      reason: form.value.checkoutType ? (reasonOptions.find(r => r.value === form.value.checkoutType)?.label || '') : '',
    }).then(() => {
      proxy.$modal.msgSuccess('退住申请提交成功')
      router.push('/nursing/checkout')
    }).catch(() => { submitting.value = false })
  })
}

function goBack() { router.push('/nursing/checkout') }
onMounted(() => { loadElderOptions() })
</script>

<style scoped>
.upload-file {
  display: inline-flex; align-items: center; gap: 6px;
  padding: 8px 16px; background: #ecf5ff; border: 1px solid #b3d8ff; border-radius: 4px;
  color: #409EFF; cursor: pointer;
}
.upload-file:hover { background: #dbeafe; }
</style>
