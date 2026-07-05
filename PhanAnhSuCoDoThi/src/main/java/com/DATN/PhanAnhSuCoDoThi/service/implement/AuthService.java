package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.LoginRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.RegisterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.UpdateProfileRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.AuthResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ProfileResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.*;
import com.DATN.PhanAnhSuCoDoThi.mapper.AuthMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.*;
import com.DATN.PhanAnhSuCoDoThi.security.JwtUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IAuthService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final TaikhoanRepository taikhoanRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final QuyenTruyCapRepository quyenTruyCapRepository;
    private final AuthMapper authMapper;
    private final NguoidanRepository nguoidanRepository;
    private final NhanVienDieuPhoiRepository nhanVienDieuPhoiRepository;
    private final NhanVienDonViRepository nhanVienDonViRepository;
    private final NhanVienChucVuRepsitory nhanVienChucVuRepsitory;

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

        NguoidanEntity nguoidan = new NguoidanEntity();
        nguoidan.setDiemUyTin(100);
        nguoidan.setTaiKhoan(taikhoan);
        nguoidan.setMaNguoiDan(maTaiKhoan);

        nguoidanRepository.save(nguoidan);

        String token = jwtUtils.generateToken(
                taikhoan.getEmail(),
                taikhoan.getMaTaiKhoan(),
                quyens,
                null
        );

        return authMapper.toResponse(taikhoan, token, "R_USER");

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

        String refMa = null;
        String role = null;

        if (quyens.contains("R_Admin")) {

            role = "R_Admin";

        } else if (quyens.contains("R_TXULY")) {

            role = "R_TXULY";

            refMa = nhanVienDonViRepository
                    .findByTaiKhoan_MaTaiKhoan(taikhoan.getMaTaiKhoan())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy trưởng đơn vị"))
                    .getMaNhanVien();

        } else if (quyens.contains("R_NVXULY")) {

            role = "R_NVXULY";

            refMa = nhanVienDonViRepository
                    .findByTaiKhoan_MaTaiKhoan(taikhoan.getMaTaiKhoan())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy NV xử lý"))
                    .getMaNhanVien();

        } else if (quyens.contains("R_DIEUPHOI")) {

            role = "R_DIEUPHOI";

            refMa = nhanVienDieuPhoiRepository
                    .findByTaiKhoan_MaTaiKhoan(taikhoan.getMaTaiKhoan())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy NV điều phối"))
                    .getMaNhanVienDieuPhoi();

        } else if (quyens.contains("R_USER")) {

            role = "R_USER";

            refMa = nguoidanRepository
                    .findByTaiKhoan_MaTaiKhoan(taikhoan.getMaTaiKhoan())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dân"))
                    .getMaNguoiDan();

        } else {
            throw new RuntimeException("Role không hợp lệ");
        }

        String token = jwtUtils.generateToken(
                taikhoan.getEmail(),
                taikhoan.getMaTaiKhoan(),
                quyens,
                refMa
        );

        return authMapper.toResponse(taikhoan, token, role);
    }

    @Override
    public String upDateProfile(UpdateProfileRequest updateProfileRequest,String maTaiKhoan) {

       TaikhoanEntity taiKhoan = taikhoanRepository.findById(maTaiKhoan).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        taiKhoan.setDiaChi(updateProfileRequest.getDiaChi());
        taiKhoan.setEmail(updateProfileRequest.getEmail());
        taiKhoan.setAnhDaiDien(updateProfileRequest.getAnhDaiDien());
        taiKhoan.setHoTen(updateProfileRequest.getHoTen());

        taikhoanRepository.save(taiKhoan);

        return "Đã cập nhật thành công" ;

    }

    @Override
    public ProfileResponse getProfile(String maTaiKhoan) {
        // Tìm tài khoản
        TaikhoanEntity taikhoan = taikhoanRepository.findById(maTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        // Lấy danh sách quyền
        List<String> quyens = taikhoan.getQuyens() == null ? List.of() :
                taikhoan.getQuyens().stream()
                        .map(q -> q.getMaQuyen())
                        .toList();

        String refMa = null;
        String role = null;
        String chucVu = null;
        LocalDate ngayBatDau = null;

        ProfileResponse profileResponse = ProfileResponse.builder()

                .maTaiKhoan(maTaiKhoan)
                .hoTen(taikhoan.getHoTen())
                .email(taikhoan.getEmail())
                .anhDaiDien(taikhoan.getAnhDaiDien())
                .diaChi(taikhoan.getDiaChi())
                .build();
        if (quyens.contains("R_Admin")) {
            role = "R_Admin";

        } else if (quyens.contains("R_TXULY") || quyens.contains("R_NVXULY")) {
            role = quyens.contains("R_TXULY") ? "R_TXULY" : "R_NVXULY"; {

            refMa = nhanVienDonViRepository
                    .findByTaiKhoan_MaTaiKhoan(taikhoan.getMaTaiKhoan())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy trưởng đơn vị"))
                    .getMaNhanVien();

                NhanVienChucVuEntity nhanVienChucVu = nhanVienChucVuRepsitory.findByNhanVien_MaNhanVienAndNgayKetThucIsNull(refMa)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy chức vụ của nhân viên"));

                chucVu = nhanVienChucVu.getChucVu().getTenChucVu();
                ngayBatDau = nhanVienChucVu.getNgayBatDau();
        }

        } else if (quyens.contains("R_DIEUPHOI")) {

            chucVu = "Nhân viên điều phối";
            NhanVienDieuPhoiEntity nhanVienDieuPhoiEntity = nhanVienDieuPhoiRepository.findByTaiKhoan_MaTaiKhoan(maTaiKhoan)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy được nhân viên"));

            ngayBatDau =nhanVienDieuPhoiEntity.getNgayBatDau();


        } else if (quyens.contains("R_USER")) {
            chucVu = "Người dân";
        } else {
            throw new RuntimeException("Role không hợp lệ");
        }
        profileResponse.setChucVu(chucVu);
        profileResponse.setNgayBatDau(ngayBatDau);



        return profileResponse;
    }
}
