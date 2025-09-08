package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.Feedback;
import com.management.yuvro.projection.FeebackUsersProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "feedbacks", path = "feedbacks")
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @RestResource(exported = false)
    Page<Feedback> findAllByCandidateTask_CandidateTaskId(Long candidateTaskId, PageRequest pageRequest);

//    @Query("SELECT t.createdById as userId, ct.candidateId as candidateId FROM CandidateTask ct JOIN ct.task t WHERE ct.candidateTaskId = :candidateTaskId")
//    FeebackUsersProjection findTaskCreatedByIdAndCandidateIdByCandidateTaskId(@Param("candidateTaskId") Long candidateTaskId);
}
