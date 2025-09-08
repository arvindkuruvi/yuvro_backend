package com.management.yuvro.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateFeedbackResponse extends CommonApiResponse {
    private Long feedbackId;
}
