package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MCQAnswerDTO {
    private Long questionId;
    private String question;
    private String questionType;
    private String description;
    private String answer;
    private Integer score;
    private List<String> options;
    private boolean correctAns;
}
