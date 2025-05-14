package com.management.yuvro.service;

import java.util.List;

import com.management.yuvro.dto.CandidateDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.jpa.entity.Candidate;

public interface CandidateService {
	List<Candidate> saveListOfCandidates(List<Candidate> candidates);

    PageDTO<CandidateDTO> getAllCandidates(int page, int size);
}
