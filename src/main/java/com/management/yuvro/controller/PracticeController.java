package com.management.yuvro.controller;

import com.management.yuvro.dto.request.AttemptPracticeRequest;
import com.management.yuvro.dto.request.SavePracticeQuestionRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.GetPracticeQuestionResponse;
import com.management.yuvro.dto.response.PracticeResultResponse;
import com.management.yuvro.service.PracticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CommonApiResponse> attemptPractice(@RequestBody AttemptPracticeRequest request) {
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
}
