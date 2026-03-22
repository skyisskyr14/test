<template>
  <el-card>
    <template #header>
      <div class="between">
        <span>员工自评打分</span>
        <div>
          <el-input-number v-model="taskId" :min="1" />
          <el-button type="primary" style="margin-left:8px" @click="load">加载指标</el-button>
        </div>
      </div>
    </template>

    <el-table :data="items" border>
      <el-table-column prop="indicatorName" label="指标"/>
      <el-table-column prop="assignedWeight" label="权重" width="100"/>
      <el-table-column label="自评分" width="180">
        <template #default="scope">
          <el-input-number v-model="scope.row.score" :min="0" :max="100"/>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top:12px; text-align:right">
      <el-button type="success" @click="submit">提交自评并生成结果</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { scoreItemsApi, submitScoresApi } from '../api/modules'

const taskId = ref(1)
const items = ref([])

const load = async () => {
  const { data } = await scoreItemsApi(taskId.value)
  items.value = data.data.map(i => ({ ...i, score: i.score ?? 80 }))
}

const submit = async () => {
  const payload = items.value.map(i => ({ indicatorId: i.indicatorId, score: i.score }))
  await submitScoresApi(taskId.value, payload)
  ElMessage.success('提交成功')
}
</script>

<style scoped>
.between {display:flex; justify-content:space-between; align-items:center;}
</style>
