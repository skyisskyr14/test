import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import DashboardLayout from '../views/DashboardLayout.vue'
import HomeView from '../views/HomeView.vue'
import PlanView from '../views/PlanView.vue'

export default createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: LoginView },
    {
      path: '/dashboard',
      component: DashboardLayout,
      children: [
        { path: '', component: HomeView },
        { path: 'plans', component: PlanView }
      ]
    }
  ]
})
