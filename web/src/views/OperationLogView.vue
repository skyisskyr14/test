<template>
  <el-card>
    <template #header>系统操作日志（最近200条）</template>
    <el-table :data="logs" border v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="operateTime" label="时间" width="170" />
      <el-table-column prop="userId" label="用户ID" width="90" />
      <el-table-column prop="module" label="模块" width="120" />
      <el-table-column prop="requestMethod" label="方法" width="90" />
      <el-table-column prop="requestUrl" label="URL" min-width="220" />
      <el-table-column prop="status" label="状态" width="90">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '成功' : '失败' }}</el-tag>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { systemLogsApi } from '../api/modules'

const logs = ref([])
const loading = ref(false)

onMounted(async () => {
  loading.value = true
  try {
    const { data } = await systemLogsApi()
    if (!data.success) {
      ElMessage.error(data.message || '加载日志失败')
      return
    }
    logs.value = data.data || []
  } catch {
    ElMessage.error('加载日志失败，请检查后端服务')
  } finally {
    loading.value = false
  }
})
</script>
