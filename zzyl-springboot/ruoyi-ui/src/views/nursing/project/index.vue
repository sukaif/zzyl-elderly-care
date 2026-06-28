<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 150px">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增护理项目</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="index" label="序号" width="50" align="center" />
      <el-table-column label="名称" align="center" prop="name" width="100" />
      <el-table-column label="排序号" align="center" prop="orderNo" width="80" />
      <el-table-column label="单位" align="center" prop="unit" width="80" />
      <el-table-column label="价格（元）" align="center" prop="price" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.price != null ? scope.row.price.toFixed(2) : '-' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" prop="image" width="80">
        <template slot-scope="scope">
          <img v-if="scope.row.image" :src="scope.row.image" class="img-circle" style="width: 40px; height: 40px" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="护理要求" align="center" prop="nursingRequirement" :show-overflow-tooltip="true" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-color="#13ce66"
            inactive-color="#ff4d4f"
            active-text="启用"
            inactive-text="禁用"
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.status === 1 ? 'el-icon-lock' : 'el-icon-unlock'"
            @click="handleStatus(scope.row)"
          >
            {{ scope.row.status === 1 ? '禁用' : '启用' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="排序号" prop="orderNo">
          <el-input v-model="form.orderNo" type="number" placeholder="请输入排序号" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" type="number" step="0.01" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <imageUpload v-model="form.image" />
        </el-form-item>
        <el-form-item label="护理要求" prop="nursingRequirement">
          <el-input v-model="form.nursingRequirement" type="textarea" :rows="3" placeholder="请输入护理要求" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listNursingProject, getNursingProject, delNursingProject, addNursingProject, updateNursingProject } from '@/api/nursing/project'

export default {
  name: 'NursingProject',
  data() {
    return {
      loading: false,
      showSearch: true,
      ids: [],
      total: 0,
      tableData: [],
      title: '',
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        status: undefined
      },
      form: {},
      rules: {
        name: [
          { required: true, message: '名称不能为空', trigger: 'blur' }
        ],
        orderNo: [
          { required: true, message: '排序号不能为空', trigger: 'blur' }
        ],
        unit: [
          { required: true, message: '单位不能为空', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '价格不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listNursingProject(this.queryParams).then(response => {
        this.tableData = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleAdd() {
      this.form = {}
      this.form.status = 1
      this.open = true
      this.title = '新增护理项目'
    },
    handleUpdate(row) {
      const id = row.id || this.ids[0]
      getNursingProject(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改护理项目'
      })
    },
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateNursingProject(this.form).then(response => {
              if (response.code === 200) {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              }
            })
          } else {
            addNursingProject(this.form).then(response => {
              if (response.code === 200) {
                this.$modal.msgSuccess('新增成功')
                this.open = false
                this.getList()
              }
            })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除护理项目编号为"' + ids + '"的数据项？').then(function() {
        return delNursingProject(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    handleStatus(row) {
      const id = row.id
      const status = row.status === 1 ? 0 : 1
      const statusText = status === 1 ? '启用' : '禁用'
      this.$modal.confirm('是否确认' + statusText + '护理项目编号为"' + id + '"的数据项？').then(function() {
        return updateNursingProject({ id, status })
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(statusText + '成功')
      }).catch(() => {
        row.status = row.status === 1 ? 0 : 1
      })
    },
    handleStatusChange(row) {
      const id = row.id
      const status = row.status
      const statusText = status === 1 ? '启用' : '禁用'
      updateNursingProject({ id, status }).then(response => {
        if (response.code !== 200) {
          row.status = status === 1 ? 0 : 1
          this.$modal.msgError(statusText + '失败')
        } else {
          this.$modal.msgSuccess(statusText + '成功')
        }
      }).catch(() => {
        row.status = status === 1 ? 0 : 1
      })
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
    },
    handleExport() {
      this.download('nursing/project/export', { ...this.queryParams }, 'nursingProject_' + new Date().getTime() + '.xlsx')
    }
  }
}
</script>
