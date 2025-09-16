package com.management.yuvro.controller;

import java.util.List;

import com.management.yuvro.dto.CandidateAssessmentDTO;
import com.management.yuvro.dto.CandidateDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.QuestionsDTO;
import com.management.yuvro.dto.request.SaveCandidateDTO;
import com.management.yuvro.dto.response.CommonApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.management.yuvro.jpa.entity.Candidate;
import com.management.yuvro.service.CandidateService;

@RestController
@RequestMapping("/candidates/")
public class CandidateController {
    private CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("save-candidates")
    public ResponseEntity<List<CandidateDTO>> saveListOfCandidates(@RequestBody List<SaveCandidateDTO> candidates) {
        return ResponseEntity.ok().body(candidateService.saveListOfCandidates(candidates));
    }

    @GetMapping("/get-all-candidates")
    public ResponseEntity<PageDTO<CandidateDTO>> getAllCandidates(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(candidateService.getAllCandidates(page, size));
    }

    @GetMapping("/get-batch-candidates")
    public ResponseEntity<PageDTO<CandidateDTO>> getBatchCandidates(@RequestParam(name = "batchId") Long batchId, @RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(candidateService.getBatchCandidates(batchId, page, size));
    }

    @PostMapping("/delete-candidates")
    public ResponseEntity<CommonApiResponse> deleteCandidates(@RequestBody List<Long> candidateIds) {
        return ResponseEntity.ok().body(candidateService.deleteCandidates(candidateIds));
    }
}
