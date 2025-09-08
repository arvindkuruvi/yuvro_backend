package com.management.yuvro.service.impl;

import com.management.yuvro.dto.*;
import com.management.yuvro.dto.request.AddAssessmentCandidatesRequest;
import com.management.yuvro.dto.request.SaveAssessmentAnswerRequest;
import com.management.yuvro.dto.request.SaveAssessmentQuestionRequest;
import com.management.yuvro.dto.request.SaveAssessmentRequest;
import com.management.yuvro.dto.response.*;
import com.management.yuvro.exceptions.EntityNotFoundException;
import com.management.yuvro.jpa.entity.*;
import com.management.yuvro.jpa.repository.*;
import com.management.yuvro.mapper.AssessmentMapper;
import com.management.yuvro.mapper.CandidateAssessmentMapper;
import com.management.yuvro.mapper.QuestionMapper;
import com.management.yuvro.service.AssessmentService;
import com.management.yuvro.service.BatchService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.management.yuvro.constants.Constants.*;
import static com.management.yuvro.utils.AppUtils.getPercentage;

@Slf4j
@Service
public class AssessmentServiceImpl implements AssessmentService {
    @Autowired
    AssessmentRepository assessmentRepository;
    @Autowired
    AssessmentMapper assessmentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    MCQQuestionRepository mcqQuestionRepository;
    @Autowired
    MCQOptionRepository mcqOptionRepository;
    @Autowired
    CandidateAssessmentRepository candidateAssessmentRepository;
    @Autowired
    AttemptedQuestionRepository attemptedQuestionRepository;
    @Autowired
    CandidateAssessmentMapper candidateAssessmentMapper;
    @Autowired
    BatchService batchService;

    @Override
    public PageDTO<GetAssessmentDTO> getAllAssessments(int page, int size, Long batchId, Long candidateId) {

        if (ObjectUtils.isEmpty(batchId)) {
            throw new IllegalArgumentException("Batch ID cannot be null or empty");
        }

        if (ObjectUtils.isEmpty(candidateId)) {
            throw new IllegalArgumentException("Candidate ID cannot be null or empty");
        }

        return assessmentMapper.mapPageOfGetAssessmentProjectionToPageDTOOfAssessmentDTO(assessmentRepository
                .findAllAssessmentDetailsByCandidateIdAndBatchId(candidateId, batchId, PageRequest.of(page, size, Sort.by("assessmentId"))));
    }

    @Override
    public SaveAssessmentResponse saveAssessment(SaveAssessmentRequest saveAssessmentRequest) {
        var batch = batchService.findBatchById(saveAssessmentRequest.getBatchId());

        var assessment = assessmentMapper.mapSaveAssessmentRequestToAssessment(saveAssessmentRequest);
        assessment.setBatch(batch);
        assessment.setTotalQuestions(0L);

        assessment = assessmentRepository.save(assessment);

        log.info("Assessment saved successfully: {}", assessment.getAssessmentId());

        var response = new SaveAssessmentResponse();
        response.setAssessmentDTO(assessmentMapper.mapAssessmentToAssessmentDTO(assessment));
        response.setSuccess(true);
        response.setMessage(SAVE_SUCCESS);

        return response;
    }

