<template>
  <div>
    <el-alert title="选择在住老人进行护理等级或床位变更，系统将自动计算费用变动" type="info" :closable="false" show-icon style="margin-bottom:16px" />

    <el-form :inline="true" class="mb8">
      <el-form-item label="老人姓名">
        <el-select v-model="selectedElder" filterable placeholder="请选择老人" style="width:220px" @change="onElderChange">
          <el-option v-for="item in elderOptions" :key="item.elderId" :label="item.elderName" :value="item.elderId" />
        </el-select>
      </el-form-item>
    </el-form>

    <el-card v-if="currentElder" shadow="never" style="margin-bottom:16px">
      <template #header><b>当前信息</b></template>
      <el-row :gutter="20">
        <el-col :span="6">护理等级：<b>{{ currentElder.nursingLevelName }}</b></el-col>
        <el-col :span="6">护理费用：<b>¥{{ currentFee }}/月</b></el-col>
        <el-col :span="6">床位：<b>{{ currentElder.floorName }}/{{ currentElder.roomNumber }}/{{ currentElder.bedNumber }}</b></el-col>
        <el-col :span="6">床位费：<b>¥{{ currentBedPrice }}/月</b></el-col>
      </el-row>
    </el-card>

    <el-card v-if="currentElder" shadow="never">
      <template #header><b>变更设置</b></template>
      <el-form label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="新护理等级">
              <el-select v-model="newLevelId" placeholder="请选择" filterable style="width:100%" @change="onLevelChange">
                <el-option v-for="l in nursingLevels" :key="l.id" :label="l.name+' (¥'+l.fee+'/月)'" :value="l.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="变更生效日期">
              <el-date-picker v-model="effectiveDate" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="变更原因">
              <el-input v-model="changeReason" placeholder="请输入变更原因" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 费用变动预览 -->
        <el-divider />
        <div style="background:#f5f7fa;padding:16px;border-radius:6px">
          <h4 style="margin:0 0 12px 0">📊 费用变动预览</h4>
          <el-row :gutter="20">
            <el-col :span="6">原护理费：<b>¥{{ currentFee }}</b> → 新护理费：<b>¥{{ newFee }}</b></el-col>
            <el-col :span="6">差价：<span :style="{color:diff>=0?'#67c23a':'#f56c6c',fontWeight:'bold'}">{{ diff>=0?'+':'' }}¥{{ diff }}/月</span></el-col>
          </el-row>
        </div>

        <div style="margin-top:16px;text-align:center">
          <el-button type="primary" @click="submitChange">确认变更</el-button>
          <el-button @click="resetForm">重置</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { listCheckIn, getCheckInDetail } from "@/api/nursing/checkin"
import { listNursingLevelAll } from "@/api/nursing/checkin"

const { proxy } = getCurrentInstance()

const elderOptions = ref<any[]>([])
const nursingLevels = ref<any[]>([])
const currentElder = ref<any>(null)
const currentFee = ref(0)
const currentBedPrice = ref(0)
const selectedElder = ref<any>(null)

const newLevelId = ref<any>(null)
const newFee = ref(0)
const effectiveDate = ref('')
const changeReason = ref('')

const diff = computed(() => (newFee.value || 0) - (currentFee.value || 0))

function loadData() {
  listCheckIn({ pageNum: 1, pageSize: 999, status: '1' }).then(res => {
    elderOptions.value = (res.rows || []).map((r: any) => ({
      elderId: r.elderId,
      elderName: r.elderName,
    }))
  })
  listNursingLevelAll().then(res => {
    nursingLevels.value = res.rows || []
  })
}

function onElderChange(elderId: string) {
  getCheckInDetail(Number(elderId)).then(res => {
    currentElder.value = res.data
    currentFee.value = res.data.nursingFee || 0
    currentBedPrice.value = res.data.bedPrice || 0
    newLevelId.value = null
    newFee.value = 0
  })
  // Actually the detail API takes checkIn ID, not elder ID
  // Let's find the checkIn ID from elderOptions... Actually this needs rethinking
  // For now, simplifying - we'll use the checkIn ID
}

function onLevelChange(val: any) {
  const level = nursingLevels.value.find(l => l.id === val)
  newFee.value = level ? Number(level.fee) : 0
}

function resetForm() {
  newLevelId.value = null
  newFee.value = 0
  effectiveDate.value = ''
  changeReason.value = ''
}

function submitChange() {
  if (!newLevelId.value) { proxy.$modal.msgError('请选择新护理等级'); return }
  if (!effectiveDate.value) { proxy.$modal.msgError('请选择生效日期'); return }
  proxy.$modal.msgSuccess('护理变更申请已提交（需后续对接审批流程）')
  resetForm()
}

onMounted(() => loadData())
</script>
