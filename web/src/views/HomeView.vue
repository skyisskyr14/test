<template>
  <div>
    <el-row :gutter="16">
      <el-col :span="6" v-for="item in cards" :key="item.label">
        <el-card>
          <h4>{{ item.label }}</h4>
          <div class="num">{{ item.value }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { dashboardApi } from '../api/modules'

const cards = ref([])
onMounted(async () => {
  const { data } = await dashboardApi()
  const d = data.data
  cards.value = [
    { label: '待办考核任务', value: d.pendingTasks },
    { label: '待审批结果', value: d.pendingApprovals },
    { label: '进行中方案', value: d.activePlans },
    { label: '待处理申诉', value: d.appeals }
  ]
})
</script>

<style scoped>
.num { font-size: 28px; color: #2b5aed; font-weight: bold; }
</style>
