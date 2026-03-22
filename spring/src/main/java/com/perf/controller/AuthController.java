package com.perf.controller;

import com.perf.dto.ApiResponse;
import com.perf.dto.LoginRequest;
import com.perf.model.User;
import com.perf.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.login(request.getUsername(), request.getPassword());
        if (user == null) {
            return ApiResponse.fail("用户名或密码错误");
        }
        return ApiResponse.ok(Map.of(
                "token", "demo-token-" + user.getId(),
                "user", Map.of(
                        "id", user.getId(),
                        "username", user.getUsername(),
                        "realName", user.getRealName(),
                        "roleId", user.getRoleId()
                )
        ));
    }
}
