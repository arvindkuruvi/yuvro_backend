package com.management.yuvro.dto.response;

import com.management.yuvro.dto.MCQAnswerDTO;
import com.management.yuvro.dto.MCQQuestionDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class AssessmentDetailsResponse extends CommonApiResponse {
    private Long candidateId;
    private Long assessmentId;
    private String assessmentName;
    private String description;
    private String subject;
    private String topic;
    private Long score;
    private String totalCorrectAnswers;
    private String totalInCorrectAnswers;
    private String totalNotAttemptedQuestions;
    private String submissionDateTime;
    private List<MCQAnswerDTO> questions;
}
