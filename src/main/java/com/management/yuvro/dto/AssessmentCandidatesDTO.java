package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentCandidatesDTO {
    private Long candidateAssessmentId;
    private Long candidateId;
    private String name;
    private String status;
    private Long score;
    private String totalCorrectAnswers;
    private String totalInCorrectAnswers;
    private String totalNotAttemptedQuestions;
    private String submissionDateTime;
}
