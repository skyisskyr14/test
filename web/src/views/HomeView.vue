<template>
  <div>
    <el-row :gutter="16">
      <el-col :span="6" v-for="item in cards" :key="item.label">
        <el-card shadow="hover" class="card">
          <h4>{{ item.label }}</h4>
          <div class="num">{{ item.value }}</div>
          <small>{{ item.tip }}</small>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 16px">
      <template #header>待办中心</template>
      <el-alert
        v-for="todo in todos"
        :key="todo"
        :title="todo"
        type="info"
        show-icon
        :closable="false"
        style="margin-bottom: 10px"
      />
    </el-card>
  </div>
</template>

<script setup>
import { computed, ref, onMounted } from 'vue'
import { dashboardApi } from '../api/modules'
import { ROLE } from '../constants/navigation'

const cards = ref([])
const dashboardData = ref({ pendingTasks: 0, pendingApprovals: 0, activePlans: 0, appeals: 0 })
const user = JSON.parse(localStorage.getItem('user') || 'null')
const roleId = Number(user?.roleId || ROLE.EMPLOYEE)

const todos = computed(() => {
  if (roleId === ROLE.HR) {
    return ['检查本月考核方案发布进度', `当前待处理申诉：${dashboardData.value.appeals} 条`, '核对指标库与权重配置']
  }
  if (roleId === ROLE.LEADER) {
    return [`待审批绩效结果：${dashboardData.value.pendingApprovals} 条`, '关注跨部门绩效趋势', '审批意见请补充理由']
  }
  return [`我的待办任务：${dashboardData.value.pendingTasks} 条`, '尽快完成自评并提交', '可在“申诉处理”发起绩效申诉']
})

onMounted(async () => {
  const { data } = await dashboardApi()
  const d = data.data
  dashboardData.value = d

  if (roleId === ROLE.HR) {
    cards.value = [
      { label: '进行中方案', value: d.activePlans, tip: '由HR维护的方案数量' },
      { label: '待处理申诉', value: d.appeals, tip: '需尽快反馈员工' },
      { label: '待办任务', value: d.pendingTasks, tip: '全员待推进任务' },
      { label: '待审批结果', value: d.pendingApprovals, tip: '待管理层审批' }
    ]
    return
  }

  if (roleId === ROLE.LEADER) {
    cards.value = [
      { label: '待审批结果', value: d.pendingApprovals, tip: '请及时完成审批' },
      { label: '进行中方案', value: d.activePlans, tip: '关注目标达成进度' },
      { label: '待办任务', value: d.pendingTasks, tip: '组织内任务看板' },
      { label: '待处理申诉', value: d.appeals, tip: '可查看申诉处理状态' }
    ]
    return
  }

  cards.value = [
    { label: '我的待办任务', value: d.pendingTasks, tip: '优先处理未提交任务' },
    { label: '可查看方案', value: d.activePlans, tip: '当前进行中的方案' },
    { label: '待处理结果', value: d.pendingApprovals, tip: '等待管理层审批' },
    { label: '申诉进度', value: d.appeals, tip: '可跟踪处理状态' }
  ]
})
</script>

<style scoped>
.card h4 {
  margin: 0 0 8px;
}

.num {
  font-size: 28px;
  color: #2563eb;
  font-weight: bold;
  margin-bottom: 8px;
}

small {
  color: #64748b;
}
</style>
