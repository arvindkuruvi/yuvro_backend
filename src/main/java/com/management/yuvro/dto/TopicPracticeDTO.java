package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopicPracticeDTO {
    private Long candidateId;
    private Long topicId;
    private Long practiceId;
    private Long candidateName;
    private String status;
    private boolean attempted;
    private boolean completed;
    private String attemptedDateTime;
    private String submittedDateTime;
}
