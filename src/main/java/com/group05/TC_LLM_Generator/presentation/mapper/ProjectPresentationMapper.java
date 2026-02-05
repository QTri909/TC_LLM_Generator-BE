package com.group05.TC_LLM_Generator.presentation.mapper;

import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.Project;
import com.group05.TC_LLM_Generator.presentation.dto.request.UpdateProjectRequest;
import com.group05.TC_LLM_Generator.presentation.dto.response.ProjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * MapStruct mapper for Project presentation layer
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectPresentationMapper {

    /**
     * Map Project to ProjectResponse
     */
    @Mapping(target = "workspaceId", source = "workspace.workspaceId")
    @Mapping(target = "workspaceName", source = "workspace.name")
    @Mapping(target = "createdByUserId", source = "createdByUser.userId")
    @Mapping(target = "createdByUserName", source = "createdByUser.fullName")
    ProjectResponse toResponse(Project entity);

    /**
     * Map list of Project to list of ProjectResponse
     */
    List<ProjectResponse> toResponseList(List<Project> entities);

    /**
     * Update Project from UpdateProjectRequest
     */
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "workspace", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "projectKey", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateProjectRequest request, @MappingTarget Project entity);
}
