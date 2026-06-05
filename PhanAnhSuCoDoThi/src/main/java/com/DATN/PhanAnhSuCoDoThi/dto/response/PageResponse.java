package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;
    private PagiNationMeta pagination;

    public static <T> PageResponse<T> of(Page<T> page) {

        return PageResponse.<T>builder()
                .content(page.getContent())
                .pagination(PagiNationMeta.builder()
                        .page(page.getNumber())
                        .size(page.getSize())
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .isFirst(page.isFirst())
                        .isLast(page.isLast())
                        .build())
                .build();


    }

}
