package com.management.yuvro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<List<Candidate>> saveListOfCandidates(@RequestBody List<Candidate> candidates) {
		return ResponseEntity.ok().body(candidateService.saveListOfCandidates(candidates));

	}
}
