package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsAttenedDTO {
	private Long questionId;
	private String question;
	private String questionType;
	private String selectedAnswer;
	private boolean questionAttended;
	private boolean correct;
	private List<String> options;
}