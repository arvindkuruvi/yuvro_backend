package com.management.yuvro.projection;

import java.time.LocalDateTime;

public interface GetCandidateTasksProjection {

    Long getTaskId();

    Long getCandidateId();

    Long getCandidateTaskId();

    LocalDateTime getSubmissionDateTime();

    LocalDateTime getCreatedDateTime();

    Long getCreatedById();

    String getCreatedByName();

    String getTitle();

    String getDescription();

    String getStatus();
}
