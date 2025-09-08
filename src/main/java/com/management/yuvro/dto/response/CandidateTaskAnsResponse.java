package com.management.yuvro.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CandidateTaskAnsResponse extends CommonApiResponse {
    private Long candidateTaskId;
    private String status;
    private String submissionDateTime;
    private String attemptedDateTime;
    private String content;
}
