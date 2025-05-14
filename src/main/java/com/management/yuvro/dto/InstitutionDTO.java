package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDTO {
    private Long institutionId;
    private String institution;
    private String institutionCode;
}
