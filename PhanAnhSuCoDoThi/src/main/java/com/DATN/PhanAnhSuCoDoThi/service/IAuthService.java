package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.LoginRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.RegisterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.AuthResponse;

public interface IAuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

}
