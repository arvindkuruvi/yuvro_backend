package com.management.yuvro.projection;

import java.time.LocalDateTime;

public interface PracticeCandidatesProjection {

    Long getCandidateId();

    String getCandidateName();

    String getStatus();

    LocalDateTime getAttemptedDateTime();

    LocalDateTime getSubmittedDateTime();

    Long getTopicId();

    Long getPracticeId();

    boolean getAttempted();

    boolean getCompleted();

}
