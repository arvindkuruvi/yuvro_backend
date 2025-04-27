package com.management.yuvro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.SubTopic;

@RepositoryRestResource(collectionResourceRel = "sub-topics", path = "sub-topics")
public interface SubTopicRepository extends JpaRepository<SubTopic, Long> {
}
