import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import DashboardLayout from '../views/DashboardLayout.vue'
import HomeView from '../views/HomeView.vue'
import PlanView from '../views/PlanView.vue'
import AppealView from '../views/AppealView.vue'
import ReportView from '../views/ReportView.vue'

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
        { path: 'appeals', component: AppealView },
        { path: 'reports', component: ReportView }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.path !== '/' && !localStorage.getItem('token')) {
    return next('/')
  }
  next()
})

export default router
