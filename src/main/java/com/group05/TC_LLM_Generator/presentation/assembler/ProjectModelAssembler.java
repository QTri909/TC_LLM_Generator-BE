package com.group05.TC_LLM_Generator.presentation.assembler;

import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.Project;
import com.group05.TC_LLM_Generator.presentation.controller.ProjectController;
import com.group05.TC_LLM_Generator.presentation.dto.response.ProjectResponse;
import com.group05.TC_LLM_Generator.presentation.mapper.ProjectPresentationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * HATEOAS assembler for Project resources
 */
@Component
@RequiredArgsConstructor
public class ProjectModelAssembler implements RepresentationModelAssembler<Project, ProjectResponse> {

    private final ProjectPresentationMapper mapper;

    @Override
    public ProjectResponse toModel(Project entity) {
        ProjectResponse response = mapper.toResponse(entity);

        // Add HATEOAS links
        response.add(linkTo(methodOn(ProjectController.class).getProjectById(entity.getProjectId())).withSelfRel());
        response.add(linkTo(methodOn(ProjectController.class).updateProject(entity.getProjectId(), null)).withRel("update"));
        response.add(linkTo(methodOn(ProjectController.class).deleteProject(entity.getProjectId())).withRel("delete"));
        response.add(linkTo(methodOn(ProjectController.class).getAllProjects(null)).withRel("projects"));

        return response;
    }

    @Override
    public CollectionModel<ProjectResponse> toCollectionModel(Iterable<? extends Project> entities) {
        CollectionModel<ProjectResponse> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);

        collectionModel.add(linkTo(methodOn(ProjectController.class).getAllProjects(null)).withSelfRel());

        return collectionModel;
    }
}
