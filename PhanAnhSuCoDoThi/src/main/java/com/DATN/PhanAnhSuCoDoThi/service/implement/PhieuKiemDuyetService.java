package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuKiemDuyet.CreatePhieuKiemDuyetRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuKiemDuyetResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDieuPhoiEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuKiemDuyetEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKiemDuyet;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuKiemDuyetMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.NhanVienDieuPhoiRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.PhieuKiemDuyetRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.SucoRepository;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuKiemDuyetService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhieuKiemDuyetService implements IPhieuKiemDuyetService {
    private final SucoRepository sucoRepository;
    private final PhieuKiemDuyetRepository phieuKiemDuyetRepository;
    private final PhieuKiemDuyetMapper phieuKiemDuyetMapper;
    private final NhanVienDieuPhoiRepository  nhanVienDieuPhoiRepository;
    @Override
    public PhieuKiemDuyetResponse create(CreatePhieuKiemDuyetRequest request) {

        SucoEntity suCo = sucoRepository.findById(request.getMaSuCo())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sự cố"));

        String refMa = SecurityUtils.getCurrentRefMa();
        NhanVienDieuPhoiEntity nhanVienDieuPhoiEntity = nhanVienDieuPhoiRepository.findById(refMa)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
        PhieuKiemDuyetEntity entity = new PhieuKiemDuyetEntity();
        entity.setMaKiemDuyet(IdGenerator.generateMaPhieuKiemDuyet(refMa));
        entity.setTrangThai(request.getTrangThaiKiemDuyet());
        entity.setNhanVienDieuPhoi(nhanVienDieuPhoiEntity);
        entity.setLyDoTuChoi(request.getLyDoTuChoi());
        entity.setThoiGianTao(LocalDateTime.now());
        entity.setSuCo(suCo);
        if(entity.getTrangThai()== TrangThaiKiemDuyet.DUYET)
        {
            suCo.setTrangThai(TrangThaiSuCo.DA_TIEP_NHAN);
        }
        else
        {
            suCo.setTrangThai(TrangThaiSuCo.TU_CHOI);
        }
        phieuKiemDuyetRepository.save(entity);

        return phieuKiemDuyetMapper.toResponse(entity);
    }

    @Override
    public PhieuKiemDuyetResponse getByMa(String maKiemDuyet) {
        PhieuKiemDuyetEntity entity = phieuKiemDuyetRepository.findById(maKiemDuyet)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu kiểm duyệt"));
        return phieuKiemDuyetMapper.toResponse(entity);
    }

    @Override
    public List<PhieuKiemDuyetResponse> getByMaSuCo(String maSuCo) {
        return phieuKiemDuyetRepository.findBySuCo_MaSuCo(maSuCo)
                .stream()
                .map(phieuKiemDuyetMapper::toResponse)
                .toList();
    }

    @Override
    public PageResponse<PhieuKiemDuyetResponse> getByNhanVien(
            String maNhanVien, int page, int size,
            LocalDate tuNgay, LocalDate denNgay
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());

        Page<PhieuKiemDuyetEntity> pageResult = phieuKiemDuyetRepository
                .findAllByFilter(maNhanVien, tuNgay, denNgay, pageable);

        return PageResponse.of(pageResult.map(pkt -> {
            PhieuKiemDuyetResponse response = phieuKiemDuyetMapper.toResponse(pkt);
            if (response == null) return null;
            response.setTieuDe(pkt.getSuCo().getTieuDe());
            response.setDiaDiem(pkt.getSuCo().getDiaDiem());
            return response;
        }));
    }

}
