<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-card shadow="never" style="margin-bottom:16px">
      <el-form :model="queryParams" ref="queryRef" :inline="true" label-width="90px">
        <el-form-item label="老人姓名" prop="elderName">
          <el-input v-model="queryParams.elderName" placeholder="模糊搜索" clearable @keyup.enter="handleQuery" style="width:160px" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCardNo">
          <el-input v-model="queryParams.idCardNo" placeholder="精准搜索" clearable @keyup.enter="handleQuery" style="width:180px" />
        </el-form-item>
        <el-form-item label="入住情况" prop="admissionStatus">
          <el-select v-model="queryParams.admissionStatus" placeholder="全部" clearable style="width:120px">
            <el-option label="已入住" value="已入住" />
            <el-option label="未入住" value="未入住" />
          </el-select>
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
        <el-button type="primary" @click="handleUpload">
          <el-icon style="vertical-align:-2px;margin-right:4px"><upload-filled /></el-icon>上传体检报告
        </el-button>
      </el-col>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list" stripe border>
      <el-table-column label="序号" type="index" width="60" align="center" />
      <el-table-column label="老人姓名" prop="elderName" width="110" />
      <el-table-column label="身份证号" prop="idCardNo" width="180" />
      <el-table-column label="健康评分" prop="healthScore" width="110" align="center">
        <template #default="s">
          <span :style="{color: s.row.healthScore >= 80 ? '#67c23a' : s.row.healthScore >= 60 ? '#e6a23c' : '#f56c6c', fontWeight:'bold'}">
            {{ s.row.healthScore != null ? Number(s.row.healthScore).toFixed(1) + ' 分' : '--' }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="建议入住" prop="suggestAdmission" width="100" align="center">
        <template #default="s">
          <el-tag :type="s.row.suggestAdmission==='建议入住'?'success':'info'" size="small">{{ s.row.suggestAdmission||'--' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="推荐等级" prop="recommendNursingLevel" width="120" align="center" />
      <el-table-column label="入住情况" prop="admissionStatus" width="100" align="center">
        <template #default="s">
          <el-tag :type="s.row.admissionStatus==='已入住'?'success':'warning'" size="small">{{ s.row.admissionStatus||'--' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="总检日期" prop="examinationDate" width="110" align="center" />
      <el-table-column label="评估时间" prop="assessmentTime" width="160" align="center" />
      <el-table-column label="操作" width="100" align="center" fixed="right">
        <template #default="s">
          <el-button link type="primary" @click="handleView(s.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 上传体检报告弹窗 -->
    <el-dialog title="上传体检报告" v-model="uploadOpen" width="520px" append-to-body :close-on-click-modal="false" destroy-on-close>
      <el-form ref="uploadRef" :model="uploadForm" :rules="uploadRules" label-width="100px">
        <el-form-item label="老人姓名" prop="elderName">
          <el-input v-model="uploadForm.elderName" placeholder="请输入老人姓名" />
        </el-form-item>
        <el-form-item label="老人ID" prop="elderId">
          <el-input-number v-model="uploadForm.elderId" :min="1" style="width:100%" />
        </el-form-item>
        <el-form-item label="报告标题" prop="reportTitle">
          <el-input v-model="uploadForm.reportTitle" placeholder="如：张三-2026年6月体检报告" />
        </el-form-item>
        <el-form-item label="体检日期" prop="assessmentDate">
          <el-date-picker v-model="uploadForm.assessmentDate" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="报告文件">
          <el-upload drag :limit="1" :auto-upload="false">
            <el-icon class="el-icon--upload" style="font-size:40px"><upload-filled /></el-icon>
            <div class="el-upload__text">拖拽或 <em>点击上传</em></div>
            <template #tip><div class="el-upload__tip" style="color:#999">支持 PDF/图片，不超过 20MB</div></template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="uploadOpen = false">取消</el-button>
        <el-button type="primary" @click="submitUpload" :loading="uploadLoading">
          {{ uploadLoading ? 'AI分析中...' : '确定' }}
        </el-button>
      </template>
    </el-dialog>

    <!-- 评估详情弹窗 -->
    <el-dialog title="健康评估详情" v-model="detailOpen" width="750px" append-to-body>
      <template v-if="detailData">
        <el-descriptions :column="3" border>
          <el-descriptions-item label="老人姓名" :span="1">{{ detailData.elderName }}</el-descriptions-item>
          <el-descriptions-item label="身份证号" :span="2">{{ detailData.idCardNo }}</el-descriptions-item>

          <el-descriptions-item label="健康评分">
            <span :style="{color: detailData.healthScore>=80?'#67c23a':detailData.healthScore>=60?'#e6a23c':'#f56c6c',fontWeight:'bold',fontSize:'16px'}">
              {{ detailData.healthScore != null ? Number(detailData.healthScore).toFixed(1) + ' 分' : '--' }}
            </span>
          </el-descriptions-item>
          <el-descriptions-item label="建议入住">
            <el-tag :type="detailData.suggestAdmission==='建议入住'?'success':'info'" size="small">{{ detailData.suggestAdmission||'--' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="推荐等级">{{ detailData.recommendNursingLevel||'--' }}</el-descriptions-item>

          <el-descriptions-item label="入住情况">
            <el-tag :type="detailData.admissionStatus==='已入住'?'success':'warning'" size="small">{{ detailData.admissionStatus||'--' }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="总检日期">{{ detailData.examinationDate||'--' }}</el-descriptions-item>
          <el-descriptions-item label="评估时间">{{ detailData.assessmentTime||'--' }}</el-descriptions-item>

          <el-descriptions-item label="报告标题" :span="3">{{ detailData.reportTitle||'--' }}</el-descriptions-item>

          <el-descriptions-item label="评估日期">{{ detailData.assessmentDate||'--' }}</el-descriptions-item>
          <el-descriptions-item label="状态" :span="2">
            <el-tag :type="detailData.status==='1'?'success':'warning'" size="small">{{ detailData.status==='1'?'已完成':'待分析' }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>

        <el-divider />
        <div style="font-size:16px;font-weight:bold;margin-bottom:12px">📋 AI分析结果</div>
        <div v-if="detailData.assessmentResult">
          <el-alert :title="'结论：' + (resultData.conclusion||'')" type="success" :closable="false" show-icon style="margin-bottom:12px" />
          <el-table :data="resultData.details || []" border size="small" style="width:100%">
            <el-table-column label="检查项目" prop="item" min-width="140" />
            <el-table-column label="测量值" prop="value" min-width="140" />
            <el-table-column label="结果" prop="result" width="100" align="center">
              <template #default="s">
                <el-tag :type="s.row.result==='正常'?'success':'danger'" size="small">{{ s.row.result }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-empty v-else description="暂无AI分析结果" :image-size="80" />
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { listHealth, getHealth, addHealth } from "@/api/nursing/health"

const { proxy } = getCurrentInstance()

const list = ref<any[]>([])
const total = ref(0)
const loading = ref(true)
const showSearch = ref(true)
const uploadOpen = ref(false)
const uploadLoading = ref(false)
const detailOpen = ref(false)
const detailData = ref<any>(null)

const resultData = computed(() => {
  if (!detailData.value?.assessmentResult) return { conclusion: '', details: [] }
  try { return JSON.parse(detailData.value.assessmentResult) }
  catch { return { conclusion: detailData.value.assessmentResult, details: [] } }
})

const queryParams = ref({
  pageNum: 1, pageSize: 10,
  elderName: undefined,
  idCardNo: undefined,
  admissionStatus: undefined,
})

const uploadForm = ref({
  elderName: '', elderId: undefined,
  reportTitle: '', assessmentDate: '', reportFile: '',
})

const uploadRules = {
  elderName: [{ required: true, message: '请输入老人姓名', trigger: 'blur' }],
  elderId: [{ required: true, message: '请输入老人ID', trigger: 'blur' }],
  reportTitle: [{ required: true, message: '请输入报告标题', trigger: 'blur' }],
  assessmentDate: [{ required: true, message: '请选择体检日期', trigger: 'change' }],
}

function getList() {
  loading.value = true
  listHealth(queryParams.value).then(res => {
    list.value = res.rows; total.value = res.total; loading.value = false
  })
}

function handleQuery() { queryParams.value.pageNum = 1; getList() }

function resetQuery() {
  queryParams.value = { pageNum: 1, pageSize: 10, elderName: undefined, idCardNo: undefined, admissionStatus: undefined }
  handleQuery()
}

function handleUpload() {
  uploadForm.value = { elderName: '', elderId: undefined, reportTitle: '', assessmentDate: '', reportFile: '' }
  uploadOpen.value = true
}

function submitUpload() {
  proxy.$refs.uploadRef.validate((valid: boolean) => {
    if (!valid) return
    uploadLoading.value = true
    setTimeout(() => {
      addHealth(uploadForm.value).then(res => {
        proxy.$modal.msgSuccess('上传成功，AI 分析中...')
        uploadOpen.value = false
        const newId = res.data || res
        getHealth(typeof newId === 'object' ? newId.id : newId).then(detailRes => {
          detailData.value = detailRes.data
          detailOpen.value = true
        })
        getList()
      }).finally(() => { uploadLoading.value = false })
    }, 1500)
  })
}

function handleView(row: any) {
  getHealth(row.id).then(res => { detailData.value = res.data; detailOpen.value = true })
}

onMounted(() => getList())
</script>
