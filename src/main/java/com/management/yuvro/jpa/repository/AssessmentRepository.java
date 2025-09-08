package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.Assessment;
import com.management.yuvro.projection.GetAssessmentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "assessments", path = "assessments")
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    @RestResource(exported = false)
    Page<Assessment> findAllByBatch_BatchId(Long batchId, PageRequest pageRequest);


    @RestResource(exported = false)
    Page<Assessment> findAllByCreatedBy(String createdBy, PageRequest pageRequest);

    @RestResource(exported = false)
    Page<Assessment> findAllBySentAndBatch_BatchId(boolean senFlag, Long candidateId, Long batchId, PageRequest pageRequest);

    @Query("""
            SELECT COALESCE(ca.status, 'PENDING') as status, a.assessmentId as assessmentId, a.assessmentType as assessmentType, a.assessmentName as assessmentName,
                   a.description as description, a.subject as subject, a.topic as topic, 
                   a.createdDateTime as createdDateTime, a.startDateTime as startDateTime, 
                   a.dueDateTime as dueDateTime, a.durationInHours as durationInHours, 
                   a.totalQuestions as totalQuestions
            FROM Assessment a LEFT JOIN CandidateAssessment ca ON a.assessmentId = ca.assessment.assessmentId AND ca.candidateId = :candidateId 
            WHERE a.sent = true AND a.batch.batchId = :batchId
            """)
    Page<GetAssessmentProjection> findAllAssessmentDetailsByCandidateIdAndBatchId(@Param("candidateId") Long candidateId, @Param("batchId") Long batchId, PageRequest pageRequest);
}