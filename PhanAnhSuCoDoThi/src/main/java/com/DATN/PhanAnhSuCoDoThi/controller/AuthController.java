package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.LoginRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.RegisterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.AuthResponse;
import com.DATN.PhanAnhSuCoDoThi.service.IAuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        AuthResponse response = authService.register(request);

        return ResponseEntity.status(201)
                .body(ApiSuccessResponse.created(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiSuccessResponse.ok(response)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiSuccessResponse<Void>> logout() {

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(
                ApiSuccessResponse.success(
                        200,
                        "Đăng xuất thành công",
                        null
                )
        );
    }
}