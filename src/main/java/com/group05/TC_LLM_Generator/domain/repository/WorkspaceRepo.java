package com.group05.TC_LLM_Generator.domain.repository;

import com.group05.TC_LLM_Generator.domain.model.entity.Workspace;

import java.util.List;
import java.util.UUID;

public interface WorkspaceRepo {
    List<Workspace> findAllByOwnerId(UUID ownerId);

    Workspace save(Workspace workspace);
}
