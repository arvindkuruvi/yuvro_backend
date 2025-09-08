package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "tasks", path = "tasks")
public interface TaskRepository extends JpaRepository<Task, Long> {
    @RestResource(exported = false)
    Page<Task> findAllByBatch_BatchId(Long batchId, PageRequest pageRequest);
}
