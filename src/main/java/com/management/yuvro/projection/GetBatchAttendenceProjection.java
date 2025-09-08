package com.management.yuvro.projection;

import java.time.LocalDate;

public interface GetBatchAttendenceProjection {

    Long getAttendenceId();

    Long getCandidateId();

    Long getBatchId();

    String getCandidateName();

    LocalDate getAttendenceDate();

    boolean getPresent();

}
