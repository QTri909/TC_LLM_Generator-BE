package com.group05.TC_LLM_Generator.infrastructure.persistence.adapter;

import com.group05.TC_LLM_Generator.domain.model.entity.Workspace;
import com.group05.TC_LLM_Generator.domain.repository.WorkspaceRepo;
import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.UserEntity;
import com.group05.TC_LLM_Generator.infrastructure.persistence.mapper.WorkspaceMapper;
import com.group05.TC_LLM_Generator.infrastructure.persistence.repository.UserRepository;
import com.group05.TC_LLM_Generator.infrastructure.persistence.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WorkspaceRepoAdapter implements WorkspaceRepo {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final WorkspaceMapper workspaceMapper;

    @Override
    public List<Workspace> findAllByOwnerId(UUID ownerId) {
        return workspaceRepository.findByOwnerUser_UserId(ownerId).stream()
                .map(workspaceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Workspace save(Workspace workspace) {
        UserEntity owner = userRepository.findById(workspace.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + workspace.getOwnerId()));

        var entity = workspaceMapper.toEntity(workspace, owner);
        var savedEntity = workspaceRepository.save(entity);
        return workspaceMapper.toDomain(savedEntity);
    }
}
