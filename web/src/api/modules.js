import http from './http'

export const loginApi = (data) => http.post('/auth/login', data)
export const dashboardApi = () => http.get('/dashboard/summary')
export const planListApi = () => http.get('/plans')
export const createPlanApi = (data) => http.post('/plans', data)
