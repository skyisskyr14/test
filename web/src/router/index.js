import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import DashboardLayout from '../views/DashboardLayout.vue'
import HomeView from '../views/HomeView.vue'
import PlanView from '../views/PlanView.vue'
import IndicatorView from '../views/IndicatorView.vue'
import TaskView from '../views/TaskView.vue'
import SelfScoreView from '../views/SelfScoreView.vue'
import ApprovalView from '../views/ApprovalView.vue'
import AppealView from '../views/AppealView.vue'
import ReportView from '../views/ReportView.vue'
import RoadmapView from '../views/RoadmapView.vue'
import { canAccess, ROLE } from '../constants/navigation'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: LoginView },
    {
      path: '/dashboard',
      component: DashboardLayout,
      children: [
        { path: '', component: HomeView },
        { path: 'plans', component: PlanView },
        { path: 'indicators', component: IndicatorView },
        { path: 'tasks', component: TaskView },
        { path: 'self-score', component: SelfScoreView },
        { path: 'approvals', component: ApprovalView },
        { path: 'appeals', component: AppealView },
        { path: 'reports', component: ReportView },
        { path: 'roadmap', component: RoadmapView }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/' && !token) {
    return next('/')
  }
  if (to.path.startsWith('/dashboard')) {
    const user = JSON.parse(localStorage.getItem('user') || 'null')
    const roleId = Number(user?.roleId || ROLE.EMPLOYEE)
    if (!canAccess(roleId, to.path)) {
      return next('/dashboard')
    }
  }
  next()
})

export default router
