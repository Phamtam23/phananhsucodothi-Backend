package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.CreateTaiKhoanRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.UpdateTaiKhoanRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.TaiKhoanResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.*;
import com.DATN.PhanAnhSuCoDoThi.repository.*;
import com.DATN.PhanAnhSuCoDoThi.service.ITaiKhoanService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaiKhoanService implements ITaiKhoanService {

    private final TaikhoanRepository taikhoanRepository;
    private final QuyenTruyCapRepository quyenTruyCapRepository;
    private final NguoidanRepository nguoidanRepository;
    private final DonViXuLyRepository donViXuLyRepository;
    private final PasswordEncoder passwordEncoder;
    private final NhanVienDieuPhoiRepository nhanVienDieuPhoiRepository;
    private final NhanVienChucVuRepsitory nhanVienChucVuRepsitory;
    private final NhanVienDonViRepository nhanVienDonViRepository;
    private final ChucVuRepository chucVuRepsitory;

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
        validateRequest(request);

        TaikhoanEntity entity = buildTaiKhoanEntity(request);
        String maQuyen = mapVaiTroToMaQuyen(request.getVaiTro());
        QuyenTruyCapEntity quyen = quyenTruyCapRepository.findById(maQuyen)
                .orElseThrow(() -> new RuntimeException("Vai trò không hợp lệ: " + maQuyen));
        entity.setQuyens(Set.of(quyen));
        taikhoanRepository.save(entity);

        switch (maQuyen) {
            case "R_USER"     -> taoNguoiDan(entity);
            case "R_DIEUPHOI" -> taoNhanVienDieuPhoi(entity);
            case "R_TXULY"    -> taoNhanVienDonVi(entity, request.getMaDonVi(), "C_TDVXL000");
            case "R_NVXULY"   -> taoNhanVienDonVi(entity, request.getMaDonVi(), "C_NDVXL000");
        }

        return toResponse(entity);
    }

    private void validateRequest(CreateTaiKhoanRequest request) {
        if (taikhoanRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email đã được sử dụng");
        if (taikhoanRepository.existsBySoDienThoai(request.getSoDienThoai()))
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        if (taikhoanRepository.existsByCccd(request.getCccd()))
            throw new RuntimeException("CCCD đã được sử dụng");
    }

    private TaikhoanEntity buildTaiKhoanEntity(CreateTaiKhoanRequest request) {
        TaikhoanEntity entity = new TaikhoanEntity();
        entity.setMaTaiKhoan(IdGenerator.generateMaTaiKhoan());
        entity.setEmail(request.getEmail());
        entity.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        entity.setHoTen(request.getHoTen());
        entity.setSoDienThoai(request.getSoDienThoai());
        entity.setCccd(request.getCccd());
        entity.setDiaChi(request.getDiaChi());
        entity.setDaXacThucEmail(false);
        entity.setTrangThai("HOAT_DONG");
        return entity;
    }

    private void taoNguoiDan(TaikhoanEntity entity) {
        NguoidanEntity nguoidan = new NguoidanEntity();
        nguoidan.setMaNguoiDan(entity.getMaTaiKhoan());
        nguoidan.setTaiKhoan(entity);
        nguoidan.setDiemUyTin(100);
        nguoidanRepository.save(nguoidan);
    }

    private void taoNhanVienDieuPhoi(TaikhoanEntity entity) {
        NhanVienDieuPhoiEntity nv = new NhanVienDieuPhoiEntity();
        nv.setTaiKhoan(entity);
        nv.setMaNhanVienDieuPhoi(entity.getMaTaiKhoan());
        nv.setTrangThai(entity.getTrangThai());
        nv.setNgayBatDau(LocalDate.now());
        nhanVienDieuPhoiRepository.save(nv);
    }

    private void taoNhanVienDonVi(TaikhoanEntity entity, String maDonVi, String maChucVu) {
        DonViXuLyEntity donViXuLy = donViXuLyRepository.findById(maDonVi)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn vị"));

        NhanVienDonViEntity nv = new NhanVienDonViEntity();
        nv.setTaiKhoan(entity);
        nv.setMaNhanVien(entity.getMaTaiKhoan());
        nv.setDonVi(donViXuLy);
        nhanVienDonViRepository.save(nv);

        taoChucVu(nv, maChucVu);
    }

    private void taoChucVu(NhanVienDonViEntity nhanVien, String maChucVu) {
        ChucVuEntity chucVu = chucVuRepsitory.findById(maChucVu)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chức vụ: " + maChucVu));

        NhanVienChucVuEntity chucVuEntity = new NhanVienChucVuEntity();
        chucVuEntity.setMaNhanVienChucVu(IdGenerator.generateMaNhanVienChucVu(nhanVien.getMaNhanVien()));
        chucVuEntity.setNhanVien(nhanVien);
        chucVuEntity.setChucVu(chucVu);
        chucVuEntity.setNgayBatDau(LocalDate.now());
        nhanVienChucVuRepsitory.save(chucVuEntity);
    }

    @Override
    public TaiKhoanResponse update(String maTaiKhoan, UpdateTaiKhoanRequest request) {
        TaikhoanEntity entity = taikhoanRepository.findById(maTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản: " + maTaiKhoan));

        if (request.getHoTen() != null) entity.setHoTen(request.getHoTen());
        if (request.getSoDienThoai() != null) entity.setSoDienThoai(request.getSoDienThoai());
        if (request.getDiaChi() != null) entity.setDiaChi(request.getDiaChi());

        if (request.getVaiTro() != null) {
            String maQuyen = mapVaiTroToMaQuyen(request.getVaiTro());

            if (!"R_TXULY".equals(maQuyen) && !"R_NVXULY".equals(maQuyen))
                throw new RuntimeException("Chỉ được cập nhật vai trò nhân viên xử lý hoặc trưởng đơn vị");

            QuyenTruyCapEntity quyen = quyenTruyCapRepository.findById(maQuyen)
                    .orElseThrow(() -> new RuntimeException("Vai trò không hợp lệ: " + maQuyen));
            entity.setQuyens(Set.of(quyen));

            NhanVienChucVuEntity chucVuCu = nhanVienChucVuRepsitory
                    .findByNhanVien_MaNhanVienAndNgayKetThucIsNull(maTaiKhoan)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chức vụ hiện tại"));
            chucVuCu.setNgayKetThuc(LocalDate.now());
            nhanVienChucVuRepsitory.save(chucVuCu);

            String maChucVuMoi = "R_TXULY".equals(maQuyen) ? "C_TDVXL000" : "C_NDVXL000";
            ChucVuEntity chucVuMoi = chucVuRepsitory.findById(maChucVuMoi)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chức vụ: " + maChucVuMoi));

            NhanVienDonViEntity nhanVien = nhanVienDonViRepository.findById(maTaiKhoan)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

            NhanVienChucVuEntity chucVuMoiEntity = new NhanVienChucVuEntity();
            chucVuMoiEntity.setNhanVien(nhanVien);
            chucVuMoiEntity.setChucVu(chucVuMoi);
            chucVuMoiEntity.setNgayBatDau(LocalDate.now());
            chucVuMoiEntity.setNgayKetThuc(null);
            nhanVienChucVuRepsitory.save(chucVuMoiEntity);
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
