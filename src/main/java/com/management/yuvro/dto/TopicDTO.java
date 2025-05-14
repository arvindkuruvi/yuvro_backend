package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private Long topicId;
    private String topicName;
    private String description;
    private boolean isTopicLocked;
}
