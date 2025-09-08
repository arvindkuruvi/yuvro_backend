package com.management.yuvro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TaskStatsResponse extends CommonApiResponse {
    private int totalCandidates;
    private long completedCandidates;
    private long pendingCandidates;
    private long inProgressCandidates;
}
