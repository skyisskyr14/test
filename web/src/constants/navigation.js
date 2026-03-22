export const ROLE = {
  EMPLOYEE: 1,
  HR: 2,
  LEADER: 3
}

export const ALL_MENUS = [
  { label: '主控台', path: '/dashboard', roles: [ROLE.EMPLOYEE, ROLE.HR, ROLE.LEADER] },
  { label: '考核方案', path: '/dashboard/plans', roles: [ROLE.HR, ROLE.LEADER] },
  { label: '指标管理', path: '/dashboard/indicators', roles: [ROLE.HR] },
  { label: '我的任务', path: '/dashboard/tasks', roles: [ROLE.EMPLOYEE, ROLE.HR, ROLE.LEADER] },
  { label: '自评打分', path: '/dashboard/self-score', roles: [ROLE.EMPLOYEE] },
  { label: '结果审批', path: '/dashboard/approvals', roles: [ROLE.LEADER] },
  { label: '申诉处理', path: '/dashboard/appeals', roles: [ROLE.EMPLOYEE, ROLE.HR] },
  { label: '数据分析', path: '/dashboard/reports', roles: [ROLE.HR, ROLE.LEADER] },
  { label: '项目进度', path: '/dashboard/roadmap', roles: [ROLE.EMPLOYEE, ROLE.HR, ROLE.LEADER] },
  { label: '系统日志', path: '/dashboard/system-logs', roles: [ROLE.HR, ROLE.LEADER] }
]

export const getMenusByRole = (roleId) => ALL_MENUS.filter((item) => item.roles.includes(roleId))

export const canAccess = (roleId, targetPath) => {
  const routeMenu = ALL_MENUS.find((item) => item.path === targetPath)
  if (!routeMenu) return true
  return routeMenu.roles.includes(roleId)
}
