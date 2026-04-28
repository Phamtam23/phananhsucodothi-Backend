package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuKiemDuyet.CreatePhieuKiemDuyetRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuKiemDuyetResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuKiemDuyetEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuKiemDuyetMapper;
import com.DATN.PhanAnhSuCoDoThi.respository.PhieuKiemDuyetRepository;
import com.DATN.PhanAnhSuCoDoThi.respository.SucoRespository;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuKiemDuyetService;
import com.DATN.PhanAnhSuCoDoThi.service.ISucoService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhieuKiemDuyetService implements IPhieuKiemDuyetService {
    private final SucoRespository sucoRespository;
    private final PhieuKiemDuyetRepository phieuKiemDuyetRepository;
    private final PhieuKiemDuyetMapper phieuKiemDuyetMapper;

    @Override
    public PhieuKiemDuyetResponse create(CreatePhieuKiemDuyetRequest request) {

        SucoEntity suCo = sucoRespository.findById(request.getMaSuCo())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sự cố"));

        String refMa = SecurityUtils.getCurrentRefMa();
        PhieuKiemDuyetEntity entity = new PhieuKiemDuyetEntity();
        entity.setMaKiemDuyet(IdGenerator.generateMaPhieuKiemDuyet(refMa));
        entity.setTrangThai(request.getTrangThaiKiemDuyet());
        entity.setSuCo(suCo);

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

}
