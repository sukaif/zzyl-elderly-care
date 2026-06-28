<template>
  <div>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" @click="showAdd=true">新增记录</el-button></el-col>
    </el-row>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="序号" type="index" width="60" align="center" />
      <el-table-column label="老人姓名" prop="elderName" width="120" />
      <el-table-column label="家属姓名" prop="familyName" width="100" />
      <el-table-column label="沟通方式" width="90"><template #default="s">{{ {0:'电话',1:'面谈',2:'微信',3:'其他'}[s.row.communicationType]||'--' }}</template></el-table-column>
      <el-table-column label="沟通时间" prop="communicationTime" width="160" />
      <el-table-column label="沟通原因" prop="reason" min-width="180" show-overflow-tooltip />
      <el-table-column label="沟通结果" prop="result" min-width="180" show-overflow-tooltip />
      <el-table-column label="沟通人" prop="communicator" width="90" />
      <el-table-column label="操作" width="80" align="center"><template #default="s"><el-button link type="danger" @click="handleDel(s.row)">删除</el-button></template></el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" v-model:page="query.pageNum" v-model:limit="query.pageSize" @pagination="loadData" />

    <el-dialog title="新增知情告示" v-model="showAdd" width="600px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row><el-col :span="12"><el-form-item label="老人ID" prop="elderId"><el-input-number v-model="form.elderId" :min="1" style="width:100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="家属姓名" prop="familyName"><el-input v-model="form.familyName" /></el-form-item></el-col></el-row>
        <el-row><el-col :span="12"><el-form-item label="沟通方式" prop="communicationType"><el-select v-model="form.communicationType" style="width:100%"><el-option label="电话" value="0" /><el-option label="面谈" value="1" /><el-option label="微信" value="2" /><el-option label="其他" value="3" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="沟通时间" prop="communicationTime"><el-date-picker v-model="form.communicationTime" type="datetime" style="width:100%" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item></el-col></el-row>
        <el-row><el-col :span="12"><el-form-item label="沟通人" prop="communicator"><el-input v-model="form.communicator" /></el-form-item></el-col></el-row>
        <el-form-item label="沟通原因" prop="reason"><el-input v-model="form.reason" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="沟通结果" prop="result"><el-input v-model="form.result" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button type="primary" :loading="submitting" @click="submitForm">确定</el-button><el-button @click="showAdd=false">取消</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { listNoticeRecord, addNoticeRecord, delNoticeRecord } from "@/api/nursing/stay"
const { proxy } = getCurrentInstance()

const list = ref<any[]>([])
const total = ref(0)
const loading = ref(false)
const showAdd = ref(false)
const submitting = ref(false)
const query = ref({ pageNum:1, pageSize:10 })
const form = ref<any>({})
const formRef = ref()
const rules = { elderId:[{required:true}], familyName:[{required:true}], communicationType:[{required:true}], communicationTime:[{required:true}] }

function loadData() { loading.value=true; listNoticeRecord(query.value).then(r=>{list.value=r.rows;total.value=r.total;loading.value=false}) }
function handleDel(row:any) { proxy.$modal.confirm('确认删除？').then(()=>delNoticeRecord(row.id).then(()=>{proxy.$modal.msgSuccess('已删除');loadData()})).catch(()=>{}) }
function submitForm() { proxy.$refs.formRef.validate((v:boolean)=>{if(v){submitting.value=true;addNoticeRecord(form.value).then(()=>{proxy.$modal.msgSuccess('新增成功');showAdd.value=false;loadData()}).finally(()=>submitting.value=false)}}) }
onMounted(()=>loadData())
</script>
