<template>
  <el-row :gutter="16">
    <el-col :span="12">
      <el-card>
        <template #header>部门绩效对比</template>
        <el-table :data="dept" border>
          <el-table-column prop="department" label="部门" />
          <el-table-column prop="avgScore" label="平均分" />
          <el-table-column prop="rateA" label="A档占比" />
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card>
        <template #header>周期趋势分析</template>
        <el-timeline>
          <el-timeline-item v-for="item in trend" :key="item.period" :timestamp="item.period">
            平均绩效分：{{ item.score }}
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { deptReportApi, trendReportApi } from '../api/modules'

const dept = ref([])
const trend = ref([])

onMounted(async () => {
  const d1 = await deptReportApi()
  const d2 = await trendReportApi()
  dept.value = d1.data.data
  trend.value = d2.data.data
})
</script>
