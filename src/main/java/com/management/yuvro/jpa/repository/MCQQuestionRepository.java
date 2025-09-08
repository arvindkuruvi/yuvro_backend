package com.management.yuvro.jpa.repository;

import java.util.List;
import java.util.Optional;

import com.management.yuvro.jpa.entity.CandidateAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.MCQQuestion;

@RepositoryRestResource(collectionResourceRel = "questions", path = "questions")
public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, Long> {

    @Query(value = "SELECT q FROM MCQQuestion q WHERE q.topic.topicId = :topicId")
    List<MCQQuestion> getByTopic(@Param("topicId") Long topicId);

    @Query(value = "SELECT COUNT(q) FROM MCQQuestion q WHERE q.topic.topicId = :topicId")
    long countByTopicId(@Param("topicId") Long topicId);
    
    List<MCQQuestion> findAllByAssessment_AssessmentId(Long assessmentId);

}