package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanLoaiRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.LoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanLoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.LoaiEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanLoaiEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhanLoaiMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.LoaiRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.PhieuPhanLoaiRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.SucoRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IPhanLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhanLoaiService implements IPhanLoaiService {
    private final PhieuPhanLoaiRepository phieuPhanLoaiRepository;

    private final SucoRepository sucoRepository;

    private final LoaiRepository loaiRepository;

    private final PhanLoaiMapper phanLoaiMapper;

    @Override
    public PhieuPhanLoaiResponse create(
            PhieuPhanLoaiRequest request
    ) {

        SucoEntity suCo =
                sucoRepository.findById(
                        request.getMaSuCo()
                ).orElseThrow(
                        () -> new RuntimeException("Không tìm thấy sự cố"));

        List<PhieuPhanLoaiEntity> entities =
                new ArrayList<>();

        for (String maLoai : request.getMaLoai()) {

            LoaiEntity loai = loaiRepository.findById(maLoai)
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy loại"));

            PhieuPhanLoaiEntity entity = new PhieuPhanLoaiEntity();

            entity.setMaSuCo(suCo.getMaSuCo());

            entity.setMaLoaiSuCo(loai.getMaLoaiSuCo());

            entity.setSuCo(suCo);

            entity.setLoai(loai);

            entity.setThoiGianTao(LocalDateTime.now());

            entities.add(entity);
        }

        phieuPhanLoaiRepository.saveAll(entities);

        return phanLoaiMapper.toResponse(
                request.getMaSuCo(),
                entities
        );
    }

    @Override
    public void delete(
            String maSuCo,
            String maLoai
    ) {

        phieuPhanLoaiRepository
                .deleteByMaSuCoAndMaLoaiSuCo(
                        maSuCo,
                        maLoai
                );
    }

    @Override
    public List<LoaiResponse> getByMaSuCo(String maSuCo) {
        return phieuPhanLoaiRepository.findAllByMaSuCo(maSuCo)
                .stream()
                .map(e -> LoaiResponse.builder()
                        .tenLoaiSuCo(e.getLoai().getTenLoaiSuCo())
                        .maLoai(e.getMaLoaiSuCo()).build())
                .collect(Collectors.toList());
    }

}
