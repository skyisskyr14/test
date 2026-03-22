package com.perf.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RoleGuard {
    public void hrOrLeader(HttpServletRequest request) {
        Long roleId = (Long) request.getAttribute("roleId");
        if (roleId == null || (roleId != 2L && roleId != 3L)) {
            throw new IllegalArgumentException("权限不足，仅HR或管理层可操作");
        }
    }

    public void leaderOnly(HttpServletRequest request) {
        Long roleId = (Long) request.getAttribute("roleId");
        if (roleId == null || roleId != 3L) {
            throw new IllegalArgumentException("权限不足，仅管理层可操作");
        }
    }
}
