package com.management.yuvro.controller;

import com.management.yuvro.dto.CourseDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.QuestionsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/get-all-questions")
	public ResponseEntity<PageDTO<QuestionsDTO>> getAllQuestions(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
		return ResponseEntity.ok().body(questionService.getAllQuestions(page, size));
	}
}