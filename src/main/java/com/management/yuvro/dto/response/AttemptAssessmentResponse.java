package com.management.yuvro.dto.response;

import com.management.yuvro.dto.MCQQuestionDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class AttemptAssessmentResponse extends CommonApiResponse {
    private Long candidateId;
    private Long assessmentId;
    private String assessmentName;
    private String description;
    private String subject;
    private String topic;
    private String duration;
    private List<MCQQuestionDTO> questions;
}
