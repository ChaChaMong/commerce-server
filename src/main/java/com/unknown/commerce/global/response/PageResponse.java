package com.unknown.commerce.global.response;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PageResponse<T> {
    private T data;
    private PageInfo pageInfo;

    public static <T> PageResponse of(Page<T> page) {
        return PageResponse.builder()
                .data(page.getContent())
                .pageInfo(PageInfo.of(page))
                .build();
    }
}
