package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.response.LoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.LoaiEntity;
import com.DATN.PhanAnhSuCoDoThi.mapper.LoaiMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.LoaiRepository;
import com.DATN.PhanAnhSuCoDoThi.service.ILoaiService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoaiService implements ILoaiService {

    private final LoaiRepository loaiRepository;
    private final LoaiMapper loaiMapper;

    @Override
    public LoaiResponse create(
            String tenLoai
    ) {

        LoaiEntity entity =
                new LoaiEntity();

        entity.setMaLoaiSuCo(
                UUID.randomUUID()
                        .toString()
                        .substring(0, 10)
        );

        entity.setTenLoaiSuCo(
                tenLoai
        );

        entity.setDeletedAt(null);

        loaiRepository.save(entity);

        return loaiMapper.toResponse(entity);
    }

    @Override
    public LoaiResponse update(
            String maLoai,
            String tenLoai
    ) {

        LoaiEntity entity =
                loaiRepository
                        .findByMaLoaiSuCoAndDeletedAtIsNull(
                                maLoai
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Không tìm thấy loại"
                                )
                        );

        entity.setTenLoaiSuCo(
                tenLoai
        );

        loaiRepository.save(entity);

        return  loaiMapper.toResponse(entity);
    }

    @Override
    public Void delete(
            String maLoai
    ) {

        LoaiEntity entity =
                loaiRepository
                        .findByMaLoaiSuCoAndDeletedAtIsNull(
                                maLoai
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Không tìm thấy loại"
                                )
                        );

        entity.setDeletedAt(
                LocalDate.now()
        );

        loaiRepository.save(entity);

        return null;
    }

    @Override
    public LoaiResponse findById(
            String maLoai
    ) {

        LoaiEntity entity =
                loaiRepository
                        .findByMaLoaiSuCoAndDeletedAtIsNull(
                                maLoai
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Không tìm thấy loại"
                                )
                        );

        return  loaiMapper.toResponse(entity);
    }

    @Override
    public List<LoaiResponse> findAll() {

        return loaiRepository
                .findAllByDeletedAtIsNull()
                .stream()
                .map(loaiMapper::toResponse)
                .toList();
    }


}