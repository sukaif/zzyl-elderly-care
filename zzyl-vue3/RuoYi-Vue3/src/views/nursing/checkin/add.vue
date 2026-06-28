<template>
  <div class="app-container">
    <el-form ref="checkinRef" :model="form" :rules="rules" label-width="120px">
      <!-- 1 老人信息 -->
      <div style="border-left:3px solid #409EFF; padding-left:10px; margin-bottom:20px;">
        <span style="font-weight:bold; font-size:16px;">1 老人信息</span>
      </div>

      <el-row>
        <el-col :span="8">
          <el-form-item label="*老人姓名" prop="name">
            <el-input v-model="form.name" placeholder="请输入" maxlength="10" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="*身份证号" prop="idCardNo">
            <el-input v-model="form.idCardNo" placeholder="请输入" maxlength="18" show-word-limit @blur="onIdCardBlur" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="出生日期" prop="birthDate">
            <el-date-picker v-model="form.birthDate" type="date" placeholder="自动填充" style="width:100%" value-format="YYYY-MM-DD" disabled />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="年龄" prop="age">
            <el-input v-model="form.age" placeholder="自动填充" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="性别" prop="gender">
            <el-radio-group v-model="form.gender" disabled>
              <el-radio label="0">男</el-radio>
              <el-radio label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="*联系方式" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入" maxlength="11" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="*家庭住址" prop="address">
            <el-input v-model="form.address" placeholder="请输入" type="textarea" maxlength="100" show-word-limit :rows="2" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="*一寸照片" prop="photo">
            <el-upload class="avatar-uploader" :show-file-list="false" :auto-upload="false" list-type="picture">
              <template #trigger>
                <div v-if="!form.photo" class="upload-box"><el-icon><Plus /></el-icon><div>点击上传</div></div>
                <div v-else class="upload-preview"><el-image :src="form.photo" fit="cover" style="width:100%;height:100%">
                  <template #overlay><div class="overlay"><span @click.stop="form.photo=''" style="color:#fff;cursor:pointer">删除</span></div></template>
                </el-image></div>
              </template>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="*身份证人像面" prop="idCardFront">
            <el-upload class="avatar-uploader" :show-file-list="false" :auto-upload="false">
              <div v-if="!form.idCardFront" class="upload-box"><el-icon><Plus /></el-icon><div>点击上传</div></div>
              <div v-else class="upload-preview"><el-image :src="form.idCardFront" fit="cover" /></div>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="*身份证国徽面" prop="idCardBack">
            <el-upload class="avatar-uploader" :show-file-list="false" :auto-upload="false">
              <div v-if="!form.idCardBack" class="upload-box"><el-icon><Plus /></el-icon><div>点击上传</div></div>
              <div v-else class="upload-preview"><el-image :src="form.idCardBack" fit="cover" /></div>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 2 家属信息 -->
      <div style="border-left:3px solid #409EFF; padding-left:10px; margin-bottom:20px; margin-top:30px;">
        <span style="font-weight:bold; font-size:16px;">2 家属信息</span>
        <span style="color:#999;font-size:12px;margin-left:10px;">至少填写一条</span>
      </div>

      <el-table :data="familyMembers" border style="width:100%; margin-bottom:20px;" size="small">
        <el-table-column label="家属姓名" min-width="160">
          <template #default="scope">
            <el-input v-model="scope.row.name" placeholder="请输入" maxlength="10" show-word-limit />
          </template>
        </el-table-column>
        <el-table-column label="家属联系方式" min-width="180">
          <template #default="scope">
            <el-input v-model="scope.row.phone" placeholder="请输入" maxlength="11" show-word-limit />
          </template>
        </el-table-column>
        <el-table-column label="与老人关系" min-width="140">
          <template #default="scope">
            <el-select v-model="scope.row.relation" placeholder="请选择" style="width:100%">
              <el-option label="子女" value="子女" />
              <el-option label="配偶" value="配偶" />
              <el-option label="亲属" value="亲属" />
              <el-option label="朋友" value="朋友" />
              <el-option label="社工" value="社工" />
              <el-option label="其他" value="其他" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="90" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="addFamilyMember(scope.$index)">＋</el-button>
            <el-button v-if="familyMembers.length > 1" link type="danger" size="small" @click="removeFamilyMember(scope.$index)">－</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 3 入住配置 -->
      <div style="border-left:3px solid #409EFF; padding-left:10px; margin-bottom:20px;">
        <span style="font-weight:bold; font-size:16px;">3 入住配置</span>
        <el-tooltip content="选择入住期限、护理等级和床位信息" placement="top">
          <el-icon style="margin-left:6px;color:#409EFF;cursor:pointer;vertical-align:-2px"><InfoFilled /></el-icon>
        </el-tooltip>
      </div>

      <el-row>
        <el-col :span="12">
          <el-form-item label="*入住期限" prop="checkInDate">
            <el-date-picker v-model="form.checkInDate" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" style="width:100%" value-format="YYYY-MM-DD" :disabled-date="d=>d.getTime()<Date.now()-86400000" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="*费用期限" prop="feeDate">
            <el-date-picker v-model="form.feeDate" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" style="width:100%" value-format="YYYY-MM-DD" :disabled-date="d=>d.getTime()<Date.now()-86400000" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="*护理等级" prop="nursingLevelId">
            <el-select v-model="form.nursingLevelId" placeholder="请选择" clearable filterable style="width:100%" @change="onNursingLevelChange">
              <el-option v-for="item in nursingLevels" :key="item.id" :label="item.name + ' (' + item.fee + '元/月)'" :value="item.id" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="*入住床位" prop="bedId">
            <el-cascader v-model="form.bedId" :options="bedTree" :props="{value:'id',label:'label',children:'children',checkStrictly:true}" placeholder="请选择楼栋/房间/床位" style="width:100%" filterable clearable @change="onBedChange" />
          </el-form-item>
        </el-col>
      </el-row>

      <div style="text-align:right;margin-bottom:10px">
        <el-button type="primary" plain @click="showBillPreview = true">📋 账单预览</el-button>
      </div>

      <!-- 4 费用明细 -->
      <div style="border-left:3px solid #409EFF; padding-left:10px; margin-bottom:20px;">
        <span style="font-weight:bold; font-size:16px;">4 费用明细</span>
        <el-tooltip content="护理/床位费用可修改±10%范围内" placement="top">
          <el-icon style="margin-left:6px;color:#409EFF;cursor:pointer;vertical-align:-2px"><InfoFilled /></el-icon>
        </el-tooltip>
      </div>

      <el-row>
        <el-col :span="6">
          <el-form-item label="押金" prop="deposit">
            <el-input-number v-model="form.deposit" :min="0" :precision="2" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="*护理费用" prop="nursingFee">
            <el-input-number v-model="form.nursingFee" :min="0" :precision="2" :disabled="!form.nursingLevelId" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="*床位费用" prop="bedPrice">
            <el-input-number v-model="form.bedPrice" :min="0" :precision="2" :disabled="!form.bedId" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="其他费用" prop="otherFee">
            <el-input-number v-model="form.otherFee" :min="0" :precision="2" style="width:100%" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <el-form-item label="医保费用" prop="medicalFee">
            <el-input-number v-model="form.medicalFee" :min="0" :precision="2" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="政府补贴" prop="subsidy">
            <el-input-number v-model="form.subsidy" :min="0" :precision="2" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12" style="display:flex;align-items:center;padding-top:5px">
          <span style="color:#999;font-size:13px">该费用为月账单，每月1号收当月费用</span>
        </el-col>
      </el-row>

      <!-- 5 签约办理 -->
      <div style="border-left:3px solid #409EFF; padding-left:10px; margin-bottom:20px;">
        <span style="font-weight:bold; font-size:16px;">5 签约办理</span>
        <el-tooltip content="填写合同信息并上传合同文件" placement="top">
          <el-icon style="margin-left:6px;color:#409EFF;cursor:pointer;vertical-align:-2px"><InfoFilled /></el-icon>
        </el-tooltip>
      </div>

      <el-row>
        <el-col :span="8">
          <el-form-item label="*合同名称" prop="contractName">
            <el-input v-model="form.contractName" placeholder="请输入" maxlength="20" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="*签约日期" prop="signDate">
            <el-date-picker v-model="form.signDate" type="date" placeholder="默认当前日期" style="width:100%" value-format="YYYY-MM-DD" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="丙方姓名" prop="thirdPartyName">
            <el-input v-model="form.thirdPartyName" placeholder="选填" maxlength="10" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="丙方联系方式" prop="thirdPartyPhone">
            <el-input v-model="form.thirdPartyPhone" placeholder="选填" maxlength="11" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="*入住合同" prop="contractFile">
            <el-upload :show-file-list="true" :auto-upload="false" :limit="1" accept=".pdf">
              <div class="upload-file"><el-icon><Upload /></el-icon><span>上传PDF文件</span></div>
              <template #tip><div class="el-upload__tip" style="color:#999">请上传pdf文件，大小在60M以内</div></template>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 提示信息 -->
    <el-alert title="入住成功后，系统将会生成首期账单。生成账单后，入住配置和签约办理信息将无法修改，若修改信息，需办理退住流程。"
      type="warning" :closable="false" show-icon style="margin-bottom:20px" />

    <!-- 提交按钮 -->
    <div style="text-align:center; margin-top:20px;">
      <el-button type="primary" :loading="submitting" size="large" @click="submitForm">确认提交</el-button>
      <el-button size="large" @click="goBack">取消</el-button>
    </div>

    <!-- 账单预览弹窗 -->
    <el-dialog title="账单预览" v-model="showBillPreview" width="700px" append-to-body>
      <div style="font-size:16px;font-weight:bold;margin-bottom:8px">{{ form.name || '老人姓名' }}</div>
      <div style="color:#999;margin-bottom:16px;font-size:13px">
        账单周期：{{ billCycleStart }} —— {{ billCycleEnd }}
        <span style="margin-left:16px">共计 {{ billDays }} 天</span>
      </div>

      <el-table border size="small" style="margin-bottom:8px">
        <el-table-column label="类型" width="80" align="center">
          <template #default="s">{{ s.$index === 0 ? '添加项' : '' }}</template>
        </el-table-column>
        <el-table-column label="费用项目" width="120">
          <template #default="s">
            <span v-if="s.$index < 3">{{ ['护理费用','床位费用','其他费用'][s.$index] }}</span>
            <span v-else>{{ ['医保费用','政府补贴'][s.$index-3] }}</span>
          </template>
        </el-table-column>
        <el-table-column label="服务内容" width="120">
          <template #default="s">
            {{ ['特级护理等级','特护房','其他','——','——'][s.$index] || '—' }}
          </template>
        </el-table-column>
        <el-table-column label="账单月份" width="100">
          <template #default="s">{{ billMonth }}</template>
        </el-table-column>
        <el-table-column label="金额(元)" width="100" align="right">
          <template #default="s">
            {{ [form.nursingFee||0, form.bedPrice||0, form.otherFee||0, -(form.medicalFee||0), -(form.subsidy||0)][s.$index] }}
          </template>
        </el-table-column>
      </el-table>

      <div style="background:#f5f7fa;padding:12px 16px;border-radius:6px;margin-bottom:12px;font-size:14px">
        <el-row>
          <el-col :span="6">添加项小计：<b>¥{{ addSubtotal }}</b></el-col>
          <el-col :span="6">扣减项小计：<b>¥{{ deductSubtotal }}</b></el-col>
          <el-col :span="6">每月应付：<b style="color:#e6a23c;font-size:18px">¥{{ monthlyPay }}</b></el-col>
          <el-col :span="6">共计天数：<b>{{ billDays }} 天</b></el-col>
        </el-row>
      </div>

      <el-row :gutter="16">
        <el-col :span="8"><div style="background:#f0f9eb;padding:12px;text-align:center;border-radius:6px"><div style="color:#999;font-size:12px">押金</div><b style="font-size:18px;color:#67c23a">¥{{ form.deposit||0 }}</b></div></el-col>
        <el-col :span="8"><div style="background:#ecf5ff;padding:12px;text-align:center;border-radius:6px"><div style="color:#999;font-size:12px">本期应付</div><b style="font-size:18px;color:#409eff">¥{{ currentPay }}</b></div></el-col>
        <el-col :span="8"><div style="background:#fdf6ec;padding:12px;text-align:center;border-radius:6px"><div style="color:#999;font-size:12px">账单金额</div><b style="font-size:18px;color:#e6a23c">¥{{ billAmount }}</b></div></el-col>
      </el-row>

      <template #footer><el-button @click="showBillPreview = false">关闭</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { Plus, Upload, InfoFilled } from '@element-plus/icons-vue'
