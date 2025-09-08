package com.management.yuvro.controller;

import com.management.yuvro.dto.AssessmentDTO;
import com.management.yuvro.dto.CandidateAssessmentDTO;
import com.management.yuvro.dto.GetAssessmentDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.AddAssessmentCandidatesRequest;
import com.management.yuvro.dto.request.SaveAssessmentAnswerRequest;
import com.management.yuvro.dto.request.SaveAssessmentQuestionRequest;
import com.management.yuvro.dto.request.SaveAssessmentRequest;
import com.management.yuvro.dto.response.*;
import com.management.yuvro.service.AssessmentService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.management.yuvro.utils.AppUtils.generateExcel;

@RestController
@RequestMapping("/assessments/")
public class AssessmentController {
    @Autowired
    AssessmentService assessmentService;

    @GetMapping("/get-all-assessments")
    public ResponseEntity<PageDTO<GetAssessmentDTO>> getAllAssessments(@RequestParam(name = "candidateId") Long candidateId, @RequestParam(name = "batchId") Long batchId, @RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(assessmentService.getAllAssessments(page, size, batchId, candidateId));
    }

    @GetMapping("/get-my-assessments")
    public ResponseEntity<PageDTO<AssessmentDTO>> getMyAssessments(@RequestParam(name = "createdBy") String createdBy, @RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(assessmentService.getMyAssessments(page, size, createdBy));
    }

    @PostMapping("/create-assessment")
    public ResponseEntity<SaveAssessmentResponse> saveAssessment(@RequestBody SaveAssessmentRequest saveAssessmentRequest) {
        return ResponseEntity.ok().body(assessmentService.saveAssessment(saveAssessmentRequest));
    }

    @PostMapping("/save-assessment-question")
    public ResponseEntity<CommonApiResponse> saveAssessmentQuestion(@RequestBody SaveAssessmentQuestionRequest request) {
        return ResponseEntity.ok().body(assessmentService.saveAssessmentQuestion(request));
    }

    @PutMapping("/send-assessment/{assessmentId}")
    public ResponseEntity<CommonApiResponse> sendAssessment(@PathVariable(name = "assessmentId") Long assessmentId) {
        return ResponseEntity.ok().body(assessmentService.sendAssessment(assessmentId));
    }

    @PostMapping("/add-assessment-candidates")
    public ResponseEntity<CommonApiResponse> addCandidatesToAssessment(@RequestBody AddAssessmentCandidatesRequest request) {
        return ResponseEntity.ok().body(assessmentService.addCandidatesToAssessment(request));
    }

    @PutMapping("/submit-assessment")
    public ResponseEntity<CommonApiResponse> submitCandidateAssessment(@RequestParam(name = "candidateId") Long candidateId, @RequestParam(name = "assessmentId") Long assessmentId) {
        return ResponseEntity.ok().body(assessmentService.submitCandidateAssessment(candidateId, assessmentId));
    }

    @PutMapping("/attempt-assessment")
    public ResponseEntity<AttemptAssessmentResponse> attemptCandidateAssessment(@RequestParam(name = "candidateId") Long candidateId, @RequestParam(name = "assessmentId") Long assessmentId) {
        return ResponseEntity.ok().body(assessmentService.attemptCandidateAssessment(candidateId, assessmentId));
    }

    @GetMapping("/assessment-stats")
    public ResponseEntity<AssessmentStatsResponse> getAssessmentStatistics(@RequestParam(name = "assessmentId") Long assessmentId) {
        return ResponseEntity.ok().body(assessmentService.getAssessmentStatistics(assessmentId));
    }

    @PostMapping("/save-assessment-answer")
    public ResponseEntity<CommonApiResponse> saveAssessmentAnswer(@RequestBody SaveAssessmentAnswerRequest request) {
        return ResponseEntity.ok().body(assessmentService.saveAssessmentAnswer(request));
    }

    @GetMapping("/get-assessment-candidates")
    public ResponseEntity<PageDTO<CandidateAssessmentDTO>> getAssessmentCandidates(@RequestParam(name = "assessmentId") Long assessmentId, @RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(assessmentService.getAssessmentCandidates(assessmentId, page, size));
    }

    @GetMapping("/get-assessment-details")
    public ResponseEntity<AssessmentDetailsResponse> getAssessmentDetails(@RequestParam(name = "candidateId") Long candidateId, @RequestParam(name = "assessmentId") Long assessmentId) {
        return ResponseEntity.ok().body(assessmentService.getAssessmentDetails(candidateId, assessmentId));
    }

    @SneakyThrows
    @GetMapping("/download/{assessmentId}/report")
    public ResponseEntity<byte[]> downloadAssessmentCandidatesExcel(@PathVariable(name = "assessmentId") Long assessmentId) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=assessment" + assessmentId + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(generateExcel(assessmentService.getAssessmentCandidatesExcel(assessmentId)).readAllBytes());
    }
}
