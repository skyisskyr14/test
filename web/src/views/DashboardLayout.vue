<template>
  <el-container style="height: 100vh">
    <el-aside width="240px" class="aside">
      <h3>绩效系统</h3>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">主控台</el-menu-item>
        <el-menu-item index="/dashboard/plans">考核方案</el-menu-item>
        <el-menu-item index="/dashboard/indicators">指标管理</el-menu-item>
        <el-menu-item index="/dashboard/tasks">我的任务</el-menu-item>
        <el-menu-item index="/dashboard/self-score">自评打分</el-menu-item>
        <el-menu-item index="/dashboard/approvals">结果审批</el-menu-item>
        <el-menu-item index="/dashboard/appeals">申诉处理</el-menu-item>
        <el-menu-item index="/dashboard/reports">数据分析</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <span>官方版 · 中小企业员工绩效考核管理平台</span>
        <el-button size="small" @click="logout">退出登录</el-button>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

const router = useRouter()
const store = useUserStore()
const logout = () => {
  store.logout()
  router.push('/')
}
</script>

<style scoped>
.aside { background:#1f2d3d; color:#fff; padding: 12px; }
.header { background:#fff; border-bottom:1px solid #eee; font-weight:600; display:flex; justify-content:space-between; align-items:center; }
</style>
