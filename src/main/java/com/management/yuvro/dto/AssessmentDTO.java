package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentDTO {
    private Long assessmentId;
    private String assessmentName;
    private String assessmentType;
    private String description;
    private String subject;
    private String topic;
    private String createdDateTime;
    private String startDateTime;
    private String dueDateTime;
    private String durationInHours;
    private Long totalQuestions;
    private boolean sent;
}
