package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.Attendence;
import com.management.yuvro.projection.GetBatchAttendenceProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "attendences", path = "attendences")
public interface AttendenceRepository extends JpaRepository<Attendence, Long> {

    @Query("""
            SELECT 
            COALESCE(a.attendenceId, 0) AS attendenceId,
            COALESCE(a.present, false) AS present,
            c.id AS candidateId, 
            b.batchId AS batchId,
            COALESCE(a.attendenceDate, NULL) AS attendenceDate,
            c.name AS candidateName
            FROM Candidate c
            JOIN c.batches b ON b.batchId = :batchId
            LEFT JOIN Attendence a ON a.candidate = c AND a.attendenceDate = :date
            WHERE b.batchId = :batchId
            ORDER BY candidateName
            """)
    Page<GetBatchAttendenceProjection> findAttendenceByBatchIdAndAttendenceDate(@Param("batchId") Long batchId, @Param("date") LocalDate date, PageRequest pageRequest);

    Optional<Attendence> findByBatch_BatchIdAndCandidate_CandidateIdAndAttendenceDate(Long batchId, Long candidateId, LocalDate attendenceDate);
}
