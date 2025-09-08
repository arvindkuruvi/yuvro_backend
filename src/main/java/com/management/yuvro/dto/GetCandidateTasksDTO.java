package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCandidateTasksDTO {
    private Long taskId;
    private Long candidateId;
    private Long candidateTaskId;
    private String createdDateTime;
    private String submittedDateTime;
    private Long createdById;
    private String createdByName;
    private String title;
    private String description;
    private String status;
}
