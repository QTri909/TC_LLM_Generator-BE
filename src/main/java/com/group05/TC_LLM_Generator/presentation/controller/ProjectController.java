package com.group05.TC_LLM_Generator.presentation.controller;

import com.group05.TC_LLM_Generator.application.service.ProjectService;
import com.group05.TC_LLM_Generator.application.service.UserService;
import com.group05.TC_LLM_Generator.application.service.WorkspaceService;
import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.Project;
import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.UserEntity;
import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.Workspace;
import com.group05.TC_LLM_Generator.presentation.assembler.ProjectModelAssembler;
import com.group05.TC_LLM_Generator.presentation.dto.common.ApiResponse;
import com.group05.TC_LLM_Generator.presentation.dto.request.CreateProjectRequest;
import com.group05.TC_LLM_Generator.presentation.dto.request.UpdateProjectRequest;
import com.group05.TC_LLM_Generator.presentation.dto.response.ProjectResponse;
import com.group05.TC_LLM_Generator.presentation.exception.ResourceNotFoundException;
import com.group05.TC_LLM_Generator.presentation.mapper.ProjectPresentationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for Project CRUD operations.
 * Implements HATEOAS Level 3 REST API with wrapped responses and pagination.
 */
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final WorkspaceService workspaceService;
    private final UserService userService;
    private final ProjectPresentationMapper mapper;
    private final ProjectModelAssembler assembler;
    private final PagedResourcesAssembler<Project> pagedResourcesAssembler;

    /**
     * Create a new project
     * POST /api/v1/projects
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @Valid @RequestBody CreateProjectRequest request) {

        Workspace workspace = workspaceService.getWorkspaceById(request.getWorkspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace", "id", request.getWorkspaceId()));

        UserEntity creator = userService.getUserById(request.getCreatedByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getCreatedByUserId()));

        Project project = Project.builder()
                .workspace(workspace)
                .createdByUser(creator)
                .projectKey(request.getProjectKey())
                .name(request.getName())
                .description(request.getDescription())
                .jiraSiteId(request.getJiraSiteId())
                .jiraProjectKey(request.getJiraProjectKey())
                .status("ACTIVE")
                .build();

        Project savedProject = projectService.createProject(project);
        ProjectResponse response = assembler.toModel(savedProject);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Project created successfully"));
    }

    /**
     * Get project by ID
     * GET /api/v1/projects/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable("id") UUID id) {
        Project project = projectService.getProjectById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));

        ProjectResponse response = assembler.toModel(project);

        return ResponseEntity.ok(ApiResponse.success(response, "Project retrieved successfully"));
    }

    /**
     * Get project by project key
     * GET /api/v1/projects/key/{projectKey}
     */
    @GetMapping("/key/{projectKey}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectByKey(@PathVariable("projectKey") String projectKey) {
        Project project = projectService.getProjectByKey(projectKey)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "key", projectKey));

        ProjectResponse response = assembler.toModel(project);

        return ResponseEntity.ok(ApiResponse.success(response, "Project retrieved successfully"));
    }

    /**
     * Get all projects with pagination
     * GET /api/v1/projects?page=0&size=20&sort=createdAt,desc
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PagedModel<ProjectResponse>>> getAllProjects(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Project> page = projectService.getAllProjects(pageable);
        PagedModel<ProjectResponse> pagedModel = pagedResourcesAssembler.toModel(page, assembler);

        return ResponseEntity.ok(ApiResponse.success(pagedModel, "Projects retrieved successfully"));
    }

    /**
     * Update project by ID
     * PUT /api/v1/projects/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateProjectRequest request) {

        Project existingProject = projectService.getProjectById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));

        mapper.updateEntity(request, existingProject);
        Project updatedProject = projectService.updateProject(id, existingProject);
        ProjectResponse response = assembler.toModel(updatedProject);

        return ResponseEntity.ok(ApiResponse.success(response, "Project updated successfully"));
    }

    /**
     * Delete project by ID
     * DELETE /api/v1/projects/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable("id") UUID id) {
        if (!projectService.projectExists(id)) {
            throw new ResourceNotFoundException("Project", "id", id);
        }

        projectService.deleteProject(id);

        return ResponseEntity.ok(ApiResponse.success("Project deleted successfully"));
    }

    /**
     * Get projects by workspace ID with pagination
     * GET /api/v1/projects/workspace/{workspaceId}?page=0&size=20
     */
    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<ApiResponse<PagedModel<ProjectResponse>>> getProjectsByWorkspace(
            @PathVariable("workspaceId") UUID workspaceId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Project> page = projectService.getProjectsByWorkspace(workspaceId, pageable);
        PagedModel<ProjectResponse> pagedModel = pagedResourcesAssembler.toModel(page, assembler);

        return ResponseEntity.ok(ApiResponse.success(pagedModel, "Projects retrieved successfully"));
    }
}
