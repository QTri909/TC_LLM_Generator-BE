package com.group05.TC_LLM_Generator.infrastructure.persistence.repository;

import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.TestPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for TestPlan entity
 */
@Repository
public interface TestPlanRepository extends JpaRepository<TestPlan, UUID> {

    List<TestPlan> findByProject_ProjectId(UUID projectId);

    Page<TestPlan> findByProject_ProjectId(UUID projectId, Pageable pageable);

    List<TestPlan> findByProject_ProjectIdAndStatus(UUID projectId, String status);

    List<TestPlan> findByProject_ProjectIdOrderByCreatedAtDesc(UUID projectId);

    List<TestPlan> findByCreatedByUser_UserId(UUID userId);

    List<TestPlan> findByProject_ProjectIdAndNameContainingIgnoreCase(UUID projectId, String name);
}
