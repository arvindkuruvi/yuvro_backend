package com.management.yuvro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.MCQOptions;

@RepositoryRestResource(collectionResourceRel = "mcq-options", path = "mcq-options")
public interface MCQOptionRepository extends JpaRepository<MCQOptions, Long> {
}
