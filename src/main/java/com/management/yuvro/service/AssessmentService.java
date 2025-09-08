package com.management.yuvro.service;

import com.management.yuvro.dto.*;
import com.management.yuvro.dto.request.AddAssessmentCandidatesRequest;
import com.management.yuvro.dto.request.SaveAssessmentAnswerRequest;
import com.management.yuvro.dto.request.SaveAssessmentQuestionRequest;
import com.management.yuvro.dto.request.SaveAssessmentRequest;
import com.management.yuvro.dto.response.*;
import jakarta.transaction.Transactional;

import java.util.List;

public interface AssessmentService {

    PageDTO<GetAssessmentDTO> getAllAssessments(int page, int size, Long batchId, Long candidateId);

    SaveAssessmentResponse saveAssessment(SaveAssessmentRequest saveAssessmentRequest);

    CommonApiResponse saveAssessmentQuestion(SaveAssessmentQuestionRequest request);

    CommonApiResponse addCandidatesToAssessment(AddAssessmentCandidatesRequest request);

    @Transactional
    CommonApiResponse submitCandidateAssessment(Long candidateId, Long assessmentId);

    @Transactional
    AssessmentDetailsResponse getAssessmentDetails(Long candidateId, Long assessmentId);

    AttemptAssessmentResponse attemptCandidateAssessment(Long candidateId, Long assessmentId);

    AssessmentStatsResponse getAssessmentStatistics(Long assessmentId);

    CommonApiResponse saveAssessmentAnswer(SaveAssessmentAnswerRequest request);

    PageDTO<CandidateAssessmentDTO> getAssessmentCandidates(Long assessmentId, int page, int size);

    CommonApiResponse sendAssessment(Long assessmentId);

    PageDTO<AssessmentDTO> getMyAssessments(int page, int size, String createdBy);

    List<AssessmentCandidatesDTO> getAssessmentCandidatesExcel(Long assessmentId);
}
