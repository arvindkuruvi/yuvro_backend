package com.management.yuvro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {
    private String feedbackText;
    private String annotatedText;
    private boolean faculty;
    private boolean student;
    private String lineNo;
    private String createdDateTime;
    private Long candidateTaskId;
}
