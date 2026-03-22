<template>
  <div class="login-page">
    <div class="login-bg"></div>
    <el-card class="login-card" shadow="hover">
      <template #header>
        <div class="title-row">
          <div>
            <h2>员工绩效考核管理系统</h2>
            <p class="sub">Performance Management Platform</p>
          </div>
          <el-tag :type="healthType" effect="dark">{{ healthText }}</el-tag>
        </div>
      </template>

      <el-form :model="form" @submit.prevent>
        <el-form-item>
          <el-input v-model="form.username" size="large" placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" size="large" type="password" show-password placeholder="密码" />
        </el-form-item>
        <el-button type="primary" size="large" class="w-full" :loading="loading" @click="login">登录</el-button>
      </el-form>

      <div class="hint-wrap">
        <p class="hint">演示账号：hr_admin / 123456</p>
        <el-link type="primary" :underline="false" @click="refreshHealth">重新检测后端状态</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { healthApi, loginApi } from '../api/modules'
import { useUserStore } from '../stores/user'

const router = useRouter()
const store = useUserStore()
const form = reactive({ username: 'hr_admin', password: '123456' })
const loading = ref(false)
const backendReady = ref(false)

const healthText = computed(() => (backendReady.value ? '后端在线' : '后端离线'))
const healthType = computed(() => (backendReady.value ? 'success' : 'danger'))

const refreshHealth = async () => {
  try {
    const { data } = await healthApi()
    backendReady.value = Boolean(data?.success)
  } catch {
    backendReady.value = false
  }
}

const login = async () => {
  if (!backendReady.value) {
    ElMessage.error('后端未就绪，请先启动后端服务')
    return
  }
  loading.value = true
  try {
    const { data } = await loginApi(form)
    if (!data.success) return ElMessage.error(data.message)
    store.setLogin(data.data)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } catch {
    ElMessage.error('登录失败，请检查后端服务与数据库连接')
  } finally {
    loading.value = false
  }
}

onMounted(refreshHealth)
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: radial-gradient(circle at 20% 20%, #dbeafe 0%, #eff6ff 36%, #f8fafc 100%);
  position: relative;
  overflow: hidden;
}

.login-bg {
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.18), transparent 40%),
    linear-gradient(315deg, rgba(16, 185, 129, 0.15), transparent 45%);
}

.login-card {
  width: 420px;
  border-radius: 18px;
  position: relative;
  z-index: 1;
}

.title-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

h2 {
  margin: 0;
  font-size: 22px;
}

.sub {
  margin: 4px 0 0;
  color: #64748b;
  font-size: 12px;
}

.w-full {
  width: 100%;
}

.hint-wrap {
  margin-top: 12px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.hint {
  color: #64748b;
  margin: 0;
  font-size: 12px;
}
</style>
