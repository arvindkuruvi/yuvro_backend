package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCandidatesDTO {
    private Long taskId;
    private Long candidateId;
    private Long candidateTaskId;
    private String candidateName;
    private String createdDateTime;
    private String submittedDateTime;
    private String status;
}
