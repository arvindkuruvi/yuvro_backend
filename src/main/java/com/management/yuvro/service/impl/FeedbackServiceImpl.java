package com.management.yuvro.service.impl;

import com.management.yuvro.dto.FeedbackDTO;
import com.management.yuvro.dto.NotificationDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.FeedbackRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.CreateFeedbackResponse;
import com.management.yuvro.jpa.entity.CandidateTask;
import com.management.yuvro.jpa.repository.CandidateTaskRepository;
import com.management.yuvro.jpa.repository.FeedbackRepository;
import com.management.yuvro.mapper.FeedbackMapper;
import com.management.yuvro.service.FeedbackService;
import com.management.yuvro.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    FeedbackMapper feedbackMapper;
    @Autowired
    CandidateTaskRepository candidateTaskRepository;
    @Autowired
    NotificationService notificationService;

    @Override
    public CommonApiResponse createFeedback(FeedbackRequest request) {
        log.info("Creating feedback for candidate task ID: {}", request.getCandidateTaskId());

        var candidateTask = getCandidateTask(request.getCandidateTaskId());

        var feedback = feedbackMapper.mapFeedbackRequestToFeedback(request);
        feedback.setCandidateTask(candidateTask);
        feedback = feedbackRepository.save(feedback);

        var response = new CreateFeedbackResponse();
        response.setFeedbackId(feedback.getFeedbackId());
        response.setMessage("Feedback created successfully");
        response.setSuccess(true);

        log.info("Feedback created successfully with ID: {}", feedback.getFeedbackId());


//        var userProjection = feedbackRepository.findTaskCreatedByIdAndCandidateIdByCandidateTaskId(request.getCandidateTaskId());
//        var notification = new NotificationDTO();
//        notification.setCreatedBy(userProjection.getCandidateId());
//
//        notificationService.saveNotification(
//                userProjection.getUserId(),
//                userProjection.getUserName(),
//                "New feedback received for your task",
//                "You have received new feedback for the task: " + candidateTask.getTask().getTitle(),
//                request.getCandidateTaskId(),
//                "FEEDBACK");

        return response;
    }

    public CandidateTask getCandidateTask(Long candidateTaskId) {
        return candidateTaskRepository.findById(candidateTaskId)
                .orElseThrow(() -> new RuntimeException("Candidate task not found with ID: " + candidateTaskId));
    }

    @Override
    public PageDTO<FeedbackDTO> getFeedbacks(Long candidateTaskId, int page, int size) {
        log.info("Fetching feedbacks for candidate task ID: {}", candidateTaskId);

        return feedbackMapper.mapPageOfFeedbackToPageDTOOfFeedbackDTO(
                feedbackRepository.findAllByCandidateTask_CandidateTaskId(
                        candidateTaskId,
                        PageRequest.of(page, size, Sort.by("feedbackId").descending())));
    }
}
