package com.management.yuvro.service;

import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.QuestionsDTO;
import com.management.yuvro.dto.request.SaveQuestionRequest;
import com.management.yuvro.dto.request.ValidateQuestionRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.GetQuestionsResponse;

public interface QuestionService {
	CommonApiResponse saveQuestion(SaveQuestionRequest question);

	GetQuestionsResponse getQuestionsByTopicId(Long subTopicId);

	CommonApiResponse validateQuestion(ValidateQuestionRequest validateQuestionRequest);

	PageDTO<QuestionsDTO> getAllQuestions(int page, int size);
}
