package com.management.yuvro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentStatsResponse extends CommonApiResponse {
    private long totalCandidates;
    private long completedCandidates;
    private long pendingCandidates;
    private long inProgressCandidates;
    private String averageScore;
}
