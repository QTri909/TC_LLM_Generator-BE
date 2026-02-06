package com.group05.TC_LLM_Generator.infrastructure.persistence.mapper;

import com.group05.TC_LLM_Generator.domain.model.entity.ProjectMember;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberMapper {

    public ProjectMember toDomain(com.group05.TC_LLM_Generator.infrastructure.persistence.entity.ProjectMember entity) {
        if (entity == null) {
            return null;
        }
        return ProjectMember.builder()
                .id(entity.getProjectMemberId())
                .projectId(entity.getProject() != null ? entity.getProject().getProjectId() : null)
                .userId(entity.getUser() != null ? entity.getUser().getUserId() : null)
                .role(entity.getRole())
                .joinedAt(entity.getJoinedAt())
                .build();
    }
}
