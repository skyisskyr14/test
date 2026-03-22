<template>
  <el-row :gutter="16">
    <el-col :span="12">
      <el-card>
        <template #header>部门绩效对比（ECharts）</template>
        <div ref="deptChartRef" style="height: 320px"></div>
        <el-divider />
        <el-table :data="dept" border size="small">
          <el-table-column prop="department" label="部门" />
          <el-table-column prop="avgScore" label="平均分" />
          <el-table-column prop="rateA" label="A档占比" />
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card>
        <template #header>周期趋势分析（ECharts）</template>
        <div ref="trendChartRef" style="height: 320px"></div>
        <el-divider />
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
import { nextTick, onBeforeUnmount, onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { deptReportApi, trendReportApi } from '../api/modules'

const dept = ref([])
const trend = ref([])

const deptChartRef = ref(null)
const trendChartRef = ref(null)
let deptChart
let trendChart

function renderDeptChart() {
  if (!deptChartRef.value) return
  if (!deptChart) deptChart = echarts.init(deptChartRef.value)
  deptChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: dept.value.map(item => item.department) },
    yAxis: [
      { type: 'value', name: '平均分', min: 0, max: 100 },
      { type: 'value', name: 'A档占比(%)', min: 0, max: 100 }
    ],
    series: [
      {
        type: 'bar',
        name: '平均分',
        data: dept.value.map(item => Number(item.avgScore || 0))
      },
      {
        type: 'line',
        name: 'A档占比',
        yAxisIndex: 1,
        smooth: true,
        data: dept.value.map(item => Number(String(item.rateA || '0').replace('%', '')))
      }
    ]
  })
}

function renderTrendChart() {
  if (!trendChartRef.value) return
  if (!trendChart) trendChart = echarts.init(trendChartRef.value)
  trendChart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: trend.value.map(item => item.period) },
    yAxis: { type: 'value', name: '平均分', min: 0, max: 100 },
    series: [
      {
        type: 'line',
        name: '平均绩效分',
        smooth: true,
        areaStyle: {},
        data: trend.value.map(item => Number(item.score || 0))
      }
    ]
  })
}

function handleResize() {
  deptChart?.resize()
  trendChart?.resize()
}

onMounted(async () => {
  const d1 = await deptReportApi()
  const d2 = await trendReportApi()
  dept.value = d1.data.data || []
  trend.value = d2.data.data || []
  await nextTick()
  renderDeptChart()
  renderTrendChart()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  deptChart?.dispose()
  trendChart?.dispose()
})
</script>
