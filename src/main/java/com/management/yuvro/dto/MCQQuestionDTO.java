package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MCQQuestionDTO {
    private Long questionId;
    private String question;
    private String questionType;
    private List<String> options;
}
