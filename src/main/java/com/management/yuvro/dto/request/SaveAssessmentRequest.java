package com.management.yuvro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAssessmentRequest {
    private String assessmentName;
    private String assessmentType;
    private String description;
    private String subject;
    private String topic;
    @Schema(example = "2025-05-18T14:30:00")
    private String startDateTime;
    @Schema(example = "2025-05-18T14:30:00")
    private String dueDateTime;
    private String durationInHours;
    private Long batchId;
    private String createdBy;
}
