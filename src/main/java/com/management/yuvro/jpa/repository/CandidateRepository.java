package com.management.yuvro.jpa.repository;

import com.management.yuvro.jpa.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(collectionResourceRel = "candidates", path = "candidates")
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    /**
     * Deletes candidates from batches based on the candidate ID.
     * This method is used to remove candidates from any associated batches before deleting the candidate.
     *
     * @param candidateId the ID of the candidate whose associations with batches should be removed
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Batch b WHERE b.id IN (SELECT b.id FROM Batch b JOIN b.candidates c WHERE c.id = :candidateId)")
    void deleteCandidatesFromBatches(@Param("candidateId") Long candidateId);

    @Query("SELECT c FROM Candidate c JOIN c.batches cl WHERE cl.batchId = :batchId")
    Page<Candidate> findAllByBatchId(@Param("batchId") Long batchId, Pageable pageable);
}
