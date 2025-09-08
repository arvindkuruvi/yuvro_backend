package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.AttemptedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "attemptedQuestions", path = "attempt-questions")
public interface AttemptedQuestionRepository extends JpaRepository<AttemptedQuestion, Long> {
    Optional<AttemptedQuestion> findByQuestionId(Long questionId);
}
