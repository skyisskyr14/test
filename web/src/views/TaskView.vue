<template>
  <el-card>
    <template #header>我的考核任务</template>
    <el-table :data="tasks" border>
      <el-table-column prop="id" label="任务ID" width="80"/>
      <el-table-column prop="planId" label="方案ID" width="90"/>
      <el-table-column prop="taskStatus" label="状态" width="90"/>
      <el-table-column prop="submitTime" label="提交时间"/>
      <el-table-column label="操作" width="140">
        <template #default="scope">
          <el-button size="small" type="primary" @click="submit(scope.row.id)">提交自评</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { myTaskApi, submitTaskApi } from '../api/modules'

const tasks = ref([])

const load = async () => {
  const { data } = await myTaskApi()
  tasks.value = data.data
}

const submit = async (id) => {
  await submitTaskApi(id)
  ElMessage.success('已提交')
  load()
}

onMounted(load)
</script>
