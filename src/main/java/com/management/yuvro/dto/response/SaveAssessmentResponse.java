package com.management.yuvro.dto.response;

import com.management.yuvro.dto.AssessmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SaveAssessmentResponse extends CommonApiResponse {
    private AssessmentDTO assessmentDTO;
}
