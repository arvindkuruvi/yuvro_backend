package com.management.yuvro.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.management.yuvro.jpa.entity.Candidate;
import com.management.yuvro.jpa.repository.CandidateRepository;
import com.management.yuvro.service.CandidateService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService {
	private final CandidateRepository candidateRepository;

	public CandidateServiceImpl(CandidateRepository candidateRepository) {
		this.candidateRepository = candidateRepository;
	}

	@Override
	public List<Candidate> saveListOfCandidates(List<Candidate> candidates) {
		log.info("Initalizing saving candidates");

		return candidateRepository.saveAll(candidates);
	}
}