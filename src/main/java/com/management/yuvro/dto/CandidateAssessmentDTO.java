package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateAssessmentDTO {
    private Long candidateAssessmentId;
    private String status;
    private String remainingDuration;
    private Long lastAttendedQuestion;
    private Long candidateId;
    private Long score;
    private String totalCorrectAnswers;
    private String totalInCorrectAnswers;
    private String totalNotAttemptedQuestions;
    private String submissionDateTime;
    private String name;
}
