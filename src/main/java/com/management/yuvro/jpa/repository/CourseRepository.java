package com.management.yuvro.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.Course;

@RepositoryRestResource(collectionResourceRel = "courses", path = "courses")
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByBatches_BatchId(Long batchId, PageRequest pageRequest);
}
