package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private Long feedbackId;
    private String feedbackText;
    private String annotatedText;
    private boolean faculty;
    private boolean student;
    private String lineNo;
    private String createdDateTime;
}
