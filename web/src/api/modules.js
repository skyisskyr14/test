import http from './http'

export const loginApi = (data) => http.post('/auth/login', data)
export const dashboardApi = () => http.get('/dashboard/summary')
export const planListApi = () => http.get('/plans')
export const createPlanApi = (data) => http.post('/plans', data)
export const appealListApi = () => http.get('/appeals')
export const createAppealApi = (data) => http.post('/appeals', data)
export const processAppealApi = (id, data) => http.put(`/appeals/${id}/process`, data)
export const deptReportApi = () => http.get('/reports/department')
export const trendReportApi = () => http.get('/reports/trend')
