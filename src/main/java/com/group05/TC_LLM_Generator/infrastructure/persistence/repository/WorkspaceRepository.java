package com.group05.TC_LLM_Generator.infrastructure.persistence.repository;

import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Workspace entity
 */
@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    List<Workspace> findByOwnerUser_UserId(UUID ownerUserId);

    Page<Workspace> findByOwnerUser_UserId(UUID ownerUserId, Pageable pageable);

    List<Workspace> findByName(String name);

    List<Workspace> findByNameContainingIgnoreCase(String name);
}
