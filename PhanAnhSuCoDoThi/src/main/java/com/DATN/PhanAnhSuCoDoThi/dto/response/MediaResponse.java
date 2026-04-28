package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MediaResponse {
    private String url;

    private String type; // IMAGE / VIDEO
}
