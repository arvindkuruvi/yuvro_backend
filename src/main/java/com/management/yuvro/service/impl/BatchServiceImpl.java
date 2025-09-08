package com.management.yuvro.service.impl;

import com.management.yuvro.dto.BatchDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.AddCandidatesToBatchRequest;
import com.management.yuvro.dto.request.AddCoursesToBatchRequest;
import com.management.yuvro.dto.request.AddInstitutionsToBatchRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.exceptions.EntityNotFoundException;
import com.management.yuvro.jpa.entity.Batch;
import com.management.yuvro.jpa.entity.Candidate;
import com.management.yuvro.jpa.entity.Course;
import com.management.yuvro.jpa.entity.Institution;
import com.management.yuvro.jpa.repository.BatchRepository;
import com.management.yuvro.jpa.repository.CandidateRepository;
import com.management.yuvro.jpa.repository.CourseRepository;
import com.management.yuvro.jpa.repository.InstitutionRepository;
import com.management.yuvro.mapper.BatchMapper;
import com.management.yuvro.service.BatchService;
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
public class BatchServiceImpl implements BatchService {

    @Autowired
    BatchMapper batchMapper;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    InstitutionRepository institutionRepository;

    @Override
    public PageDTO<BatchDTO> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("batchId"));
        return batchMapper.convertPageOfBatchToPageDTOOfBatchDTO(batchRepository.findAll(pageable));
    }

    @Override
    public Batch findBatchById(Long id) {
        return batchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, BATCH, id)));
    }

    @Override
    public CommonApiResponse addCoursesToBatch(AddCoursesToBatchRequest request) {

        Batch batch = findBatchById(request.getBatchId());

        List<Course> courses = courseRepository.findAllById(request.getCourseIds());

        if (courses.isEmpty()) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, COURSE, request.getCourseIds()));
        } else {
            for (Course course : courses) {
                batch.getCourses().add(course);
                course.getBatches().add(batch);
            }
            courseRepository.saveAll(courses);
            batchRepository.save(batch);
        }
        return new CommonApiResponse(SAVE_SUCCESS, true);
    }

    @Override
    public CommonApiResponse addCandidatesToBatch(AddCandidatesToBatchRequest request) {

        Batch batch = findBatchById(request.getBatchId());

        List<Candidate> candidates = candidateRepository.findAllById(request.getCandidateIds());

        if (candidates.isEmpty()) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, COURSE, request.getCandidateIds()));
        } else {
            for (Candidate candidate : candidates) {
                batch.getCandidates().add(candidate);
                candidate.getBatches().add(batch);
            }
            candidateRepository.saveAll(candidates);
            batchRepository.save(batch);
        }
        return new CommonApiResponse(SAVE_SUCCESS, true);
    }

    @Override
    public CommonApiResponse addInstitutionsToBatch(AddInstitutionsToBatchRequest request) {

        Batch batch = findBatchById(request.getBatchId());

        List<Institution> institutions = institutionRepository.findAllById(request.getInstitutionIds());

        if (institutions.isEmpty()) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, COURSE, request.getInstitutionIds()));
        } else {
            for (Institution institution : institutions) {
                batch.getInstitutions().add(institution);
                institution.getBatches().add(batch);
            }
            institutionRepository.saveAll(institutions);
            batchRepository.save(batch);
        }
        return new CommonApiResponse(SAVE_SUCCESS, true);
    }
}
