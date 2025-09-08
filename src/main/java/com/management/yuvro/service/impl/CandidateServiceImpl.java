package com.management.yuvro.service.impl;

import com.management.yuvro.dto.CandidateDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.exceptions.EntityNotFoundException;
import com.management.yuvro.jpa.entity.Batch;
import com.management.yuvro.jpa.entity.Candidate;
import com.management.yuvro.jpa.repository.CandidateAssessmentRepository;
import com.management.yuvro.jpa.repository.CandidateRepository;
import com.management.yuvro.mapper.CandidateMapper;
import com.management.yuvro.service.CandidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.management.yuvro.constants.Constants.*;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    @Autowired
    CandidateAssessmentRepository candidateAssessmentRepository;

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

    @Override
    public CommonApiResponse deleteCandidates(List<Long> candidateIds) {

        for (Long candidateId : candidateIds) {
            if (candidateRepository.existsById(candidateId)) {
                log.info("Deleting candidate with ID :: {}", candidateId);
                candidateRepository.deleteCandidatesFromBatches(candidateId);

                candidateAssessmentRepository.deleteAllByCandidateId(candidateId);

                candidateRepository.deleteById(candidateId);
                log.info("Candidate with ID :: {} deleted successfully", candidateId);
            } else {
                log.warn("Candidate with ID :: {} does not exist", candidateId);
            }
        }

        return new CommonApiResponse("Candidates deleted successfully", true);
    }

    @Override
    public Candidate findCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, CANDIDATE, id)));
    }

    @Override
    public PageDTO<CandidateDTO> getBatchCandidates(Long batchId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return candidateMapper.convertPageOfCandidatesToPageDTOOfCandidateDTO(candidateRepository.findAllByBatchId(batchId, pageable));
    }
}