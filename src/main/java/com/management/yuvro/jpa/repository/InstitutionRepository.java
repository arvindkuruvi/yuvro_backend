package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "institutions", path = "institutions")
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
