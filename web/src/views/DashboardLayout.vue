<template>
  <el-container style="height: 100vh">
    <el-aside width="240px" class="aside">
      <div class="brand">
        <h3>绩效系统</h3>
        <small>{{ roleName }}</small>
      </div>
      <el-menu router :default-active="$route.path" background-color="#0f172a" text-color="#cbd5e1" active-text-color="#60a5fa">
        <el-menu-item v-for="item in menus" :key="item.path" :index="item.path">
          {{ item.label }}
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <span>官方版 · 中小企业员工绩效考核管理平台</span>
        <div class="header-right">
          <el-tag type="info" effect="plain">{{ currentUser?.realName || currentUser?.username }}</el-tag>
          <el-button size="small" @click="logout">退出登录</el-button>
        </div>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { getMenusByRole, ROLE } from '../constants/navigation'

const router = useRouter()
const store = useUserStore()

const currentUser = computed(() => store.user)
const roleId = computed(() => Number(store.user?.roleId || ROLE.EMPLOYEE))
const menus = computed(() => getMenusByRole(roleId.value))
const roleName = computed(() => {
  if (roleId.value === ROLE.HR) return 'HR角色'
  if (roleId.value === ROLE.LEADER) return '管理层角色'
  return '员工角色'
})

const logout = () => {
  store.logout()
  router.push('/')
}
</script>

<style scoped>
.aside {
  background: #0f172a;
  color: #fff;
  padding: 12px;
}

.brand {
  padding: 8px 8px 16px;
  border-bottom: 1px solid rgba(148, 163, 184, 0.25);
  margin-bottom: 12px;
}

.brand h3 {
  margin: 0;
  font-size: 18px;
}

.brand small {
  color: #94a3b8;
}

.header {
  background: #fff;
  border-bottom: 1px solid #e2e8f0;
  font-weight: 600;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>
