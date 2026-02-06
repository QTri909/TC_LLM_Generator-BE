package com.group05.TC_LLM_Generator.infrastructure.persistence.mapper;

import com.group05.TC_LLM_Generator.domain.model.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    @org.springframework.beans.factory.annotation.Autowired
    private ProjectMemberMapper projectMemberMapper;

    public Project toDomain(com.group05.TC_LLM_Generator.infrastructure.persistence.entity.Project entity) {
        if (entity == null) {
            return null;
        }
        return Project.builder()
                .id(entity.getProjectId())
                .workspaceId(entity.getWorkspace() != null ? entity.getWorkspace().getWorkspaceId() : null)
                .createdByUserId(entity.getCreatedByUser() != null ? entity.getCreatedByUser().getUserId() : null)
                .projectKey(entity.getProjectKey())
                .name(entity.getName())
                .description(entity.getDescription())
                .jiraSiteId(entity.getJiraSiteId())
                .jiraProjectKey(entity.getJiraProjectKey())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .members(entity.getMembers() != null ? entity.getMembers().stream()
                        .map(projectMemberMapper::toDomain)
                        .collect(java.util.stream.Collectors.toList())
                        : java.util.Collections.emptyList())
                .build();
    }
}
