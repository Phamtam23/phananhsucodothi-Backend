package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.LoginRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.RegisterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.AuthResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.QuyenTruyCapEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TaikhoanEntity;
import com.DATN.PhanAnhSuCoDoThi.mapper.AuthMapper;
import com.DATN.PhanAnhSuCoDoThi.respository.QuyenTruyCapRepository;
import com.DATN.PhanAnhSuCoDoThi.respository.TaikhoanRepository;
import com.DATN.PhanAnhSuCoDoThi.security.JwtUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IAuthService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final TaikhoanRepository taikhoanRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final QuyenTruyCapRepository quyenTruyCapRepository;
    private final AuthMapper authMapper;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (taikhoanRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email đã được sử dụng");
        if (taikhoanRepository.existsBySoDienThoai(request.getSoDienThoai()))
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        if (taikhoanRepository.existsByCccd(request.getCccd()))
            throw new RuntimeException("CCCD đã được sử dụng");

        String maTaiKhoan = IdGenerator.generateMaTaiKhoan();

        TaikhoanEntity taikhoan = new TaikhoanEntity();
        taikhoan.setMaTaiKhoan(maTaiKhoan);
        taikhoan.setEmail(request.getEmail());
        taikhoan.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        taikhoan.setHoTen(request.getHoTen());
        taikhoan.setSoDienThoai(request.getSoDienThoai());
        taikhoan.setCccd(request.getCccd());
        taikhoan.setDiaChi(request.getDiaChi());
        taikhoan.setDaXacThucEmail(false);

        QuyenTruyCapEntity roleUser = quyenTruyCapRepository.findById("R_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        taikhoan.setQuyens(Set.of(roleUser));

        taikhoanRepository.save(taikhoan);

        List<String> quyens = List.of();

        String token = jwtUtils.generateToken(
                taikhoan.getEmail(),
                taikhoan.getMaTaiKhoan(),
                quyens,
                null
        );

        return authMapper.toResponse(taikhoan, token);

    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // Tìm tài khoản
        TaikhoanEntity taikhoan = taikhoanRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(request.getMatKhau(), taikhoan.getMatKhau()))
            throw new RuntimeException("Mật khẩu không đúng");

        // Lấy danh sách quyền
        List<String> quyens = taikhoan.getQuyens() == null ? List.of() :
                taikhoan.getQuyens().stream()
                        .map(q -> q.getMaQuyen())
                        .toList();

        String token = jwtUtils.generateToken(
                taikhoan.getEmail(),
                taikhoan.getMaTaiKhoan(),
                quyens,
                null
        );

        return authMapper.toResponse(taikhoan, token);
    }
}
