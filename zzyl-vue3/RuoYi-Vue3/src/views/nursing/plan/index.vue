<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="planName">
        <el-input
          v-model="queryParams.planName"
          placeholder="请输入"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable>
          <el-option label="启用" value="1" />
          <el-option label="禁用" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetQuery">重置</el-button>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8" style="justify-content: flex-end;">
      <el-col :span="1.5">
        <el-button
          type="primary"
          @click="handleAdd"
          v-hasPermi="['nursing:plan:add']"
        >新增护理计划</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="planList">
      <el-table-column label="序号" align="center" type="index" width="60" />
      <el-table-column label="名称" align="center" prop="planName" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="260">
        <template #default="scope">
          <el-button link type="primary" @click="handleUpdate(scope.row)" v-hasPermi="['nursing:plan:edit']">修改</el-button>
          <el-button link type="primary" @click="handleDelete(scope.row)" v-hasPermi="['nursing:plan:remove']">删除</el-button>
          <el-button link type="primary" @click="handleLook(scope.row)">查看</el-button>
          <el-button link :type="scope.row.status === 1 ? 'danger' : 'primary'" @click="handleToggleStatus(scope.row)" v-hasPermi="['nursing:plan:edit']">
            {{ scope.row.status === 1 ? '禁用' : '启用' }}
          </el-button>
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

    <el-dialog :title="title" v-model="open" width="840px" @close="cancel()">
      <el-form ref="planRef" :model="form" :rules="rules" label-width="120px">
        <el-row gutter="10">
          <el-col :span="24" class="elcolFlex">
            <el-form-item label="护理计划名称：" prop="planName">
              <el-input v-model="form.planName" :disabled="isLook"></el-input>
            </el-form-item>
            <el-form-item label="状态：" prop="status">
              <el-radio-group v-model="form.status" :disabled="isLook">
                <el-radio v-for="dict in nursing_status" :value="dict.value" :label="dict.value"
                  :key="dict.value">{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="排序：" prop="sortNo">
              <el-input-number :disabled="isLook" v-model="form.sortNo" :min="0" :max="999999"
                :decimal-places="0" @blur="textBlurNo" @change="textBlurNo"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="护理项目：">
          <div class="info family">
            <div class="tableHead">
              <div>护理项目名称</div>
              <div>期望服务时间</div>
              <div>执行周期</div>
              <div>执行频次(次)</div>
              <div v-if="!isLook">操作</div>
            </div>
            <div class="tableBody">
              <div class="tableColumn" v-for="(item, index) in projectList" :key="index">
                <div class="column">
                  <el-select :disabled="isLook" v-model="item.projectId" placeholder="请选择">
                    <el-option v-for="opt in projectOptions" :key="opt.value" :label="opt.label"
                      :value="opt.value"></el-option>
                  </el-select>
                </div>
                <div class="column">
                  <el-time-picker v-model="item.executeTime" format="HH:mm:ss" value-format="HH:mm:ss"
                    :style="{ width: '100%' }" placeholder="请选择时间" :disabled="isLook" clearable></el-time-picker>
                </div>
                <div class="column">
                  <el-select v-model="item.executeCycle" placeholder="请选择" :disabled="isLook">
                    <el-option v-for="opt in executeCycleOptions" :key="opt.value" :label="opt.label"
                      :value="opt.value"></el-option>
                  </el-select>
                </div>
                <div class="column">
                  <el-input-number v-model="item.executeFrequency" :controls="false" :max="7" :min="1"
                    :disabled="isLook" />
                </div>
                <div class="column" v-if="!isLook">
                    <el-button type="primary" circle @click="handleRowAdd"> + </el-button>
                    <el-button type="danger" circle v-if="projectList.length > 1" @click="handleRowDel(index)"> - </el-button>
                </div>
              </div>
            </div>
          </div>
        </el-form-item>
      </el-form>
      <div class="dialog-footer" v-if="!isLook">
        <el-button type="primary" @click="submitForm">确定</el-button>
        <el-button @click="cancel()">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="Plan">
import type { NursingPlan, PlanQueryParams, ProjectPlan } from "@/types/api/nursing/plan"
import { listNursingPlan, getNursingPlan, delNursingPlan, addNursingPlan, updateNursingPlan } from "@/api/nursing/plan"
import { getAllProjects } from '@/api/nursing/project';

const { proxy } = getCurrentInstance()
const { nursing_status } = useDict('nursing_status')

const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")
const isLook = ref(false);
const projectOptions = ref([]);
const executeCycleOptions = ref([
  { label: '天', value: '0' },
  { label: '周', value: '1' },
  { label: '月', value: '2' },
]);

const planList = ref<NursingPlan[]>([])
const projectList = ref<ProjectPlan[]>([
  {
    projectId: '',
    executeTime: '',
    executeCycle: '',
    executeFrequency: '1',
  }
])

const handleRowDel = (index: number) => {
  if (projectList.value.length === 1) return;
  projectList.value.splice(index, 1);
};

const handleRowAdd = () => {
  const obj: ProjectPlan = {
    projectId: '',
    executeTime: '',
    executeCycle: '',
    executeFrequency: '1',
  };
  projectList.value.push(obj);
};

const data = reactive({
  form: {} as NursingPlan,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    planName: undefined,
    status: undefined,
  } as PlanQueryParams,
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

const textBlurNo = () => {
  const data = form.value.sortNo;
  if (data <= 1) {
    form.value.sortNo = 1;
  }
  if (data > 99999999) {
    form.value.sortNo = 99999999;
  }
};

function getProjectList() {
  getAllProjects().then(response => {
    projectOptions.value = response.data;
  });
};

function getList() {
  loading.value = true
  listNursingPlan(queryParams.value).then(response => {
    planList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function cancel() {
  open.value = false
  isLook.value = false;
  reset()
}

function reset() {
  form.value = {
    planName: null,
    status: '1',
    sortNo: 1,
  };
  projectList.value = [
    {
      projectId: '',
      executeTime: '',
      executeCycle: '',
      executeFrequency: '1',
    },
  ];
  proxy.resetForm('planRef');
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection: NursingPlan[]) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加护理计划"
}

function handleUpdate(row: NursingPlan) {
  reset()
  const _id = row.id || ids.value[0]
  getNursingPlan(_id).then(response => {
    form.value = response.data
    form.value.status = String(form.value.status)
    projectList.value = form.value.projectPlans || []
    if (projectList.value.length === 0) {
      projectList.value = [{ projectId: '', executeTime: '', executeCycle: '', executeFrequency: '1' }]
    }
    open.value = true
    title.value = "修改护理计划"
  })
}

function handleLook(row: NursingPlan) {
  isLook.value = true;
  open.value = true;
  const _id = row.id || ids.value[0]
  getNursingPlan(_id).then((response) => {
    form.value = response.data;
    form.value.status = String(form.value.status);
    projectList.value = form.value.projectPlans || []
    if (projectList.value.length === 0) {
      projectList.value = [{ projectId: '', executeTime: '', executeCycle: '', executeFrequency: '1' }]
    }
    open.value = true;
    title.value = '查看护理计划';
  });
};

function hasDuplicateIds(objectsArray: any[]) {
  if (!Array.isArray(objectsArray) || objectsArray.length === 0) {
    return false;
  }
  const idSet = new Set();
  for (const obj of objectsArray) {
    if (obj.projectId !== undefined) {
      idSet.add(obj.projectId);
    }
  }
  return idSet.size !== objectsArray.length;
}

function submitForm() {
  if (hasDuplicateIds(projectList.value)) {
    proxy.$modal.msgError('请勿选择重复的护理项目');
    return;
  }
  form.value['projectPlans'] = projectList.value;

  proxy.$refs['planRef'].validate((valid: boolean) => {
    if (valid) {
      if (form.value.id != null) {
        updateNursingPlan(form.value).then((response) => {
          proxy.$modal.msgSuccess('修改成功');
          cancel();
          getList();
        });
      } else {
        addNursingPlan(form.value).then((response) => {
          proxy.$modal.msgSuccess('新增成功');
          cancel();
          getList();
        });
      }
    }
  });
}

function handleDelete(row: NursingPlan) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除护理计划编号为"' + _ids + '"的数据项？').then(function() {
    return delNursingPlan(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleToggleStatus(row: NursingPlan) {
  const newStatus = row.status === 1 ? 0 : 1;
  const text = newStatus === 1 ? '启用' : '禁用';
  proxy.$modal.confirm('是否确认' + text + '护理计划"' + row.planName + '"？').then(function() {
    const data = { id: row.id, planName: row.planName, status: newStatus, sortNo: row.sortNo };
    return updateNursingPlan(data);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("操作成功");
  }).catch(() => {})
}

function handleExport() {
  proxy.download('nursing/plan/export', {
    ...queryParams.value
  }, `plan_${new Date().getTime()}.xlsx`)
}

getList()
getProjectList()
</script>
<style src="./index.scss" lang="scss"></style>