<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="等级名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入等级名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable>
          <el-option label="启用" value="1" />
          <el-option label="禁用" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" @click="handleAdd" v-hasPermi="['nursing:level:add']">新增</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="levelList">
      <el-table-column label="序号" align="center" type="index" width="60" />
      <el-table-column label="等级名称" align="center" prop="name" />
      <el-table-column label="护理计划" align="center" prop="planId">
        <template #default="scope">
          <span>{{ getPlanName(scope.row.planId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="护理费用" align="center" prop="fee" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="260">
        <template #default="scope">
          <el-button link type="primary" @click="handleUpdate(scope.row)" v-hasPermi="['nursing:level:edit']">修改</el-button>
          <el-button link type="primary" @click="handleDelete(scope.row)" v-hasPermi="['nursing:level:remove']">删除</el-button>
          <el-button link :type="scope.row.status === 1 ? 'danger' : 'primary'" @click="handleToggleStatus(scope.row)" v-hasPermi="['nursing:level:edit']">
            {{ scope.row.status === 1 ? '禁用' : '启用' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="levelRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="等级名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入等级名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="护理计划" prop="planId">
              <el-select v-model="form.planId" placeholder="请选择护理计划">
                <el-option v-for="item in planOptions" :key="item.id" :label="item.planName" :value="item.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="护理费用" prop="fee">
              <el-input v-model="form.fee" placeholder="请输入护理费用" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio label="1">启用</el-radio>
                <el-radio label="0">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="等级说明" prop="description">
              <el-input v-model="form.description" placeholder="请输入等级说明" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确定</el-button>
          <el-button @click="cancel">取消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="Level">
import type { NursingLevel, LevelQueryParams, NursingPlan } from "@/types/api/nursing"
import { listNursingLevel, getNursingLevel, addNursingLevel, updateNursingLevel, delNursingLevel, getEnablePlanList } from "@/api/nursing/level"

const { proxy } = getCurrentInstance()

const levelList = ref<NursingLevel[]>([])
const planOptions = ref<NursingPlan[]>([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref<number[]>([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {} as NursingLevel,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    status: undefined,
  } as LevelQueryParams,
  rules: {
    name: [{ required: true, message: "等级名称不能为空", trigger: "blur" }],
    planId: [{ required: true, message: "护理计划不能为空", trigger: "change" }],
    fee: [{ required: true, message: "护理费用不能为空", trigger: "blur" }],
    status: [{ required: true, message: "状态不能为空", trigger: "change" }],
    description: [{ required: true, message: "等级说明不能为空", trigger: "blur" }],
  }
})

const { queryParams, form, rules } = toRefs(data)

function getList() {
  loading.value = true
  listNursingLevel(queryParams.value).then(response => {
    levelList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function getPlanOptions() {
  getEnablePlanList().then(response => {
    planOptions.value = response.data || []
  })
}

function getPlanName(planId: number) {
  const plan = planOptions.value.find(item => item.id === planId)
  return plan ? plan.planName : planId
}

function cancel() {
  open.value = false
  reset()
}

function reset() {
  form.value = { id: null, name: null, planId: null, fee: null, status: '1', description: null }
  proxy.resetForm("levelRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: NursingLevel[]) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加护理等级"
}

function handleUpdate(row: NursingLevel) {
  reset()
  const _id = row.id || ids.value[0]
  getNursingLevel(_id).then(response => {
    form.value = response.data
    form.value.status = String(form.value.status)
    open.value = true
    title.value = "修改护理等级"
  })
}

function submitForm() {
  proxy.$refs["levelRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.id != null) {
        updateNursingLevel(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addNursingLevel(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row: NursingLevel) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除护理等级编号为"' + _ids + '"的数据项？').then(function() {
    return delNursingLevel(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleToggleStatus(row: NursingLevel) {
  const newStatus = row.status === 1 ? 0 : 1;
  const text = newStatus === 1 ? '启用' : '禁用';
  proxy.$modal.confirm('是否确认' + text + '护理等级"' + row.name + '"？').then(function() {
    const data = { id: row.id, name: row.name, planId: row.planId, fee: row.fee, status: newStatus, description: row.description };
    return updateNursingLevel(data);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("操作成功");
  }).catch(() => {})
}

function handleExport() {
  proxy.download('nursing/level/export', { ...queryParams.value }, `level_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList()
  getPlanOptions()
})
</script>