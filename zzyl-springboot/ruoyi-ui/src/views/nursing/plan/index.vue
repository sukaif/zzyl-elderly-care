<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="排序号" prop="sortNo">
        <el-input
          v-model="queryParams.sortNo"
          placeholder="请输入排序号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="planName">
        <el-input
          v-model="queryParams.planName"
          placeholder="请输入名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['nursing:plan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['nursing:plan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['nursing:plan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['nursing:plan:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="排序号" align="center" prop="sortNo" />
      <el-table-column label="名称" align="center" prop="planName" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="statusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['nursing:plan:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['nursing:plan:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="planRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="排序号" prop="sortNo">
              <el-input v-model="form.sortNo" placeholder="请输入排序号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="名称" prop="planName">
              <el-input v-model="form.planName" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="启用" :value="1" />
                <el-option label="禁用" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-divider content-position="left">关联护理项目</el-divider>
            <el-table :data="form.projectPlanList" border>
              <el-table-column label="护理项目" prop="projectId">
                <template #default="scope">
                  <el-select v-model="scope.row.projectId" placeholder="选择项目" style="width:100%">
                    <el-option v-for="item in projectOptions" :key="item.id" :label="item.name" :value="item.id"/>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="执行时间" prop="executeTime">
                <template #default="scope">
                  <el-input v-model="scope.row.executeTime" placeholder="执行时间" size="small"/>
                </template>
              </el-table-column>
              <el-table-column label="周期" prop="executeCycle">
                <template #default="scope">
                  <el-input v-model="scope.row.executeCycle" placeholder="周期" size="small"/>
                </template>
              </el-table-column>
              <el-table-column label="频次" prop="executeFrequency">
                <template #default="scope">
                  <el-input v-model="scope.row.executeFrequency" placeholder="频次" size="small"/>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="80">
                <template #default="scope">
                  <el-button type="danger" size="small" @click="form.projectPlanList.splice(scope.$index,1)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-button type="primary" plain size="small" style="margin-top:10px" @click="form.projectPlanList.push({})">添加项目</el-button>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Plan">
import { ref, reactive, onMounted } from 'vue'
import { listNursingPlan, getNursingPlan, addNursingPlan, updateNursingPlan, delNursingPlan } from '@/api/nursing/plan'
import { listNursingProject } from '@/api/nursing/project'

const { proxy } = getCurrentInstance()

const planList = ref([])
const projectOptions = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref('')

const data = reactive({
  form: {
    id: null,
    sortNo: null,
    planName: null,
    status: null,
    remark: null,
    projectPlanList: []
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    sortNo: undefined,
    planName: undefined,
  },
  rules: {
    planName: [
      { required: true, message: "名称不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

function getList() {
  loading.value = true
  listNursingPlan(queryParams.value).then(response => {
    planList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function getProjectOptions() {
  listNursingProject({ status: 1 }).then(response => {
    projectOptions.value = response.rows || []
  })
}

function cancel() {
  open.value = false
  reset()
}

function reset() {
  form.value = {
    id: null,
    sortNo: null,
    planName: null,
    status: null,
    remark: null,
    projectPlanList: []
  }
  proxy.resetForm("planRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  form.value.projectPlanList = []
  open.value = true
  title.value = "添加护理计划"
}

function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value[0]
  getNursingPlan(_id).then(response => {
    form.value = response.data
    if (!form.value.projectPlanList) {
      form.value.projectPlanList = []
    }
    open.value = true
    title.value = "修改护理计划"
  })
}

function submitForm() {
  proxy.$refs["planRef"].validate((valid) => {
    if (valid) {
      if (form.value.id != null) {
        updateNursingPlan(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addNursingPlan(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除护理计划编号为"' + _ids + '"的数据项？').then(function() {
    return delNursingPlan(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download('nursing/plan/export', {
    ...queryParams.value
  }, `plan_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList()
  getProjectOptions()
})
</script>
