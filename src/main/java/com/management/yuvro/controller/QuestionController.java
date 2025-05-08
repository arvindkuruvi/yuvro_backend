package com.management.yuvro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.yuvro.dto.request.SaveQuestionRequest;
import com.management.yuvro.dto.request.ValidateQuestionRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.GetQuestionsResponse;
import com.management.yuvro.service.QuestionService;

@RestController
@RequestMapping("/questions/")
public class QuestionController {
	private QuestionService questionService;

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@PostMapping("save-question")
	public ResponseEntity<CommonApiResponse> saveQuestion(@RequestBody SaveQuestionRequest question) {
		return ResponseEntity.ok().body(questionService.saveQuestion(question));
	}

	@PostMapping("get-questions")
	public ResponseEntity<GetQuestionsResponse> getQuestionsBySubTopic(@RequestParam(name = "topicId") Long topicId) {
		return ResponseEntity.ok().body(questionService.getQuestionsByTopicId(topicId));
	}
	
	@PostMapping("validate-question")
	public ResponseEntity<CommonApiResponse> validateQuestion(@RequestBody ValidateQuestionRequest validateQuestionRequest) {
		return ResponseEntity.ok().body(questionService.validateQuestion(validateQuestionRequest));
	}
}
