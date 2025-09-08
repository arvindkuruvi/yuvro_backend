package com.management.yuvro.controller;

import com.management.yuvro.dto.request.AttemptPracticeRequest;
import com.management.yuvro.dto.request.SavePracticeQuestionRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.GetPracticeQuestionResponse;
import com.management.yuvro.dto.response.PracticeResultResponse;
import com.management.yuvro.service.PracticeService;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.management.yuvro.utils.AppUtils.generateExcel;

@RestController
@RequestMapping("/practices/")
public class PracticeController {
    private PracticeService practiceService;

    public PracticeController(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @GetMapping("questions-to-practice")
    public ResponseEntity<GetPracticeQuestionResponse> getQuestionToPractice(@RequestParam(name = "topicId") Long topicId, @RequestParam(name = "candidateId") Long candidateId) {
        return ResponseEntity.ok().body(practiceService.getPracticeIfExists(candidateId, topicId));
    }

    @PostMapping("attempt-practice")
    public ResponseEntity<GetPracticeQuestionResponse> attemptPractice(@RequestBody AttemptPracticeRequest request) {
        return ResponseEntity.ok().body(practiceService.attemptPractice(request));
    }

    @PostMapping("save-practice-question")
    public ResponseEntity<CommonApiResponse> savePracticeQuestion(@RequestBody SavePracticeQuestionRequest request) {
        return ResponseEntity.ok().body(practiceService.savePracticeQuestion(request));
    }

    @GetMapping("practice-result")
    public ResponseEntity<PracticeResultResponse> getPracticeResult(@RequestParam(name = "practiceId") Long practiceId) {
        return ResponseEntity.ok().body(practiceService.getPracticeResult(practiceId));
    }

    @GetMapping("submit-practice")
    public ResponseEntity<CommonApiResponse> submitPractice(@RequestParam(name = "topicId") Long topicId, @RequestParam(name = "candidateId") Long candidateId) {
        return ResponseEntity.ok().body(practiceService.submitPractice(candidateId, topicId));
    }

    @SneakyThrows
    @GetMapping("/download/{topicId}/report")
    public ResponseEntity<byte[]> downloadTopicCandidatesExcel(@PathVariable(name = "topicId") Long topicId) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=topic" + topicId + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(generateExcel(practiceService.getTopicCandidatesExcel(topicId)).readAllBytes());
    }
}
