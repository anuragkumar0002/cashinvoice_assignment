package com.cashinvoice.order.controller;

import com.cashinvoice.order.dto.AuthRequest;
import com.cashinvoice.order.security.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest request) {

        // dummy users
        if ("admin".equals(request.getUsername())) {
            return Map.of("token",
                    jwtUtil.generateToken("admin", "ADMIN"));
        }

        return Map.of("token",
                jwtUtil.generateToken(request.getUsername(), "USER"));
    }
}
