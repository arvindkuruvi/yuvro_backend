package com.management.yuvro.enums;

public enum QuestionType {
	SINGLE_OPTION_MCQ, MULTI_OPTION_MCQ, DESCRIPTIVE;

	public static QuestionType fromQuestionType(String questionType) {
		try {
			return QuestionType.valueOf(questionType.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid question type: " + questionType);
		}
	}
}