import { listNursingLevelAll, getFloorTree, addCheckIn, listCheckIn } from "@/api/nursing/checkin"

const { proxy } = getCurrentInstance()
const router = useRouter()

const submitting = ref(false)
const nursingLevels = ref<any[]>([])
const bedTree = ref<any[]>([])
const showBillPreview = ref(false)

// 原始费用（用于校验±10%）
const originalNursingFee = ref(0)
const originalBedPrice = ref(0)

const familyMembers = ref<{ name: string; phone: string; relation: string }[]>([
  { name: '', phone: '', relation: '' }
])

const form = ref({
  name: '', idCardNo: '', birthDate: '', age: undefined, gender: '0',
  phone: '', address: '', photo: '', idCardFront: '', idCardBack: '',
  nursingLevelId: undefined, nursingFee: 0,
  bedId: undefined, bedPrice: 0,
  checkInDate: '', feeDate: '',
  deposit: 3000, otherFee: 0, medicalFee: 0, subsidy: 0,
  contractName: '', signDate: '', thirdPartyName: '', thirdPartyPhone: '', contractFile: '',
})

// 身份证校验
const idCardRegex = /^[1-9]\d{5}(19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dXx]$/
const phoneRegex = /^1[3-9]\d{9}$/

const rules: any = {
  name: [
    { required: true, message: '请输入老人姓名', trigger: 'blur' },
    { pattern: /^[^\uD800-\uDFFF]+$/, message: '不能包含表情符号', trigger: 'blur' },
  ],
  idCardNo: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { len: 18, message: '身份证号为18位', trigger: 'blur' },
    { pattern: idCardRegex, message: '身份证格式错误，请重新输入', trigger: 'blur' },
  ],
  phone: [
    { required: true, message: '请输入联系方式', trigger: 'blur' },
    { pattern: phoneRegex, message: '手机号格式错误，请重新输入', trigger: 'blur' },
  ],
  address: [{ required: true, message: '请输入家庭住址', trigger: 'blur' }],
  checkInDate: [{ required: true, message: '请选择入住期限', trigger: 'change' }],
  feeDate: [{ required: true, message: '请选择费用期限', trigger: 'change' }],
  nursingLevelId: [{ required: true, message: '请选择护理等级', trigger: 'change' }],
  bedId: [{ required: true, message: '请选择入住床位', trigger: 'change' }],
  nursingFee: [{ required: true, message: '请输入护理费用', trigger: 'blur' }],
  bedPrice: [{ required: true, message: '请输入床位费用', trigger: 'blur' }],
  contractName: [{ required: true, message: '请输入合同名称', trigger: 'blur' }],
  signDate: [{ required: true, message: '请选择签约日期', trigger: 'change' }],
}

