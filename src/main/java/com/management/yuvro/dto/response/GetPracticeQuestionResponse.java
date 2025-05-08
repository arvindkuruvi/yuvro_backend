package com.management.yuvro.dto.response;

import com.management.yuvro.dto.QuestionsAttenedDTO;
import com.management.yuvro.dto.QuestionsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class GetPracticeQuestionResponse extends CommonApiResponse {
    private Long candidateId;
    private Long topicId;
    private List<QuestionsAttenedDTO> questions;
}
