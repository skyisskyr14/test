package com.perf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.perf.mapper")
public class PerformanceSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(PerformanceSystemApplication.class, args);
    }
}
