<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2>员工绩效考核管理系统</h2>
      <el-form :model="form" @submit.prevent>
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="密码" />
        </el-form-item>
        <el-button type="primary" class="w-full" @click="login">登录</el-button>
      </el-form>
      <p class="hint">演示账号：hr_admin / 123456</p>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { loginApi } from '../api/modules'
import { useUserStore } from '../stores/user'

const router = useRouter()
const store = useUserStore()
const form = reactive({ username: 'hr_admin', password: '123456' })

const login = async () => {
  try {
    const { data } = await loginApi(form)
    if (!data.success) return ElMessage.error(data.message)
    store.setLogin(data.data)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } catch (e) {
    ElMessage.error('登录失败，请检查后端服务是否启动')
  }
}
</script>

<style scoped>
.login-page { min-height: 100vh; display:flex; align-items:center; justify-content:center; background: linear-gradient(120deg, #e0ecff, #f4f8ff); }
.login-card { width: 380px; border-radius: 16px; }
.w-full { width: 100%; }
.hint { color:#666; margin-top: 12px; font-size: 12px; }
</style>
