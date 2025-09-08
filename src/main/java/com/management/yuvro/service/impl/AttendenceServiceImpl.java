package com.management.yuvro.service.impl;

import com.management.yuvro.dto.AttendenceDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.CreateAttendenceRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.jpa.entity.Attendence;
import com.management.yuvro.jpa.repository.AttendenceRepository;
import com.management.yuvro.mapper.AttendenceMapper;
import com.management.yuvro.service.AttendenceService;
import com.management.yuvro.service.BatchService;
import com.management.yuvro.service.CandidateService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class AttendenceServiceImpl implements AttendenceService {

    @Autowired
    BatchService batchService;
    @Autowired
    CandidateService candidateService;
    @Autowired
    AttendenceRepository attendenceRepository;
    @Autowired
    AttendenceMapper attendenceMapper;


    @Transactional
    @Override
    public CommonApiResponse createAttendence(CreateAttendenceRequest request) {

        var batch = batchService.findBatchById(request.getBatchId());

        request.getAttendees().forEach(attendee -> {
            log.info("Creating attendence for candidate: {}", attendee.getCandidateId());

            try {
                var candidate = candidateService.findCandidateById(attendee.getCandidateId());
                Attendence attendence = null;

                var attendenceOptional = attendenceRepository.findByBatch_BatchIdAndCandidate_CandidateIdAndAttendenceDate(request.getBatchId(), attendee.getCandidateId(), LocalDate.parse(request.getAttendenceDate()));

                if (attendenceOptional.isPresent()) {
                    attendence = attendenceOptional.get();

                    log.info("Updating attendence for candidate: {} on date: {}", attendee.getCandidateId(), request.getAttendenceDate());
                } else {
                    log.info("Creating new attendence for candidate: {} on date: {}", attendee.getCandidateId(), request.getAttendenceDate());
                    attendence = new Attendence();
                    attendence.setCandidate(candidate);
                    attendence.setBatch(batch);
                }

                attendence.setPresent(attendee.isPresent());
                attendence.setAttendenceDate(LocalDate.parse(request.getAttendenceDate()));
                attendenceRepository.save(attendence);
                log.info("Attendence saved for candidate: {}", attendee.getCandidateId());
            } catch (RuntimeException e) {
                log.error("Error {} creating attendence for candidate: {}", e.getMessage(), attendee.getCandidateId());
            }
        });

        return new CommonApiResponse("Attendence saved successfully for " + LocalDateTime.now().toString(), true);
    }


    @Override
    public PageDTO<AttendenceDTO> getAttendence(Long batchId, String attendenceDate, int page, int size) {

        LocalDate date = LocalDate.parse(attendenceDate);
        log.info("Fetching candidates for attendence date: {}", date);
        return attendenceMapper
                .mapPageOfGetBatchAttendenceProjectionToPageDTOOfAttendenceDTO(
                        attendenceRepository.findAttendenceByBatchIdAndAttendenceDate(
                                batchId, date,
                                PageRequest.of(page, size, Sort.by("candidateName"))));
    }
}
