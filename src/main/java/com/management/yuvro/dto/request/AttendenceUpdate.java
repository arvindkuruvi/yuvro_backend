package com.management.yuvro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendenceUpdate {
    private Long candidateId;
    private boolean present;
}
