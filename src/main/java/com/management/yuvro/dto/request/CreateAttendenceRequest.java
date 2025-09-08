package com.management.yuvro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAttendenceRequest {
    private Long batchId;
    private List<AttendenceUpdate> attendees;
    @Schema(example = "2025-05-18")
    private String attendenceDate;
}
