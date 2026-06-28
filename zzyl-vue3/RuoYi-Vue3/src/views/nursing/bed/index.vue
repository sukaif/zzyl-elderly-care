<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="楼栋" prop="floorId">
        <el-select v-model="queryParams.floorId" placeholder="请选择楼栋" clearable @change="handleQuery">
          <el-option v-for="item in floorOptions" :key="item.id" :label="item.label" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="床位状态" clearable @change="handleQuery">
          <el-option label="空闲" value="0" />
          <el-option label="已入住" value="1" />
          <el-option label="维修" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="bedList" row-key="id" border default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
      <el-table-column label="名称" min-width="200" prop="label" />
      <el-table-column label="类型" align="center" width="100" prop="type">
        <template #default="scope">
          <el-tag v-if="scope.row.type === 'floor'" type="primary">楼栋</el-tag>
          <el-tag v-else-if="scope.row.type === 'room'" type="success">房间</el-tag>
          <el-tag v-else-if="scope.row.type === 'bed'" type="warning">床位</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="100" prop="status">
        <template #default="scope">
          <el-tag v-if="scope.row.type === 'bed'" :type="scope.row.status === '0' ? 'success' : scope.row.status === '1' ? 'danger' : 'info'">
            {{ scope.row.status === '0' ? '空闲' : scope.row.status === '1' ? '已入住' : '维修' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="价格(元/月)" align="center" width="120" prop="price">
        <template #default="scope">
          <span v-if="scope.row.type === 'bed'">{{ scope.row.price }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { getFloorTree } from "@/api/nursing/checkin"

const { proxy } = getCurrentInstance()

const bedList = ref<any[]>([])
const floorOptions = ref<any[]>([])
const loading = ref(true)
const showSearch = ref(true)

const queryParams = ref({
  floorId: undefined,
  status: undefined,
})

function getList() {
  loading.value = true
  getFloorTree(queryParams.value.status).then(response => {
    bedList.value = response.data || []
    loading.value = false
  })
}

function getFloorOptions() {
  getFloorTree().then(response => {
    floorOptions.value = (response.data || []).map((f: any) => ({
      id: f.id,
      label: f.label
    }))
  })
}

function handleQuery() {
  getList()
}

function resetQuery() {
  queryParams.value = { floorId: undefined, status: undefined }
  proxy.resetForm("queryRef")
  handleQuery()
}

onMounted(() => {
  getList()
  getFloorOptions()
})
</script>
