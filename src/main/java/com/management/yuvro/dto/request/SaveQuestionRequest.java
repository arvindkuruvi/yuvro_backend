package com.management.yuvro.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveQuestionRequest {
	private String question;
	private String questionType;
	private String answer;
	private Long topicId;
	private Set<MCQOptionRequest> options;
}
