package com.management.yuvro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.management.yuvro.jpa.entity.Module;

@RepositoryRestResource(collectionResourceRel = "modules", path = "modules")
public interface ModuleRepository extends JpaRepository<Module, Long> {
}
