package com.management.yuvro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.Batch;

@RepositoryRestResource(collectionResourceRel = "batches", path = "batches")
public interface BatchRepository extends JpaRepository<Batch, Long> {
}
