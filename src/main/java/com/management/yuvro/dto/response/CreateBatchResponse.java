package com.management.yuvro.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateBatchResponse extends CommonApiResponse {
    private Long batchId;
}
