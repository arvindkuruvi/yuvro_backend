package com.management.yuvro.service.impl;

import com.management.yuvro.dto.*;
import com.management.yuvro.dto.request.CreateTaskRequest;
import com.management.yuvro.dto.request.UpdateCandidateTaskRequest;
import com.management.yuvro.dto.response.CandidateTaskAnsResponse;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.CreateTaskResponse;
import com.management.yuvro.dto.response.TaskStatsResponse;
import com.management.yuvro.exceptions.EntityNotFoundException;
import com.management.yuvro.jpa.entity.CandidateTask;
import com.management.yuvro.jpa.entity.Task;
import com.management.yuvro.jpa.repository.CandidateRepository;
import com.management.yuvro.jpa.repository.CandidateTaskRepository;
import com.management.yuvro.jpa.repository.TaskRepository;
import com.management.yuvro.mapper.TaskMapper;
import com.management.yuvro.service.BatchService;
import com.management.yuvro.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.management.yuvro.constants.Constants.*;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CandidateTaskRepository candidateTaskRepository;
    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    BatchService batchService;

    @Override
    public CreateTaskResponse createTask(CreateTaskRequest request) {

        var task = taskMapper.mapCreateTaskRequestToTask(request);
        task.setBatch(batchService.findBatchById(request.getBatchId()));
        task.setCreatedDateTime(LocalDateTime.now());
        task = taskRepository.save(task);
        log.info("Task created successfully with ID: {}", task.getTaskId());

        var response = new CreateTaskResponse();
        response.setTaskId(task.getTaskId());
        response.setMessage("Task created successfully");
        response.setSuccess(true);

        return response;
    }

    @Transactional
    @Override
    public CommonApiResponse sendTask(Long taskId) {
        var task = findTaskById(taskId);

        if (task.isSent()) {
            throw new IllegalStateException("Task has already been sent.");
        }

        task.setSent(true);
        task = taskRepository.save(task);

        Task finalTask = task;
        candidateTaskRepository.saveAll(task.getBatch().getCandidates().parallelStream().map(candidate -> createCandidateTask(candidate.getCandidateId(), finalTask)).filter(Objects::nonNull).toList());

        return new CommonApiResponse(String.format(TASK_SENT_MSG, task.getTaskId()), true);
    }

    private CandidateTask createCandidateTask(Long candidateId, Task task) {

        if (candidateRepository.existsById(candidateId)) {
            log.info("Creating candidate task for candidate ID: {}", candidateId);
            var candidateTask = new CandidateTask();
            candidateTask.setStatus(PENDING);
            candidateTask.setTask(task);
            candidateTask.setCandidate(candidateRepository.findById(candidateId).get());
            return candidateTask;
        } else {
            log.error("Candidate with ID {} does not exist", candidateId);
            return null;
        }
    }

    private Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, TASK, id)));
    }

    @Override
    public PageDTO<TaskDTO> getAllTasksForBatch(Long batchId, int page, int size) {

        if (ObjectUtils.isEmpty(batchId)) throw new IllegalArgumentException("Batch ID cannot be null");

        return taskMapper.mapPageOfTasksToPageOfTaskDTO(taskRepository.findAllByBatch_BatchId(batchId, PageRequest.of(page, size, Sort.by("taskId").descending())));
    }

    @Override
    public PageDTO<ViewTaskCandidatesDTO> viewTaskCandidates(Long taskId, int page, int size) {
        if (ObjectUtils.isEmpty(taskId)) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }

        return taskMapper.mapPageOfViewTaskCandidatesProjectionToPageOfViewTaskCandidatesDTO(candidateTaskRepository.findAllCandidateTasksWithNamesByTaskId(taskId, PageRequest.of(page, size, Sort.by("candidateName").ascending())));
    }

    @Override
    public PageDTO<GetCandidateTasksDTO> getCandidateTasks(Long candidateId, int page, int size) {
        if (ObjectUtils.isEmpty(candidateId)) {
            throw new IllegalArgumentException("Candidate ID cannot be null");
        }

        return taskMapper.mapPageOfGetCandidateTasksProjectionToPageOfGetCandidateTasksDTO(candidateTaskRepository.findCandidateTasks(candidateId, PageRequest.of(page, size, Sort.by("taskId").descending())));
    }

    @Override
    public CommonApiResponse attemptCandidateTasks(Long candidateTaskId) {
        if (ObjectUtils.isEmpty(candidateTaskId)) {
            throw new IllegalArgumentException("Candidate Task ID cannot be null");
        }

        var candidateTask = candidateTaskRepository.findById(candidateTaskId).orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, CANDIDATE_TASK, candidateTaskId)));

        candidateTask.setStatus(IN_PROGRESS);
        candidateTask.setAttemptedDateTime(LocalDateTime.now());
        candidateTaskRepository.save(candidateTask);

        return new CommonApiResponse(String.format("Task with ID %d is now in progress", candidateTaskId), true);
    }

    @Override
    public CommonApiResponse updateCandidateTask(UpdateCandidateTaskRequest request, boolean submitFlag) {
        var candidateTaskId = request.getCandidateTaskId();

        if (ObjectUtils.isEmpty(candidateTaskId)) {
            throw new IllegalArgumentException("Candidate Task ID cannot be null");
        }

        var candidateTask = candidateTaskRepository.findById(candidateTaskId).orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, CANDIDATE_TASK, candidateTaskId)));

        candidateTask.setContent(request.getContent());
        if (submitFlag) {
            log.info("Submitting candidate task for candidate task ID: {}", candidateTaskId);
            candidateTask.setStatus(COMPLETED);
            candidateTask.setSubmissionDateTime(LocalDateTime.now());
        }

        candidateTaskRepository.save(candidateTask);

        return new CommonApiResponse(String.format("Candidate task with ID %d has been updated successfully", candidateTaskId), true);
    }

    @Override
    public CandidateTaskAnsResponse viewCandidateTaskAnswer(Long candidateTaskId) {
        if (ObjectUtils.isEmpty(candidateTaskId)) {
            throw new IllegalArgumentException("Candidate Task ID cannot be null");
        }

        var candidateTask = candidateTaskRepository.findById(candidateTaskId).orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, CANDIDATE_TASK, candidateTaskId)));

        var response = taskMapper.mapCandidateTaskToCandidateTaskAnsResponse(candidateTask);
        response.setMessage(RETRIEVE_SUCCESS);
        response.setSuccess(true);

        return response;
    }

    @Override
    public TaskStatsResponse getCandidateTaskStatusCount(Long taskId) {
        List<String> statuses = candidateTaskRepository.getCandidateTasksStatus(taskId);
        var response = new TaskStatsResponse();
        response.setCompletedCandidates(statuses.stream().filter(status -> status.equals(COMPLETED)).count());
        response.setPendingCandidates(statuses.stream().filter(status -> status.equals(PENDING)).count());
        response.setInProgressCandidates(statuses.stream().filter(status -> status.equals(IN_PROGRESS)).count());
        response.setTotalCandidates(statuses.size());
        response.setMessage(RETRIEVE_SUCCESS);
        response.setSuccess(true);
        return response;
    }

    @Override
    public List<TaskCandidatesDTO> getTaskReportExcel(Long taskId) {
        return taskMapper.mapListOfViewTaskCandidatesProjectionToListOfTaskCandidatesDTO(
                candidateTaskRepository.findAllTaskCandidatesByTaskId(taskId));
    }

}