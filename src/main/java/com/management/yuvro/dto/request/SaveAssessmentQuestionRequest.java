package com.management.yuvro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAssessmentQuestionRequest {
    private String question;
    private String questionType;
    private String description;
    private String answer;
    private Integer score;
    private Long assessmentId;
    private List<String> options;
}
