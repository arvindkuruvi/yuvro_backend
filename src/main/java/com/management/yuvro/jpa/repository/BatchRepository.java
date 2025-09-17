package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(exported = false)
//@RepositoryRestResource(collectionResourceRel = "batches", path = "batches")
public interface BatchRepository extends JpaRepository<Batch, Long> {
    boolean existsByBatchName(String batchName);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM BATCH_CANDIDATE WHERE batch_id = :batchId")
    void deleteBatchCandidateByBatchId(@Param("batchId") Long batchId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM batch_institution WHERE batch_id = :batchId")
    void deleteBatchInstitutionByBatchId(@Param("batchId") Long batchId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM batch_course WHERE batch_id = :batchId")
    void deleteBatchCourseByBatchId(@Param("batchId") Long batchId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM Assessment WHERE batch_id = :batchId")
    void deleteAssessmentsByBatchId(@Param("batchId") Long batchId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM Attendence WHERE batch_id = :batchId")
    void deleteAttendenceByBatchId(@Param("batchId") Long batchId);

}