    public Assessment findAssessmentById(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, ASSESSMENT, id)));
    }

    @Override
    @Transactional
    public CommonApiResponse saveAssessmentQuestion(SaveAssessmentQuestionRequest request) {
        var assessment = findAssessmentById(request.getAssessmentId());

        MCQQuestion question = questionMapper.mapSaveAssessmentQuestionRequestToQuestion(request);
        question.setAssessment(assessment);
        question = mcqQuestionRepository.save(question);

        saveMCQOptions(question, request.getOptions());

        assessment.setTotalQuestions(ObjectUtils.isNotEmpty(assessment.getTotalQuestions()) ? assessment.getTotalQuestions() + 1 : 1);
        assessmentRepository.save(assessment);

        return new CommonApiResponse(SAVE_SUCCESS, true);
    }


    private void saveMCQOptions(MCQQuestion question, List<String> options) {

        log.info("Saving options for question :: {}", question.getQuestionId());

        List<MCQOptions> mcqOptions = new ArrayList<MCQOptions>();

        for (String option : options) {
            mcqOptions.add(MCQOptions.builder().option(option).question(question).build());
        }

        mcqOptionRepository.saveAll(mcqOptions);
    }

    @Override
    public CommonApiResponse addCandidatesToAssessment(AddAssessmentCandidatesRequest request) {
        var assessment = findAssessmentById(request.getAssessmentId());

        candidateAssessmentRepository.saveAll(request.getCandidateIds()
                .parallelStream().map(candidateId -> createCandidateAssessment(candidateId, assessment)).toList());

        return new CommonApiResponse(SAVE_SUCCESS, true);
    }

    private CandidateAssessment createCandidateAssessment(Long candidateId, Assessment assessment) {
        var candidateAssessment = new CandidateAssessment();
        candidateAssessment.setAssessment(assessment);
        candidateAssessment.setStatus(PENDING);
        candidateAssessment.setCandidateId(candidateId);
        return candidateAssessment;
    }

    public CandidateAssessment findByCandidateIdAndAssessmentId(Long candidateId, Long assessmentId) {
        return candidateAssessmentRepository.findByCandidateIdAndAssessment_AssessmentId(candidateId, assessmentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ASSESSMENT_NOT_FOUND_FOR_CANDIDATE, candidateId)));
    }

    @Transactional
    @Override
    public CommonApiResponse submitCandidateAssessment(Long candidateId, Long assessmentId) {

        var candidateAssessment = findByCandidateIdAndAssessmentId(candidateId, assessmentId);
        Map<Long, MCQQuestion> allQuestionsMap =
                candidateAssessment.getAssessment().getQuestions().parallelStream()
                        .collect(Collectors.toMap(MCQQuestion::getQuestionId, Function.identity()));

        Map<Long, AttemptedQuestion> attemptedQuestionsMap = candidateAssessment.getAttemptedQuestions().parallelStream()
                .collect(Collectors.toMap(AttemptedQuestion::getQuestionId, Function.identity()));

        long totalQuestions = allQuestionsMap.size();
        long attendedQuestions = attemptedQuestionsMap.size();
        long notAttendedQuestions = totalQuestions - attendedQuestions;

        long score = 0;
        long totalCorrectAnswers = 0;

        for (AttemptedQuestion attempted : attemptedQuestionsMap.values()) {
            MCQQuestion actualQuestion = allQuestionsMap.get(attempted.getQuestionId());
            boolean isCorrect = actualQuestion != null && actualQuestion.getAnswer().equals(attempted.getAnswer());
            attempted.setCorrect(isCorrect);
            if (isCorrect) {
                score += actualQuestion.getScore();
                attempted.setScore(actualQuestion.getScore());
                totalCorrectAnswers++;
            }
        }

        candidateAssessment.setStatus(COMPLETED);
        candidateAssessment.setSubmissionDateTime(LocalDateTime.now());
        candidateAssessment.setScore(score);
        candidateAssessment.setTotalCorrectAnswers(getPercentage(totalCorrectAnswers, totalQuestions));
        candidateAssessment.setTotalInCorrectAnswers(getPercentage(attendedQuestions - totalCorrectAnswers, totalQuestions));
        candidateAssessment.setTotalNotAttemptedQuestions(getPercentage(notAttendedQuestions, totalQuestions));

        attemptedQuestionRepository.saveAll(attemptedQuestionsMap.values());

        candidateAssessmentRepository.save(candidateAssessment);

        return new CommonApiResponse(SAVE_SUCCESS, true);
    }

    @Transactional
    @Override
    public AssessmentDetailsResponse getAssessmentDetails(Long candidateId, Long assessmentId) {

        if (ObjectUtils.isEmpty(candidateId))
            throw new IllegalArgumentException("Candidate ID cannot be null or empty");

        if (ObjectUtils.isEmpty(assessmentId))
            throw new IllegalArgumentException("Assessment ID cannot be null or empty");

        var candidateAssessment = findByCandidateIdAndAssessmentId(candidateId, assessmentId);

        Map<Long, MCQQuestion> allQuestionsMap =
                candidateAssessment.getAssessment().getQuestions().parallelStream()
                        .collect(Collectors.toMap(MCQQuestion::getQuestionId, Function.identity()));

        Map<Long, AttemptedQuestion> attemptedQuestionsMap = candidateAssessment.getAttemptedQuestions().parallelStream()
                .collect(Collectors.toMap(AttemptedQuestion::getQuestionId, Function.identity()));

        List<MCQAnswerDTO> mcqAnswerDTOS = new ArrayList<>();

        for (MCQQuestion actualQuestion : allQuestionsMap.values()) {
            MCQAnswerDTO mcqAnswerDTO = questionMapper.convertMCQQuestionToMCQAnswerDTO(actualQuestion);

            if (attemptedQuestionsMap.containsKey(actualQuestion.getQuestionId())) {
                var attempted = attemptedQuestionsMap.get(actualQuestion.getQuestionId());
                if (attempted.getAnswer() != null) {
                    mcqAnswerDTO.setCorrectAns(actualQuestion.getAnswer().equals(attempted.getAnswer()));
                }
            }

            mcqAnswerDTOS.add(mcqAnswerDTO);
        }
        var assessment = candidateAssessment.getAssessment();

        var response = new AssessmentDetailsResponse();
        response.setAssessmentId(assessmentId);
        response.setCandidateId(candidateId);
        response.setAssessmentName(assessment.getAssessmentName());
        response.setDescription(assessment.getDescription());
        response.setSubject(assessment.getSubject());
        response.setTopic(assessment.getTopic());
        response.setScore(candidateAssessment.getScore());
        response.setTotalCorrectAnswers(candidateAssessment.getTotalCorrectAnswers());
        response.setTotalInCorrectAnswers(candidateAssessment.getTotalInCorrectAnswers());
        response.setTotalNotAttemptedQuestions(candidateAssessment.getTotalNotAttemptedQuestions());
        response.setSubmissionDateTime(ObjectUtils.isNotEmpty(candidateAssessment.getSubmissionDateTime()) ? candidateAssessment.getSubmissionDateTime().toString() : "Not Submitted");
        response.setSuccess(true);
        response.setMessage(RETRIEVE_SUCCESS);
        response.setQuestions(mcqAnswerDTOS);

        return response;
    }

    @Transactional
    @Override
    public AttemptAssessmentResponse attemptCandidateAssessment(Long candidateId, Long assessmentId) {

        var candidateAssessment = findByCandidateIdAndAssessmentId(candidateId, assessmentId);

        if (LocalDateTime.now().isAfter(candidateAssessment.getAssessment().getDueDateTime())) {
            throw new IllegalStateException("Assessment due date has passed. Cannot attempt assessment.");
        }

        if (COMPLETED.equalsIgnoreCase(candidateAssessment.getStatus())) {
            throw new IllegalStateException("Candidate has already completed the assessment.");
        }

        if (IN_PROGRESS.equalsIgnoreCase(candidateAssessment.getStatus())) {
            throw new IllegalStateException("Candidate is already attempting the assessment.");
        }

        candidateAssessment.setStatus(IN_PROGRESS);
        candidateAssessment = candidateAssessmentRepository.save(candidateAssessment);

        var assessment = candidateAssessment.getAssessment();

        List<MCQQuestionDTO> questionDTOS = questionMapper
                .mapListOfMCQQuestionToMCQQuestionDTO(mcqQuestionRepository.findAllByAssessment_AssessmentId(assessmentId));

        var response = new AttemptAssessmentResponse();
        response.setAssessmentId(assessmentId);
        response.setCandidateId(candidateId);
        response.setSuccess(true);
        response.setMessage(ATTEMPT_SUCCESS);
        response.setQuestions(questionDTOS);
        response.setAssessmentName(assessment.getAssessmentName());
        response.setDescription(assessment.getDescription());
        response.setTopic(assessment.getTopic());
        response.setSubject(assessment.getSubject());
        response.setDuration(assessment.getDurationInHours());

        return response;
    }

    @Override
    public AssessmentStatsResponse getAssessmentStatistics(Long assessmentId) {
        var candidateAssessments = candidateAssessmentRepository.findAllByAssessment_AssessmentId(assessmentId);

        var totalCandidates = candidateAssessments.size();

        var completedCandidates = candidateAssessments.stream().filter(ca -> COMPLETED.equalsIgnoreCase(ca.getStatus())).count();
        var pendingCandidates = candidateAssessments.stream().filter(ca -> PENDING.equalsIgnoreCase(ca.getStatus())).count();
        var inProgressCandidates = candidateAssessments.stream().filter(ca -> IN_PROGRESS.equalsIgnoreCase(ca.getStatus())).count();
        var averageScore = candidateAssessments.stream()
                .filter(ca -> ca.getScore() != null)
                .mapToLong(CandidateAssessment::getScore)
                .average()
                .orElse(0);

        var response = new AssessmentStatsResponse();
        response.setTotalCandidates(totalCandidates);
        response.setCompletedCandidates(completedCandidates);
        response.setPendingCandidates(pendingCandidates);
        response.setInProgressCandidates(inProgressCandidates);
        response.setAverageScore(getPercentage(averageScore, totalCandidates));
        response.setSuccess(true);
        response.setMessage(STATS_RETRIEVE_SUCCESS);

        return response;
    }

    @Override
    public CommonApiResponse saveAssessmentAnswer(SaveAssessmentAnswerRequest request) {

        var candidateAssessment = findByCandidateIdAndAssessmentId(request.getCandidateId(), request.getAssessmentId());

        var attemptedQuestionOptional = attemptedQuestionRepository.findByQuestionId(request.getQuestionId());
        AttemptedQuestion attemptedQuestion = null;

        if (attemptedQuestionOptional.isEmpty()) {
            log.info("Attempted question not found, creating a new one");
            attemptedQuestion = new AttemptedQuestion();
            attemptedQuestion.setCandidateAssessment(candidateAssessment);
            attemptedQuestion.setQuestionId(request.getQuestionId());
        } else {
            log.info("Attempted question found, updating the existing one");
            attemptedQuestion = attemptedQuestionOptional.get();
        }

        attemptedQuestion.setAnswer(request.getAnswer());

        attemptedQuestionRepository.save(attemptedQuestion);

        candidateAssessment.setRemainingDuration(request.getRemainingDuration());
        candidateAssessmentRepository.save(candidateAssessment);

        return new CommonApiResponse(SAVE_SUCCESS, true);
    }

    @Override
    public PageDTO<CandidateAssessmentDTO> getAssessmentCandidates(Long assessmentId, int page, int size) {

        var candidateAssessmentsPage = candidateAssessmentRepository
                .findAllByAssessment_AssessmentId(assessmentId, PageRequest.of(page, size, Sort.by("candidateAssessmentId")));

        return candidateAssessmentMapper.mapAssessmentCandidateProjectionPageToCandidateAssessmentDTOPage(candidateAssessmentsPage);
    }

    @Transactional
    @Override
    public CommonApiResponse sendAssessment(Long assessmentId) {
        var assessment = findAssessmentById(assessmentId);

        if (assessment.isSent()) {
            throw new IllegalStateException("Assessment has already been sent.");
        }

        if (assessment.getDueDateTime() == null) {
            throw new IllegalStateException("Assessment due date is not set. Please set the due date before sending.");
        }

        // Check if the due date is in the past
        if (assessment.getDueDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Assessment due date has passed. Cannot attempt assessment.");
        }

        assessment.setSent(true);
        assessment = assessmentRepository.save(assessment);

        Assessment finalAssessment = assessment;
        candidateAssessmentRepository.saveAll(assessment.getBatch().getCandidates()
                .parallelStream().map(candidate -> createCandidateAssessment(candidate.getCandidateId(), finalAssessment)).toList());

        return new CommonApiResponse(String.format(ASSESSMENT_SENT_MSG, assessment.getAssessmentId()), true);
    }

    @Override
    public PageDTO<AssessmentDTO> getMyAssessments(int page, int size, String createdBy) {

        return assessmentMapper.mapPageOfAssessmentToPageDTOOfAssessmentDTO(assessmentRepository
                .findAllByCreatedBy(createdBy, PageRequest.of(page, size, Sort.by("assessmentId"))));

    }

    @Override
    public List<AssessmentCandidatesDTO> getAssessmentCandidatesExcel(Long assessmentId) {
        log.info("Fetching all candidates for assessment ID: {}", assessmentId);
        findAssessmentById(assessmentId);

        var assessmentCandidates = candidateAssessmentRepository
                .findAllCandidatesByAssessmentId(assessmentId);

        return candidateAssessmentMapper
                .mapAssessmentCandidateProjectionListToAssessmentCandidatesDTOList(assessmentCandidates);
    }
}