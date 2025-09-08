package com.management.yuvro.projection;

import java.time.LocalDateTime;

public interface AssessmentCandidateProjection {

    Long getCandidateAssessmentId();

    String getStatus();

    String getRemainingDuration();

    Long getLastAttendedQuestion();

    Long getCandidateId();

    Long getScore();

    String getTotalCorrectAnswers();

    String getTotalInCorrectAnswers();

    String getTotalNotAttemptedQuestions();

    LocalDateTime getSubmissionDateTime();

    String getName();
}
