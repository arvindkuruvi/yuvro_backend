package com.management.yuvro.controller;

import com.management.yuvro.dto.GetCandidateTasksDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.TaskDTO;
import com.management.yuvro.dto.ViewTaskCandidatesDTO;
import com.management.yuvro.dto.request.CreateTaskRequest;
import com.management.yuvro.dto.request.UpdateCandidateTaskRequest;
import com.management.yuvro.dto.response.CandidateTaskAnsResponse;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.CreateTaskResponse;
import com.management.yuvro.dto.response.TaskStatsResponse;
import com.management.yuvro.service.TaskService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.management.yuvro.utils.AppUtils.generateExcel;

@RestController
@RequestMapping("/tasks/")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/create-task")
    public ResponseEntity<CreateTaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok().body(taskService.createTask(request));
    }

    @PutMapping("/send-task/{taskId}")
    public ResponseEntity<CommonApiResponse> sendTask(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok().body(taskService.sendTask(taskId));
    }

    @GetMapping("/get-all-tasks")
    public ResponseEntity<PageDTO<TaskDTO>> getAllTasksForBatch(@RequestParam("batchId") Long batchId,
                                                                @RequestParam(defaultValue = "0", name = "page") int page,
                                                                @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(taskService.getAllTasksForBatch(batchId, page, size));
    }


    @GetMapping("/get-candidate-tasks")
    public ResponseEntity<PageDTO<GetCandidateTasksDTO>> getCandidateTasks(@RequestParam("candidateId") Long candidateId,
                                                                           @RequestParam(defaultValue = "0", name = "page") int page,
                                                                           @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(taskService.getCandidateTasks(candidateId, page, size));
    }

    @PutMapping("/attempt-candidate-tasks/{candidateTaskId}")
    public ResponseEntity<CommonApiResponse> attemptCandidateTasks(@PathVariable("candidateTaskId") Long candidateTaskId) {
        return ResponseEntity.ok().body(taskService.attemptCandidateTasks(candidateTaskId));
    }

    @PutMapping("/update-candidate-task")
    public ResponseEntity<CommonApiResponse> updateCandidateTask(@RequestBody UpdateCandidateTaskRequest request) {
        return ResponseEntity.ok().body(taskService.updateCandidateTask(request, false));
    }

    @PutMapping("/submit-candidate-task")
    public ResponseEntity<CommonApiResponse> submitCandidateTask(@RequestBody UpdateCandidateTaskRequest request) {
        return ResponseEntity.ok().body(taskService.updateCandidateTask(request, true));
    }

    @GetMapping("/view-task-candidates/{taskId}")
    public ResponseEntity<PageDTO<ViewTaskCandidatesDTO>> viewTaskCandidates(@PathVariable("taskId") Long taskId,
                                                                             @RequestParam(defaultValue = "0", name = "page") int page,
                                                                             @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(taskService.viewTaskCandidates(taskId, page, size));
    }

    @GetMapping("/view-task-answer/{candidateTaskId}")
    public ResponseEntity<CandidateTaskAnsResponse> viewCandidateTaskAnswer(@PathVariable("candidateTaskId") Long candidateTaskId) {
        return ResponseEntity.ok().body(taskService.viewCandidateTaskAnswer(candidateTaskId));
    }

    @GetMapping("/get-task-stats/{taskId}")
    public ResponseEntity<TaskStatsResponse> getCandidateTaskStatusCount(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok().body(taskService.getCandidateTaskStatusCount(taskId));
    }

    @SneakyThrows
    @GetMapping("/download/{taskId}/report")
    public ResponseEntity<byte[]> downloadTaskReport(@PathVariable(name = "taskId") Long taskId) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=taskId" + taskId + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(generateExcel(taskService.getTaskReportExcel(taskId)).readAllBytes());
    }
}