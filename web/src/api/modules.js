import http from './http'

export const loginApi = (data) => http.post('/auth/login', data)
export const dashboardApi = () => http.get('/dashboard/summary')

export const planListApi = () => http.get('/plans')
export const createPlanApi = (data) => http.post('/plans', data)
export const publishPlanApi = (id) => http.post(`/plans/${id}/publish`)
export const assignPlanIndicatorsApi = (id, data) => http.post(`/plans/${id}/assign-indicators`, data)
export const assignPlanParticipantsApi = (id, data) => http.post(`/plans/${id}/assign-participants`, data)

export const indicatorListApi = () => http.get('/indicators')
export const createIndicatorApi = (data) => http.post('/indicators', data)
export const deleteIndicatorApi = (id) => http.delete(`/indicators/${id}`)

export const myTaskApi = () => http.get('/tasks/mine')
export const submitTaskApi = (id) => http.post(`/tasks/${id}/self-submit`)
export const scoreItemsApi = (taskId) => http.get(`/tasks/${taskId}/score-items`)
export const submitScoresApi = (taskId, data) => http.post(`/tasks/${taskId}/scores`, data)

export const resultListApi = () => http.get('/results')
export const approveResultApi = (id, data) => http.put(`/results/${id}/approve`, data)

export const appealListApi = () => http.get('/appeals')
export const createAppealApi = (data) => http.post('/appeals', data)
export const processAppealApi = (id, data) => http.put(`/appeals/${id}/process`, data)

export const deptReportApi = () => http.get('/reports/department')
export const trendReportApi = () => http.get('/reports/trend')

export const healthApi = () => http.get('/health')
