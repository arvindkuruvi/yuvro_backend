package com.management.yuvro.dto.response;

import com.management.yuvro.dto.QuestionsAttenedDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class PracticeResultResponse extends CommonApiResponse {
    private Long totalCorrectAnswers;
    private Long totalQuestions;
}
