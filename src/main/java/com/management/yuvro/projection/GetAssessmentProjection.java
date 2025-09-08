package com.management.yuvro.projection;

import java.time.LocalDateTime;

public interface GetAssessmentProjection {

    Long getAssessmentId();

    String getAssessmentName();

    String getAssessmentType();

    String getDescription();

    String getSubject();

    String getTopic();

    LocalDateTime getCreatedDateTime();

    LocalDateTime getStartDateTime();

    LocalDateTime getDueDateTime();

    String getDurationInHours();

    Long getTotalQuestions();

    String getStatus();
}
