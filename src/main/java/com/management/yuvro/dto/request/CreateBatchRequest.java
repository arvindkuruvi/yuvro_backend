package com.management.yuvro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBatchRequest {
    private String batchName;
    private String batchTitle;
    private String description;
    private String duration;
    private List<Long> courseIds;
}
