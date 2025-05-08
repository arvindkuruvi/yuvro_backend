package com.management.yuvro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavePracticeQuestionRequest {
    private Long questionId;
    private Long practiceId;
    private String selectedAns;
    private boolean correct;
}