// 身份证号自动填充（已在上方完整实现）
function addFamilyMember(index: number) {
  familyMembers.value.splice(index + 1, 0, { name: '', phone: '', relation: '' })
}

function removeFamilyMember(index: number) {
  if (familyMembers.value.length > 1) familyMembers.value.splice(index, 1)
}

function loadNursingLevels() {
  listNursingLevelAll().then(res => { nursingLevels.value = res.rows || [] })
}

function loadBedTree() {
  getFloorTree('0').then(res => { bedTree.value = res.data || [] })
}

function onNursingLevelChange(val: number) {
  const level = nursingLevels.value.find(item => item.id === val)
  if (level) {
    originalNursingFee.value = Number(level.fee)
    form.value.nursingFee = originalNursingFee.value
  }
}

function onBedChange(val: any) {
  // cascader returns array
  const bedId = Array.isArray(val) ? val[val.length - 1] : val
  // 从树中查出价格
  function findPrice(nodes: any[], id: number): number {
    for (const n of nodes) {
      if (n.id === id && n.price) return Number(n.price)
      if (n.children) { const r = findPrice(n.children, id); if (r) return r }
    }
    return 0
  }
  const price = findPrice(bedTree.value, bedId)
  if (price) {
    originalBedPrice.value = price
    form.value.bedPrice = price
  }
}

