package com.group05.TC_LLM_Generator.infrastructure.persistence.mapper;

import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.UserEntity;
import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.Workspace;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceMapper {

    @org.springframework.beans.factory.annotation.Autowired
    private ProjectMapper projectMapper;

    public com.group05.TC_LLM_Generator.domain.model.entity.Workspace toDomain(Workspace entity) {
        if (entity == null) {
            return null;
        }
        return com.group05.TC_LLM_Generator.domain.model.entity.Workspace.builder()
                .id(entity.getWorkspaceId())
                .ownerId(entity.getOwnerUser() != null ? entity.getOwnerUser().getUserId() : null)
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .projects(entity.getProjects() != null ? entity.getProjects().stream()
                        .map(projectMapper::toDomain)
                        .collect(java.util.stream.Collectors.toList())
                        : java.util.Collections.emptyList())
                .build();
    }

    public Workspace toEntity(com.group05.TC_LLM_Generator.domain.model.entity.Workspace domain, UserEntity ownerUser) {
        if (domain == null) {
            return null;
        }
        return Workspace.builder()
                .workspaceId(domain.getId())
                .ownerUser(ownerUser)
                .name(domain.getName())
                .description(domain.getDescription())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
