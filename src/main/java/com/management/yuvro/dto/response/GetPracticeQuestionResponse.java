package com.management.yuvro.dto.response;

import com.management.yuvro.dto.QuestionsAttenedDTO;
import com.management.yuvro.dto.QuestionsDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class GetPracticeQuestionResponse extends CommonApiResponse {
    private Long candidateId;
    private Long topicId;
    private String status;
    private boolean attempted;
    private boolean completed;
    private String attemptedDateTime;
    private String submittedDateTime;
    private List<QuestionsAttenedDTO> questions;
}
