package com.management.yuvro.service.impl;

import com.management.yuvro.dto.BatchDTO;
import com.management.yuvro.dto.CourseDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.*;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.CreateBatchResponse;
import com.management.yuvro.exceptions.EntityExistsException;
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
import com.management.yuvro.mapper.CourseMapper;
import com.management.yuvro.service.BatchService;
import jakarta.transaction.Transactional;
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
    CourseMapper courseMapper;

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

        validateCourseExists(courses);

        saveCoursesToBatch(courses, batch);

        return new CommonApiResponse(SAVE_SUCCESS, true);
    }

    private void saveCoursesToBatch(List<Course> courses, Batch batch) {
        log.info("Associating {} courses with batch ID: {}", courses.size(), batch.getBatchId());
        for (Course course : courses) {
            log.info("Adding course ID: {} to batch ID: {}", course.getCourseId(), batch.getBatchId());
            batch.getCourses().add(course);
            course.getBatches().add(batch);
        }
        log.info("Saving updated courses and batch to the database");
        courseRepository.saveAll(courses);
        batchRepository.save(batch);
        log.info("Successfully associated courses with batch ID: {}", batch.getBatchId());
    }

    private void validateCourseExists(List<Course> courses) {
        var courseIds = courses.stream().map(Course::getCourseId).toList();
        log.info("Validating existence of courses with IDs: {}", courseIds);

        if (courses.isEmpty()) {
            log.error("No courses found for IDs: {}", courseIds);
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, COURSE, courseIds));
        } else log.info("Found {} courses for provided IDs", courses.size());
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

    @Override
    public CreateBatchResponse createBatch(CreateBatchRequest request) {

        List<Course> courses = courseRepository.findAllById(request.getCourseIds());
        Long batchId = null;

        if (batchRepository.existsByBatchName(request.getBatchName())) {
            log.error("Batch with name '{}' already exists", request.getBatchName());
            throw new EntityExistsException(String.format(ENTITY_EXISTS, BATCH, request.getBatchName()));
        } else {
            log.info("Creating new batch with name: {}", request.getBatchName());
            var batch = batchMapper.mapCreateBatchRequestToBatch(request);
            batch = batchRepository.save(batch);

            batchId = batch.getBatchId();

            validateCourseExists(courses);

            saveCoursesToBatch(courses, batch);

            log.info("Batch created successfully with ID: {}", batchId);
        }
        var response = new CreateBatchResponse();
        response.setBatchId(batchId);
        response.setMessage(SAVE_SUCCESS);
        response.setSuccess(true);

        return response;
    }

    @Override
    public PageDTO<CourseDTO> getCoursesByBatchId(Long batchId, int page, int size) {
        log.info("Fetching courses for batch ID: {} with pagination - page: {}, size: {}", batchId, page, size);
        return courseMapper.convertPageOfCourseToPageDTOOfCourseDTO(courseRepository.findByBatches_BatchId(batchId, PageRequest.of(page, size)));
    }

    @Transactional
    @Override
    public CommonApiResponse deleteBatches(List<Long> batchIds) {

        for (var batchId : batchIds) {
            if (batchRepository.existsById(batchId)) {
                log.info("Deleting batch with ID: {}", batchId);

                batchRepository.deleteBatchCandidateByBatchId(batchId);
                batchRepository.deleteBatchInstitutionByBatchId(batchId);
                batchRepository.deleteBatchCourseByBatchId(batchId);
                batchRepository.deleteAssessmentsByBatchId(batchId);
                batchRepository.deleteAttendenceByBatchId(batchId);

                batchRepository.deleteById(batchId);

                log.info("Batch with ID: {} deleted successfully", batchId);

            } else log.error("Batch with ID '{}' does not exist", batchId);
        }

        return new CommonApiResponse(DELETE_SUCCESS, true);
    }

    @Override
    public CommonApiResponse editBatch(EditBatchRequest request) {
        log.info("Editing batch with ID: {}", request.getBatchId());
        Batch batch = findBatchById(request.getBatchId());

        List<Course> courses = courseRepository.findAllById(request.getCourses());

        validateCourseExists(courses);

        log.info("Updating batch details for ID: {}", request.getBatchId());

        batch.setBatchTitle(request.getBatchTitle());
        batch.setDescription(request.getDescription());
        batch.setDuration(request.getDuration());

        batch.getCourses().clear();

        batch = batchRepository.save(batch);

        saveCoursesToBatch(courses, batch);

        log.info("Batch with ID: {} updated successfully", request.getBatchId());

        return new CommonApiResponse(SAVE_SUCCESS, true);

    }
}
