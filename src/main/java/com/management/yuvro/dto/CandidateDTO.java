package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    private Long candidateId;
    private String name;
    private String phone;
    private String email;
    private String type;
    private String branch;
}
