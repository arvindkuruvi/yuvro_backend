package com.management.yuvro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.PracticeQuestion;

@RepositoryRestResource(collectionResourceRel = "practice-questions", path = "practice-questions")
public interface CandidatePracticeQuestionRepository extends JpaRepository<PracticeQuestion, Long> {
}