// 身份证号唯一校验（检查是否已入住）
function checkIdCardUnique(idCardNo: string): Promise<boolean> {
  return listCheckIn({ pageNum: 1, pageSize: 999, status: '1' }).then(res => {
    const exists = (res.rows || []).some((r: any) => r.idCardNo === idCardNo)
    if (exists) {
      proxy.$modal.msgError('该老人已入住，请重新输入')
      return false
    }
    return true
  })
}

function onIdCardBlur() {
  const v = form.value.idCardNo
  if (idCardRegex.test(v)) {
    checkIdCardUnique(v)
    form.value.birthDate = v.substring(6, 10) + '-' + v.substring(10, 12) + '-' + v.substring(12, 14)
    const birthYear = parseInt(v.substring(6, 10))
    const now = new Date().getFullYear()
    form.value.age = now - birthYear
    form.value.gender = parseInt(v.substring(16, 17)) % 2 === 1 ? '0' : '1'
  }
}

// 账单预览计算
const billMonth = computed(() => {
  if (Array.isArray(form.value.feeDate) && form.value.feeDate[0]) return form.value.feeDate[0].substring(0, 7)
  return '--'
})
const billCycleStart = computed(() => {
  if (Array.isArray(form.value.feeDate) && form.value.feeDate[0]) return form.value.feeDate[0]
  return '--'
})
const billCycleEnd = computed(() => {
  if (Array.isArray(form.value.feeDate) && form.value.feeDate[1]) return form.value.feeDate[1]
  return '--'
})
const billDays = computed(() => {
  if (!Array.isArray(form.value.feeDate) || !form.value.feeDate[0] || !form.value.feeDate[1]) return 0
  const s = new Date(form.value.feeDate[0]).getTime()
  const e = new Date(form.value.feeDate[1]).getTime()
  return Math.ceil((e - s) / 86400000) + 1
})

