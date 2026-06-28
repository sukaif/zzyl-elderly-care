<template>
  <div class="app-container">
    <el-card shadow="never">
      <template #header><b>合同跟踪</b></template>
      <el-table v-loading="loading" :data="list" stripe>
        <el-table-column label="合同编号" prop="contractNo" width="200" />
        <el-table-column label="老人姓名" prop="elderName" width="120" />
        <el-table-column label="签约日期" prop="signDate" width="120" />
        <el-table-column label="合同期限" min-width="200">
          <template #default="s">{{ s.row.startDate }} ~ {{ s.row.endDate }}</template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="100">
          <template #default="s">
            <el-tag :type="s.row.status==='1'?'success':s.row.status==='0'?'warning':'info'">
              {{ {'0':'待签署','1':'已签署','2':'已到期'}[s.row.status]||'--' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="total>0" :total="total" v-model:page="query.pageNum" v-model:limit="query.pageSize" @pagination="loadData" />
    </el-card>
  </div>
</template>
<script setup lang="ts">
import request from '@/utils/request'
const list = ref<any[]>([])
const total = ref(0)
const loading = ref(false)
const query = ref({ pageNum: 1, pageSize: 10 })
function loadData() {
  loading.value = true
  request({ url: '/nursing/checkin/list', method: 'get', params: { ...query.value, status: '1' } }).then((res: any) => {
    // 合同数据暂从入住列表获取，后续可扩展专用合同接口
    list.value = (res.rows || []).map((r: any) => ({ ...r, contractNo: r.id ? 'HT' + String(r.id).padStart(6,'0') : '--' }))
    total.value = res.total
    loading.value = false
  })
}
onMounted(() => loadData())
</script>
