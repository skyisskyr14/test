package com.perf.interceptor;

import com.perf.model.OperationLog;
import com.perf.service.OperationLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class OperationLogInterceptor implements HandlerInterceptor {
    private final OperationLogService operationLogService;

    public OperationLogInterceptor(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String uri = request.getRequestURI();
        if (!uri.startsWith("/api") || uri.startsWith("/api/health")) return;

        OperationLog log = new OperationLog();
        Object uid = request.getAttribute("userId");
        log.setUserId(uid instanceof Long ? (Long) uid : 0L);
        log.setOperation("API访问");
        String[] split = uri.split("/");
        log.setModule(split.length > 2 ? split[2] : "unknown");
        log.setRequestUrl(uri);
        log.setRequestMethod(request.getMethod());
        log.setRequestParams(request.getQueryString());
        log.setIpAddress(request.getRemoteAddr());
        log.setStatus(ex == null && response.getStatus() < 400 ? 1 : 0);
        log.setErrorMessage(ex == null ? null : ex.getMessage());
        operationLogService.save(log);
    }
}
