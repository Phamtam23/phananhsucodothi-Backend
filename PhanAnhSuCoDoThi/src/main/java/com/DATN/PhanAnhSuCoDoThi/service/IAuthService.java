package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.LoginRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.RegisterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.AuthResponse;

public interface IAuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

}
