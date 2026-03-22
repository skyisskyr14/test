<template>
  <el-container style="height: 100vh">
    <el-aside width="220px" class="aside">
      <h3>绩效系统</h3>
      <el-menu router>
        <el-menu-item index="/dashboard">主控台</el-menu-item>
        <el-menu-item index="/dashboard/plans">考核方案</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">官方版 · 中小企业员工绩效考核管理平台</el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.aside { background:#1f2d3d; color:#fff; padding: 12px; }
.header { background:#fff; border-bottom:1px solid #eee; font-weight:600; }
</style>
