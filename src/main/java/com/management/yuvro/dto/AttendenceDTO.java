package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendenceDTO {
    private Long attendenceId;
    private String candidateName;
    private Long candidateId;
    private Long batchId;
    private String attendenceDate;
    private boolean present;
}
