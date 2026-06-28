<template>
  <div>
    <el-alert title="对在住老人的收费项目进行折扣调整" type="info" :closable="false" show-icon style="margin-bottom:16px" />

    <el-form :inline="true" class="mb8">
      <el-form-item label="老人姓名">
        <el-select v-model="selectedElder" filterable placeholder="请选择老人" style="width:220px">
          <el-option v-for="item in elderOptions" :key="item.elderId" :label="item.elderName" :value="item.elderId" />
        </el-select>
      </el-form-item>
    </el-form>

    <el-card v-if="selectedElder" shadow="never">
      <template #header><b>折扣设置</b></template>
      <el-form label-width="140px">
        <el-form-item label="护理费折扣">
          <el-slider v-model="nursingDiscount" :min="0" :max="100" :format-tooltip="(v)=>v+'%'" style="width:300px;display:inline-block" />
          <span style="margin-left:12px;color:#999">{{ nursingDiscount }}%（原价¥{{ baseNursingFee }}）</span>
        </el-form-item>
        <el-form-item label="床位费折扣">
          <el-slider v-model="bedDiscount" :min="0" :max="100" :format-tooltip="(v)=>v+'%'" style="width:300px;display:inline-block" />
          <span style="margin-left:12px;color:#999">{{ bedDiscount }}%（原价¥{{ baseBedPrice }}）</span>
        </el-form-item>
        <el-divider />
        <el-row :gutter="20">
          <el-col :span="6">原总费用：<b>¥{{ baseTotal }}</b></el-col>
          <el-col :span="6">折后总费用：<b style="color:#e6a23c">¥{{ discountedTotal }}</b></el-col>
          <el-col :span="6">月省：<b style="color:#67c23a">¥{{ baseTotal - discountedTotal }}</b></el-col>
        </el-row>
        <el-form-item label="折扣起止时间" style="margin-top:12px">
          <el-date-picker v-model="discountDateRange" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="折扣原因">
          <el-input v-model="discountReason" placeholder="请输入折扣原因" style="width:400px" />
        </el-form-item>
        <div style="text-align:center">
          <el-button type="primary">确认设置</el-button>
          <el-button>重置</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { listCheckIn } from "@/api/nursing/checkin"

const elderOptions = ref<any[]>([])
const selectedElder = ref<any>(null)
const baseNursingFee = ref(1500)
const baseBedPrice = ref(2000)
const nursingDiscount = ref(100)
const bedDiscount = ref(100)
const discountDateRange = ref([])
const discountReason = ref('')

const baseTotal = computed(() => baseNursingFee.value + baseBedPrice.value)
const discountedTotal = computed(() =>
  Math.round(baseNursingFee.value * nursingDiscount.value / 100 * 100) / 100 +
  Math.round(baseBedPrice.value * bedDiscount.value / 100 * 100) / 100
)

onMounted(() => {
  listCheckIn({ pageNum: 1, pageSize: 999, status: '1' }).then(res => {
    elderOptions.value = (res.rows || []).map((r: any) => ({
      elderId: r.elderId,
      elderName: r.elderName,
    }))
  })
})
</script>
