<template>
  <div>
    <!-- 各护理等级人数 -->
    <el-row :gutter="16" style="margin-bottom:16px">
      <el-col :span="4" v-for="item in (stats.levelStats || [])" :key="item.levelId">
        <el-card shadow="never" :body-style="{padding:'12px',textAlign:'center',background:'#f5f7fa'}">
          <div style="font-size:24px;font-weight:bold;color:#409eff">{{ item.count }}</div>
          <div style="font-size:13px;color:#666;margin-top:4px">{{ item.name }}</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 房态床头牌展示 -->
    <el-table v-loading="loading" :data="bedTree" row-key="id" border default-expand-all
      :tree-props="{children:'children',hasChildren:'hasChildren'}">
      <el-table-column label="名称" min-width="200" prop="label" />
      <el-table-column label="类型" width="80" align="center">
        <template #default="s">
          <el-tag v-if="s.row.type==='floor'" type="primary" size="small">楼栋</el-tag>
          <el-tag v-else-if="s.row.type==='room'" type="success" size="small">房间</el-tag>
          <el-tag v-else-if="s.row.type==='bed'" :type="s.row.status==='0'?'success':s.row.status==='1'?'danger':'info'" size="small">床位</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template #default="s">
          <el-tag v-if="s.row.type==='bed'" :type="s.row.status==='0'?'success':s.row.status==='1'?'danger':'info'" size="small">
            {{ {0:'空闲',1:'已入住',2:'维修'}[s.row.status]||'--' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="价格(元/月)" width="120" align="center">
        <template #default="s"><span v-if="s.row.type==='bed'">{{ s.row.price }}</span></template>
      </el-table-column>
      <el-table-column label="入住老人" min-width="120" align="center">
        <template #default="s">
          <span v-if="s.row.type==='bed' && s.row.elderName" style="color:#409eff">{{ s.row.elderName }}</span>
          <span v-else-if="s.row.type==='bed' && s.row.status==='0'" style="color:#999">--</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { getFloorTree } from "@/api/nursing/checkin"

const props = defineProps<{ stats: any }>()
const loading = ref(false)
const bedTree = ref<any[]>([])

function loadData() {
  loading.value = true
  getFloorTree().then(res => {
    bedTree.value = res.data || []
    loading.value = false
  })
}

onMounted(() => loadData())
</script>
