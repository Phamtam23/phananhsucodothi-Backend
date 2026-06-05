package com.DATN.PhanAnhSuCoDoThi.service.implement;


import com.DATN.PhanAnhSuCoDoThi.entity.NhatKyEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TaikhoanEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.repository.NhatKyRepository;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NhatKySerivce {
    private final NhatKyRepository nhatKyRepository;

    public void create(SucoEntity suCo, TrangThaiSuCo hanhDong, TaikhoanEntity taiKhoan)
    {
        NhatKyEntity nhatKyEntity = new NhatKyEntity();
        nhatKyEntity.setMaNhatKy(IdGenerator.genMaNhatKy(taiKhoan.getMaTaiKhoan()));
        nhatKyEntity.setHanhDong(hanhDong);
        nhatKyEntity.setSuCo(suCo);
        nhatKyEntity.setTaiKhoan(taiKhoan);
        nhatKyEntity.setThoiGian(LocalDateTime.now());
        nhatKyRepository.save(nhatKyEntity);
    }


}
