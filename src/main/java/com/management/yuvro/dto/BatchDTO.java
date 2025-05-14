package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDTO {
    private Long batchId;
    private String batchName;
    private String batchTitle;
    private String description;
    private String duration;
}
