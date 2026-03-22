package com.perf.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perf.dto.ApiResponse;
import com.perf.util.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            responseUnauthorized(response);
            return false;
        }
        String[] parsed = tokenService.parse(auth.substring(7));
        if (parsed == null) {
            responseUnauthorized(response);
            return false;
        }
        request.setAttribute("userId", Long.parseLong(parsed[0]));
        request.setAttribute("username", parsed[1]);
        request.setAttribute("roleId", Long.parseLong(parsed[2]));
        return true;
    }

    private void responseUnauthorized(HttpServletResponse response) throws Exception {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail("未授权或登录已过期")));
    }
}
