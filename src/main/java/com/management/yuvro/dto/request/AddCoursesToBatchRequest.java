package com.management.yuvro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddCoursesToBatchRequest {
    private Long batchId;
    private List<Long> courseIds;
}
