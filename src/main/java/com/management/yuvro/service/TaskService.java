package com.management.yuvro.service;

import com.management.yuvro.dto.*;
import com.management.yuvro.dto.request.CreateTaskRequest;
import com.management.yuvro.dto.request.UpdateCandidateTaskRequest;
import com.management.yuvro.dto.response.CandidateTaskAnsResponse;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.CreateTaskResponse;
import com.management.yuvro.dto.response.TaskStatsResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TaskService {

    CreateTaskResponse createTask(CreateTaskRequest request);

    @Transactional
    CommonApiResponse sendTask(Long taskId);

    PageDTO<TaskDTO> getAllTasksForBatch(Long batchId, int page, int size);

    PageDTO<ViewTaskCandidatesDTO> viewTaskCandidates(Long taskId, int page, int size);

    PageDTO<GetCandidateTasksDTO> getCandidateTasks(Long candidateId, int page, int size);

    CommonApiResponse attemptCandidateTasks(Long candidateTaskId);

    CommonApiResponse updateCandidateTask(UpdateCandidateTaskRequest request, boolean submitFlag);

    CandidateTaskAnsResponse viewCandidateTaskAnswer(Long candidateTaskId);

    TaskStatsResponse getCandidateTaskStatusCount(Long taskId);

    List<TaskCandidatesDTO> getTaskReportExcel(Long taskId);
}
