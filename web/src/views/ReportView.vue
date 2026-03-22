<template>
  <el-row :gutter="16" v-loading="loading">
    <el-col :span="12">
      <el-card>
        <template #header>
          <div class="between">
            <span>部门绩效对比（ECharts）</span>
            <el-button size="small" @click="exportDeptCsv">导出CSV</el-button>
          </div>
        </template>
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
        <template #header>
          <div class="between">
            <span>周期趋势分析（ECharts）</span>
            <el-button size="small" @click="exportTrendCsv">导出CSV</el-button>
          </div>
        </template>
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
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'
import { deptReportApi, trendReportApi } from '../api/modules'

const dept = ref([])
const trend = ref([])
const loading = ref(false)

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
      { type: 'bar', name: '平均分', data: dept.value.map(item => Number(item.avgScore || 0)) },
      { type: 'line', name: 'A档占比', yAxisIndex: 1, smooth: true, data: dept.value.map(item => Number(String(item.rateA || '0').replace('%', ''))) }
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
      { type: 'line', name: '平均绩效分', smooth: true, areaStyle: {}, data: trend.value.map(item => Number(item.score || 0)) }
    ]
  })
}

function downloadCsv(filename, rows) {
  if (!rows.length) return ElMessage.warning('暂无数据可导出')
  const header = Object.keys(rows[0]).join(',')
  const body = rows.map(row => Object.values(row).join(',')).join('\n')
  const blob = new Blob([`${header}\n${body}`], { type: 'text/csv;charset=utf-8;' })
  const link = document.createElement('a')
  link.href = URL.createObjectURL(blob)
  link.download = filename
  link.click()
  URL.revokeObjectURL(link.href)
}

const exportDeptCsv = () => downloadCsv('department-report.csv', dept.value)
const exportTrendCsv = () => downloadCsv('trend-report.csv', trend.value)

function handleResize() {
  deptChart?.resize()
  trendChart?.resize()
}

onMounted(async () => {
  loading.value = true
  try {
    const d1 = await deptReportApi()
    const d2 = await trendReportApi()
    dept.value = d1.data.data || []
    trend.value = d2.data.data || []
    await nextTick()
    renderDeptChart()
    renderTrendChart()
    window.addEventListener('resize', handleResize)
  } finally {
    loading.value = false
  }
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  deptChart?.dispose()
  trendChart?.dispose()
})
</script>

<style scoped>
.between { display: flex; justify-content: space-between; align-items: center; }
</style>
