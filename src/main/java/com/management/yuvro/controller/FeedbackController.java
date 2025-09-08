package com.management.yuvro.controller;

import com.management.yuvro.dto.FeedbackDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.FeedbackRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedbacks/")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/save-feedback")
    public ResponseEntity<CommonApiResponse> createFeedback(@RequestBody FeedbackRequest request) {
        return ResponseEntity.ok().body(feedbackService.createFeedback(request));
    }

    @GetMapping("/get-feedbacks")
    public ResponseEntity<PageDTO<FeedbackDTO>> getFeedbacks(@RequestParam(name = "candidateTaskId") Long candidateTaskId, @RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(feedbackService.getFeedbacks(candidateTaskId, page, size));
    }
}
