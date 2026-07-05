package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.LoginRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.RegisterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.UpdateProfileRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.AuthResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ProfileResponse;

public interface IAuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    String upDateProfile(UpdateProfileRequest updateProfileRequest, String maTaiKhoan);

    ProfileResponse getProfile(String maTaiKhoan);

}
