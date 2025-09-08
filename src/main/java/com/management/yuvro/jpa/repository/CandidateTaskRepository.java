package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.CandidateTask;
import com.management.yuvro.projection.GetCandidateTasksProjection;
import com.management.yuvro.projection.ViewTaskCandidatesProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "candidateTasks", path = "candidate-tasks")
public interface CandidateTaskRepository extends JpaRepository<CandidateTask, Long> {
    @Query("""
            SELECT
                ct as candidateTask,
                ct.candidate.candidateId as candidateId,
                ct.candidateTaskId as candidateTaskId,
                ct.submissionDateTime as submissionDateTime,
                t.taskId as taskId, t.createdDateTime as createdDateTime,
                t.createdById as createdById,t.createdByName as createdByName, t.title as title, t.description as description, ct.status as status
            FROM 
                CandidateTask ct JOIN ct.task t
            WHERE 
                ct.candidate.candidateId = :candidateId AND t.sent = true
            ORDER BY 
                t.createdDateTime DESC, ct.submissionDateTime DESC
            """)
    Page<GetCandidateTasksProjection> findCandidateTasks(@Param("candidateId") Long candidateId, PageRequest pageRequest);

    @Query("""
            SELECT
                ct.candidate.name as candidateName,
                ct as candidateTask,
                ct.candidate.candidateId as candidateId,
                ct.candidateTaskId as candidateTaskId,
                ct.submissionDateTime as submissionDateTime,
                t.taskId as taskId, t.createdDateTime as createdDateTime,
                t.createdById as createdById,t.createdByName as createdByName, t.title as title, t.description as description, ct.status as status
            FROM
                CandidateTask ct JOIN ct.task t
            WHERE t.taskId = :taskId
            ORDER BY candidateName
            """)
    Page<ViewTaskCandidatesProjection> findAllCandidateTasksWithNamesByTaskId(@Param("taskId") Long taskId, PageRequest pageRequest);

    @Query("""
            SELECT
                ct.candidate.name as candidateName,
                ct as candidateTask,
                ct.candidate.candidateId as candidateId,
                ct.candidateTaskId as candidateTaskId,
                ct.submissionDateTime as submissionDateTime,
                t.taskId as taskId, t.createdDateTime as createdDateTime, ct.status as status
            FROM
                CandidateTask ct JOIN ct.task t
            WHERE t.taskId = :taskId
            ORDER BY candidateName
            """)
    List<ViewTaskCandidatesProjection> findAllTaskCandidatesByTaskId(@Param("taskId") Long taskId);


    @Query("""
            SELECT ct.status as status
            FROM CandidateTask ct JOIN ct.task t
            WHERE t.taskId = :taskId
            """)
    List<String> getCandidateTasksStatus(@Param("taskId") Long taskId);
}