const billAddItems = computed(() => [
  { type: '添加项', name: '护理费用', desc: '特级护理等级', month: form.value.feeDate?.[0]?.substring(0,7)||'--', amount: form.value.nursingFee || 0 },
  { type: '添加项', name: '床位费用', desc: '特护房', month: form.value.feeDate?.[0]?.substring(0,7)||'--', amount: form.value.bedPrice || 0 },
  { type: '添加项', name: '其他费用', desc: '其他', month: form.value.feeDate?.[0]?.substring(0,7)||'--', amount: form.value.otherFee || 0 },
])

const billDeductItems = computed(() => [
  { type: '扣减项', name: '医保费用', desc: '—', month: form.value.feeDate?.[0]?.substring(0,7)||'--', amount: -(form.value.medicalFee || 0) },
  { type: '扣减项', name: '政府补贴', desc: '—', month: form.value.feeDate?.[0]?.substring(0,7)||'--', amount: -(form.value.subsidy || 0) },
])

const addSubtotal = computed(() => (form.value.nursingFee||0) + (form.value.bedPrice||0) + (form.value.otherFee||0))
const deductSubtotal = computed(() => (form.value.medicalFee||0) + (form.value.subsidy||0))
const monthlyPay = computed(() => Math.max(0, addSubtotal.value - deductSubtotal.value))
const currentPay = computed(() => monthlyPay.value)
const billAmount = computed(() => currentPay.value + (form.value.deposit||0))

function submitForm() {
  // 校验家属信息
  const validFamily = familyMembers.value.some(m => m.name && m.phone && m.relation)
  if (!validFamily) { proxy.$modal.msgError('家属信息不完整，请输入家属信息'); return }

  // 校验费用±10%
  if (originalNursingFee.value && form.value.nursingFee) {
    const diff = Math.abs(form.value.nursingFee - originalNursingFee.value)
    if (diff > originalNursingFee.value * 0.1) {
      proxy.$modal.msgError('护理费用已超出/低于原费用10%，请重新输入'); return
    }
  }

  proxy.$refs.checkinRef.validate((valid: boolean) => {
    if (!valid) return

    submitting.value = true
    const postData = {
      ...form.value,
      familyInfo: JSON.stringify(familyMembers.value),
      checkInDate: Array.isArray(form.value.checkInDate) ? form.value.checkInDate[0] : form.value.checkInDate,
      feeDate: Array.isArray(form.value.feeDate) ? form.value.feeDate[0] : form.value.feeDate,
      startDate: Array.isArray(form.value.checkInDate) ? form.value.checkInDate[0] : '',
      endDate: Array.isArray(form.value.checkInDate) ? form.value.checkInDate[1] : '',
    }

    addCheckIn(postData).then(() => {
      proxy.$modal.msgSuccess('入住登记成功！')
      router.push('/nursing/checkin')
    }).catch(() => { submitting.value = false })
  })
}

function goBack() { router.push('/nursing/checkin') }

onMounted(() => {
  loadNursingLevels()
  loadBedTree()
  form.value.signDate = new Date().toISOString().substring(0, 10)
})
</script>

<style scoped>
.upload-box {
  width: 160px; height: 120px;
  border: 1px dashed #d9d9d9; border-radius: 6px;
  display: flex; flex-direction: column; align-items: center; justify-content: center;
  cursor: pointer; color: #999;
}
.upload-box:hover { border-color: #409EFF; color: #409EFF; }
.upload-preview { width: 160px; height: 120px; border-radius: 6px; overflow: hidden; position: relative; }
.overlay { position:absolute;bottom:0;left:0;right:0;background:rgba(0,0,0,.5);text-align:center;padding:4px 0; }
.upload-file {
  display: inline-flex; align-items: center; gap: 6px;
  padding: 8px 16px; background: #ecf5ff; border: 1px solid #b3d8ff; border-radius: 4px;
  color: #409EFF; cursor: pointer;
}
.upload-file:hover { background: #dbeafe; }
</style>
