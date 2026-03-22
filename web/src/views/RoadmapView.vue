<template>
  <div class="roadmap-page">
    <el-row :gutter="16">
      <el-col :span="8">
        <el-card shadow="hover" class="kpi">
          <div class="kpi-title">当前完成度</div>
          <el-progress type="dashboard" :percentage="progress" :color="progressColor" />
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header>里程碑进度</template>
          <el-timeline>
            <el-timeline-item
              v-for="item in milestones"
              :key="item.title"
              :timestamp="item.date"
              :type="item.done ? 'success' : 'primary'"
            >
              <div class="title-line">
                <strong>{{ item.title }}</strong>
                <el-tag :type="item.done ? 'success' : 'warning'" effect="plain">
                  {{ item.done ? '已完成' : '进行中' }}
                </el-tag>
              </div>
              <p>{{ item.desc }}</p>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="never" style="margin-top:16px">
      <template #header>下一阶段开发清单</template>
      <el-table :data="nextTasks" border>
        <el-table-column prop="module" label="模块" width="180" />
        <el-table-column prop="target" label="目标" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="row.status === '已完成' ? 'success' : 'info'">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const milestones = [
  { title: '数据库与基础架构', date: '2026-03-20', done: true, desc: '完成核心表结构、初始化数据和容器编排。' },
  { title: '核心业务流程打通', date: '2026-03-21', done: true, desc: '完成方案、任务、自评、审批、申诉、报表主流程。' },
  { title: '角色分离与现代化界面', date: '2026-03-22', done: true, desc: '完成角色菜单隔离、登录页升级、ECharts 报表。' },
  { title: '高可用与发布规范', date: '2026-03-23', done: false, desc: '补充审计、测试覆盖、部署规范与CI。' }
]

const nextTasks = [
  { module: '权限体系', target: '后端接口粒度权限与前端按钮级权限', status: '进行中' },
  { module: '业务闭环', target: '评分规则引擎与审批流可配置化', status: '进行中' },
  { module: '质量保障', target: '补齐单元/集成测试与接口文档', status: '进行中' },
  { module: '运维发布', target: '生产配置模板与CI自动构建发布', status: '进行中' }
]

const progress = computed(() => {
  const doneCount = milestones.filter(item => item.done).length
  return Math.round((doneCount / milestones.length) * 100)
})

const progressColor = computed(() => (progress.value >= 75 ? '#16a34a' : '#f59e0b'))
</script>

<style scoped>
.roadmap-page {
  padding-bottom: 24px;
}
.kpi {
  min-height: 360px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.kpi-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 10px;
}
.title-line {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 6px;
}
p {
  margin: 0;
  color: #64748b;
}
</style>
