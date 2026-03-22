package com.perf.util;

import com.perf.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;

@Service
public class TokenService {
    @Value("${security.token-secret:perf-secret-key}")
    private String secret;

    public String generate(User user) {
        long exp = Instant.now().plusSeconds(8 * 3600).getEpochSecond();
        String payload = user.getId() + ":" + user.getUsername() + ":" + user.getRoleId() + ":" + exp;
        String sign = sha256(payload + ":" + secret);
        return Base64.getEncoder().encodeToString((payload + ":" + sign).getBytes(StandardCharsets.UTF_8));
    }

    public String[] parse(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = decoded.split(":");
            if (parts.length != 5) return null;
            String payload = String.join(":", parts[0], parts[1], parts[2], parts[3]);
            String expectSign = sha256(payload + ":" + secret);
            long exp = Long.parseLong(parts[3]);
            if (!expectSign.equals(parts[4]) || Instant.now().getEpochSecond() > exp) {
                return null;
            }
            return parts;
        } catch (Exception e) {
            return null;
        }
    }

    private String sha256(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("token签名失败", e);
        }
    }
}
