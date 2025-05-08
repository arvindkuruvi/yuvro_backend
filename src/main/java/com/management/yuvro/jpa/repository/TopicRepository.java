package com.management.yuvro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.Topic;

@RepositoryRestResource(collectionResourceRel = "topics", path = "topics")
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
