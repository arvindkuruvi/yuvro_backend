package com.management.yuvro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCandidateDTO {
    private String name;
    private String phone;
    private String email;
    private String type;
    private String branch;
}
