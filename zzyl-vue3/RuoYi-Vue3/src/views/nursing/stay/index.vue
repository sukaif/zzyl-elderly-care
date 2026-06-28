<template>
  <div class="app-container">
    <!-- ===== 顶部统计卡片 ===== -->
    <el-row :gutter="16" style="margin-bottom:18px">
      <el-col :span="3"><el-card shadow="hover" :body-style="{padding:'14px'}"><div class="stat-item"><div class="stat-num" style="color:#409eff">{{ stats.totalBeds||0 }}</div><div class="stat-label">总床位</div></div></el-card></el-col>
      <el-col :span="3"><el-card shadow="hover" :body-style="{padding:'14px'}"><div class="stat-item"><div class="stat-num" style="color:#67c23a">{{ stats.occupiedBeds||0 }}</div><div class="stat-label">已入住</div></div></el-card></el-col>
      <el-col :span="3"><el-card shadow="hover" :body-style="{padding:'14px'}"><div class="stat-item"><div class="stat-num" style="color:#e6a23c">{{ stats.freeBeds||0 }}</div><div class="stat-label">空闲</div></div></el-card></el-col>
      <el-col :span="3"><el-card shadow="hover" :body-style="{padding:'14px'}"><div class="stat-item"><div class="stat-num" style="color:#909399">{{ stats.maintenanceBeds||0 }}</div><div class="stat-label">维修</div></div></el-card></el-col>
      <el-col :span="3"><el-card shadow="hover" :body-style="{padding:'14px'}"><div class="stat-item"><div class="stat-num" style="color:#67c23a">{{ stats.todayCheckIn||0 }}</div><div class="stat-label">今日入住</div></div></el-card></el-col>
      <el-col :span="3"><el-card shadow="hover" :body-style="{padding:'14px'}"><div class="stat-item"><div class="stat-num" style="color:#f56c6c">{{ stats.todayCheckOut||0 }}</div><div class="stat-label">今日退住</div></div></el-card></el-col>
      <el-col :span="3"><el-card shadow="hover" :body-style="{padding:'14px'}"><div class="stat-item"><div class="stat-num" style="color:#f56c6c">{{ stats.onLeave||0 }}</div><div class="stat-label">请假中</div></div></el-card></el-col>
      <el-col :span="3"><el-card shadow="hover" :body-style="{padding:'14px'}"><div class="stat-item"><div class="stat-num" style="color:#409eff">{{ stats.occupancyRate||'0' }}%</div><div class="stat-label">入住率</div></div></el-card></el-col>
    </el-row>

    <!-- ===== Tab导航（8个Tab匹配原型图） ===== -->
    <el-tabs v-model="activeTab" type="border-card" @tab-change="onTabChange">
      <el-tab-pane label="房态图" name="roomStatus" lazy>
        <room-status :stats="stats" />
      </el-tab-pane>
      <el-tab-pane label="基本信息" name="residentList" lazy>
        <resident-list />
      </el-tab-pane>
      <el-tab-pane label="护理变更" name="nursingChange" lazy>
        <nursing-change />
      </el-tab-pane>
      <el-tab-pane label="折扣变更" name="discountChange" lazy>
        <discount-change />
      </el-tab-pane>
      <el-tab-pane label="请假管理" name="leaveManage" lazy>
        <leave-manage />
      </el-tab-pane>
      <el-tab-pane label="退住办理" name="checkoutManage" lazy>
        <checkout-manage />
      </el-tab-pane>
      <el-tab-pane label="流动统计" name="flowStats" lazy>
        <flow-stats />
      </el-tab-pane>
      <el-tab-pane label="知情告示" name="noticeRecord" lazy>
        <notice-record />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts" name="StayManage">
import { getRoomStatus } from "@/api/nursing/stay"
import RoomStatus from "./tabs/roomStatus.vue"
import ResidentList from "./tabs/residentList.vue"
import NursingChange from "./tabs/nursingChange.vue"
import DiscountChange from "./tabs/discountChange.vue"
import LeaveManage from "./tabs/leaveManage.vue"
import CheckoutManage from "./tabs/checkoutManage.vue"
import FlowStats from "./tabs/flowStats.vue"
import NoticeRecord from "./tabs/noticeRecord.vue"

const activeTab = ref("roomStatus")
const stats = ref<any>({})

function loadRoomStatus() {
  getRoomStatus().then(response => {
    stats.value = response.data || {}
  })
}

function onTabChange(tab: string) {
  if (tab === "roomStatus") loadRoomStatus()
}

onMounted(() => {
  loadRoomStatus()
})
</script>

<style scoped>
.stat-item { text-align: center; }
.stat-num { font-size: 22px; font-weight: bold; }
.stat-label { font-size: 12px; color: #999; margin-top: 4px; }
</style>
