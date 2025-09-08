package com.management.yuvro.jpa.repository;

import com.management.yuvro.projection.PracticeCandidatesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.Practice;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "practices", path = "practices")
public interface PracticeRepository extends JpaRepository<Practice, Long> {

    Optional<Practice> findByTopicIdAndCandidate_CandidateId(Long topicId, Long candidateId);

    @Query("""
        SELECT 
            c.candidateId as candidateId,
            c.name as candidateName,
            COALESCE(p.status, 'PENDING') as status,
            p.attemptedDateTime as attemptedDateTime,
            p.submittedDateTime as submittedDateTime,
            p.topicId as topicId,
            p.practiceId as practiceId,
            p.attemptedDateTime as attempted,
            p.submittedDateTime as completed
        FROM Practice p
        JOIN candidate c on c.candidateId = p.candidate.candidateId
        WHERE p.topicId = :topicId
    """)
  List<PracticeCandidatesProjection> findAllCandidatesByTopicId(@Param("topicId") Long topicId);
}
