package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.CandidateAssessment;
import com.management.yuvro.projection.AssessmentCandidateProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "candidateAssessments", path = "candidate-assessments")
public interface CandidateAssessmentRepository extends JpaRepository<CandidateAssessment, Long> {

    Optional<CandidateAssessment> findByCandidateIdAndAssessment_AssessmentId(Long candidateId, Long assessmentId);

    @RestResource(exported = false)
    @Query("""
            SELECT ca.candidateAssessmentId as candidateAssessmentId,
            ca.status as status, ca.remainingDuration as remainingDuration, ca.lastAttendedQuestion as lastAttendedQuestion,
            ca.candidateId as candidateId, ca.score as score, ca.totalCorrectAnswers as totalCorrectAnswers,
            ca.totalInCorrectAnswers as totalInCorrectAnswers, ca.totalNotAttemptedQuestions as totalNotAttemptedQuestions, ca.submissionDateTime as submissionDateTime,
            c.name as name
             FROM CandidateAssessment ca 
            JOIN Candidate c ON ca.candidateId = c.candidateId 
            WHERE ca.assessment.assessmentId = :assessmentId
            """)
    Page<AssessmentCandidateProjection> findAllByAssessment_AssessmentId(Long assessmentId, PageRequest pageRequest);

    List<CandidateAssessment> findAllByAssessment_AssessmentId(Long assessmentId);

    void deleteAllByCandidateId(Long candidateId);

    @RestResource(exported = false)
    @Query("""
            SELECT ca.candidateAssessmentId as candidateAssessmentId,
            ca.status as status,
            ca.candidateId as candidateId,
            ca.score as score,
            ca.totalCorrectAnswers as totalCorrectAnswers,
            ca.totalInCorrectAnswers as totalInCorrectAnswers,
            ca.totalNotAttemptedQuestions as totalNotAttemptedQuestions,
            ca.submissionDateTime as submissionDateTime,
             c.name as name
             FROM CandidateAssessment ca 
            JOIN Candidate c ON ca.candidateId = c.candidateId 
            WHERE ca.assessment.assessmentId = :assessmentId
            """)
    List<AssessmentCandidateProjection> findAllCandidatesByAssessmentId(Long assessmentId);
}
