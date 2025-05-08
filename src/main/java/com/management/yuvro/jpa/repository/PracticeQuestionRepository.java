package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.PracticeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "practice-questions", path = "practice-questions")
public interface PracticeQuestionRepository extends JpaRepository<PracticeQuestion, Long> {
}
