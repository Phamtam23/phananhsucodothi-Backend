package com.DATN.PhanAnhSuCoDoThi.entity;

import java.io.Serializable;
import java.util.Objects;

public class PhieuPhanLoaiId implements Serializable {

    private String maSuCo;
    private String maLoaiSuCo;

    public PhieuPhanLoaiId() {}

    public PhieuPhanLoaiId(String maSuCo, String maLoaiSuCo) {
        this.maSuCo = maSuCo;
        this.maLoaiSuCo = maLoaiSuCo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhieuPhanLoaiId)) return false;
        PhieuPhanLoaiId that = (PhieuPhanLoaiId) o;
        return Objects.equals(maSuCo, that.maSuCo) &&
               Objects.equals(maLoaiSuCo, that.maLoaiSuCo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSuCo, maLoaiSuCo);
    }
}
