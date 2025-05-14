package com.management.yuvro.service.impl;

import java.util.List;

import com.management.yuvro.dto.CandidateDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.mapper.CandidateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.management.yuvro.jpa.entity.Candidate;
import com.management.yuvro.jpa.repository.CandidateRepository;
import com.management.yuvro.service.CandidateService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService {
	private final CandidateRepository candidateRepository;

	@Autowired
	CandidateMapper candidateMapper;

	public CandidateServiceImpl(CandidateRepository candidateRepository) {
		this.candidateRepository = candidateRepository;
	}

	@Override
	public List<Candidate> saveListOfCandidates(List<Candidate> candidates) {
		log.info("Initalizing saving candidates");

		return candidateRepository.saveAll(candidates);
	}

	@Override
	public PageDTO<CandidateDTO> getAllCandidates(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("candidateId"));
		return candidateMapper.convertPageOfCandidatesToPageDTOOfCandidateDTO(candidateRepository.findAll(pageable));
	}
}