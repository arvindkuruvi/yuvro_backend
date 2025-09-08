package com.management.yuvro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAssessmentAnswerRequest {
    private Long candidateId;
    private Long assessmentId;
    private Long questionId;
    private String answer;
    private String remainingDuration;
}