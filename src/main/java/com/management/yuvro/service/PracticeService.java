package com.management.yuvro.service;

import com.management.yuvro.dto.request.AttemptPracticeRequest;
import com.management.yuvro.dto.request.SavePracticeQuestionRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.GetPracticeQuestionResponse;
import com.management.yuvro.dto.response.PracticeResultResponse;

public interface PracticeService {

	GetPracticeQuestionResponse getPracticeIfExists(Long candidateId, Long topicId);

	CommonApiResponse attemptPractice(AttemptPracticeRequest request);

	CommonApiResponse savePracticeQuestion(SavePracticeQuestionRequest request);

	PracticeResultResponse getPracticeResult(Long practiceId);
}
