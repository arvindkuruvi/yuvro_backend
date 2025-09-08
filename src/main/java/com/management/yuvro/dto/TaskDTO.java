package com.management.yuvro.dto;

import com.management.yuvro.jpa.entity.Batch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long taskId;
    private String createdDateTime;
    private boolean sent;
    private Long createdById;
    private String createdByName;
    private String title;
    private String description;
    private Long batchId;
}
