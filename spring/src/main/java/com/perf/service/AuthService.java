package com.perf.service;

import com.perf.mapper.UserMapper;
import com.perf.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserMapper userMapper;

    public AuthService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null || user.getStatus() == 0) {
            return null;
        }
        return user.getPassword().equals(password) ? user : null;
    }
}
