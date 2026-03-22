package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.dto.LoginRequest;
import com.perf.model.User;
import com.perf.service.AuthService;
import com.perf.util.TokenService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.login(request.getUsername(), request.getPassword());
        if (user == null) {
            return ApiResponse.fail("用户名或密码错误");
        }
        return ApiResponse.ok(Map.of(
                "token", tokenService.generate(user),
                "expireInHours", 8,
                "loginAt", Instant.now().toString(),
                "user", Map.of(
                        "id", user.getId(),
                        "username", user.getUsername(),
                        "realName", user.getRealName(),
                        "roleId", user.getRoleId()
                )
        ));
    }
}
