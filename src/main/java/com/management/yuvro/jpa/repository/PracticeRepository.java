package com.management.yuvro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.Practice;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "practices", path = "practices")
public interface PracticeRepository extends JpaRepository<Practice, Long> {

    Optional<Practice> findByTopicIdAndCandidate_CandidateId(Long topicId, Long candidateId);
}
