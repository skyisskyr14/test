<template>
  <el-card>
    <template #header>结果审批（管理层）</template>
    <el-table :data="list" border>
      <el-table-column prop="id" label="结果ID" width="90"/>
      <el-table-column prop="taskId" label="任务ID" width="90"/>
      <el-table-column prop="totalScore" label="总分" width="90"/>
      <el-table-column prop="resultLevel" label="等级" width="90"/>
      <el-table-column prop="approvalStatus" label="状态" width="90"/>
      <el-table-column label="审批">
        <template #default="scope">
          <el-button type="success" size="small" @click="approve(scope.row.id,1)">通过</el-button>
          <el-button type="danger" size="small" @click="approve(scope.row.id,2)">驳回</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { approveResultApi, resultListApi } from '../api/modules'

const list = ref([])
const load = async () => {
  const { data } = await resultListApi()
  list.value = data.data
}

const approve = async (id, status) => {
  await approveResultApi(id, { approvalStatus: status, finalComment: status === 1 ? '审批通过' : '审批驳回' })
  ElMessage.success('审批已完成')
  load()
}

onMounted(load)
</script>
