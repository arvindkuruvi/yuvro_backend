package com.management.yuvro.service;

import java.util.List;

import com.management.yuvro.jpa.entity.Candidate;

public interface CandidateService {
	List<Candidate> saveListOfCandidates(List<Candidate> candidates);
}
