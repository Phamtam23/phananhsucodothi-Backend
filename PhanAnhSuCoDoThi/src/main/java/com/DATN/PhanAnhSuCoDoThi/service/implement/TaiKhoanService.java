package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.CreateTaiKhoanRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.UpdateTaiKhoanRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.TaiKhoanResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.TaikhoanEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.NguoidanEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.QuyenTruyCapEntity;
import com.DATN.PhanAnhSuCoDoThi.repository.TaikhoanRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.QuyenTruyCapRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.NguoidanRepository;
import com.DATN.PhanAnhSuCoDoThi.service.ITaiKhoanService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaiKhoanService implements ITaiKhoanService {

    private final TaikhoanRepository taikhoanRepository;
    private final QuyenTruyCapRepository quyenTruyCapRepository;
    private final NguoidanRepository nguoidanRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<TaiKhoanResponse> findAll() {
        return taikhoanRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaiKhoanResponse findById(String maTaiKhoan) {
        TaikhoanEntity entity = taikhoanRepository.findById(maTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản: " + maTaiKhoan));
        return toResponse(entity);
    }

    @Override
    public TaiKhoanResponse create(CreateTaiKhoanRequest request) {
        if (taikhoanRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email đã được sử dụng");
        if (taikhoanRepository.existsBySoDienThoai(request.getSoDienThoai()))
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        if (taikhoanRepository.existsByCccd(request.getCccd()))
            throw new RuntimeException("CCCD đã được sử dụng");

        String maTaiKhoan = IdGenerator.generateMaTaiKhoan();

        TaikhoanEntity entity = new TaikhoanEntity();
        entity.setMaTaiKhoan(maTaiKhoan);
        entity.setEmail(request.getEmail());
        entity.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        entity.setHoTen(request.getHoTen());
        entity.setSoDienThoai(request.getSoDienThoai());
        entity.setCccd(request.getCccd());
        entity.setDiaChi(request.getDiaChi());
        entity.setDaXacThucEmail(false);
        entity.setTrangThai("HOAT_DONG");

        // Gán quyền theo vaiTro
        String maQuyen = mapVaiTroToMaQuyen(request.getVaiTro());
        QuyenTruyCapEntity quyen = quyenTruyCapRepository.findById(maQuyen)
                .orElseThrow(() -> new RuntimeException("Vai trò không hợp lệ: " + maQuyen));
        entity.setQuyens(Set.of(quyen));

        taikhoanRepository.save(entity);

        // Nếu là người dân, tạo bản ghi NguoiDan
        if ("R_USER".equals(maQuyen)) {
            NguoidanEntity nguoidan = new NguoidanEntity();
            nguoidan.setMaNguoiDan(maTaiKhoan);
            nguoidan.setTaiKhoan(entity);
            nguoidan.setDiemUyTin(100);
            nguoidanRepository.save(nguoidan);
        }

        return toResponse(entity);
    }

    @Override
    public TaiKhoanResponse update(String maTaiKhoan, UpdateTaiKhoanRequest request) {
        TaikhoanEntity entity = taikhoanRepository.findById(maTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản: " + maTaiKhoan));

        if (request.getHoTen() != null) entity.setHoTen(request.getHoTen());
        if (request.getSoDienThoai() != null) entity.setSoDienThoai(request.getSoDienThoai());
        if (request.getDiaChi() != null) entity.setDiaChi(request.getDiaChi());

        // Cập nhật vaiTro nếu có
        if (request.getVaiTro() != null) {
            String maQuyen = mapVaiTroToMaQuyen(request.getVaiTro());
            QuyenTruyCapEntity quyen = quyenTruyCapRepository.findById(maQuyen)
                    .orElseThrow(() -> new RuntimeException("Vai trò không hợp lệ: " + maQuyen));
            entity.setQuyens(Set.of(quyen));
        }

        taikhoanRepository.save(entity);
        return toResponse(entity);
    }

    @Override
    public TaiKhoanResponse khoa(String maTaiKhoan) {
        TaikhoanEntity entity = taikhoanRepository.findById(maTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản: " + maTaiKhoan));
        entity.setTrangThai("BI_KHOA");
        taikhoanRepository.save(entity);
        return toResponse(entity);
    }

    @Override
    public TaiKhoanResponse moKhoa(String maTaiKhoan) {
        TaikhoanEntity entity = taikhoanRepository.findById(maTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản: " + maTaiKhoan));
        entity.setTrangThai("HOAT_DONG");
        taikhoanRepository.save(entity);
        return toResponse(entity);
    }

    // ---------- helpers ----------

    private TaiKhoanResponse toResponse(TaikhoanEntity entity) {
        String vaiTro = "NGUOI_DAN";
        if (entity.getQuyens() != null && !entity.getQuyens().isEmpty()) {
            String maQuyen = entity.getQuyens().iterator().next().getMaQuyen();
            vaiTro = mapMaQuyenToVaiTro(maQuyen);
        }
        return TaiKhoanResponse.builder()
                .maTaiKhoan(entity.getMaTaiKhoan())
                .email(entity.getEmail())
                .hoTen(entity.getHoTen())
                .soDienThoai(entity.getSoDienThoai())
                .cccd(entity.getCccd())
                .diaChi(entity.getDiaChi())
                .anhDaiDien(entity.getAnhDaiDien())
                .vaiTro(vaiTro)
                .trangThai(entity.getTrangThai() != null ? entity.getTrangThai() : "HOAT_DONG")
                .build();
    }

    private String mapVaiTroToMaQuyen(String vaiTro) {
        return switch (vaiTro) {
            case "ADMIN" -> "R_Admin";
            case "NHAN_VIEN_DIEU_PHOI" -> "R_DIEUPHOI";
            case "TRUONG_DON_VI" -> "R_TXULY";
            case "NHAN_VIEN_XU_LY" -> "R_NVXULY";
            default -> "R_USER";
        };
    }

    private String mapMaQuyenToVaiTro(String maQuyen) {
        return switch (maQuyen) {
            case "R_Admin" -> "ADMIN";
            case "R_DIEUPHOI" -> "NHAN_VIEN_DIEU_PHOI";
            case "R_TXULY" -> "TRUONG_DON_VI";
            case "R_NVXULY" -> "NHAN_VIEN_XU_LY";
            default -> "NGUOI_DAN";
        };
    }
}
