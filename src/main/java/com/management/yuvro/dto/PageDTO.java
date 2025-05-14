package com.management.yuvro.dto;

import com.management.yuvro.dto.response.CommonApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDTO<T> extends CommonApiResponse {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
