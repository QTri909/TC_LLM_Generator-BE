package com.group05.TC_LLM_Generator.presentation.controller;

import com.group05.TC_LLM_Generator.application.service.UserService;
import com.group05.TC_LLM_Generator.application.service.WorkspaceService;
import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.UserEntity;
import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.Workspace;
import com.group05.TC_LLM_Generator.presentation.assembler.WorkspaceModelAssembler;
import com.group05.TC_LLM_Generator.presentation.dto.common.ApiResponse;
import com.group05.TC_LLM_Generator.presentation.dto.request.CreateWorkspaceRequest;
import com.group05.TC_LLM_Generator.presentation.dto.request.UpdateWorkspaceRequest;
import com.group05.TC_LLM_Generator.presentation.dto.response.WorkspaceResponse;
import com.group05.TC_LLM_Generator.presentation.exception.ResourceNotFoundException;
import com.group05.TC_LLM_Generator.presentation.mapper.WorkspacePresentationMapper;
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
 * REST Controller for Workspace CRUD operations.
 * Implements HATEOAS Level 3 REST API with wrapped responses and pagination.
 */
@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final UserService userService;
    private final WorkspacePresentationMapper mapper;
    private final WorkspaceModelAssembler assembler;
    private final PagedResourcesAssembler<Workspace> pagedResourcesAssembler;

    /**
     * Create a new workspace
     * POST /api/v1/workspaces
     */
    @PostMapping
    public ResponseEntity<ApiResponse<WorkspaceResponse>> createWorkspace(
            @Valid @RequestBody CreateWorkspaceRequest request) {

        UserEntity owner = userService.getUserById(request.getOwnerUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getOwnerUserId()));

        Workspace workspace = Workspace.builder()
                .ownerUser(owner)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Workspace savedWorkspace = workspaceService.createWorkspace(workspace);
        WorkspaceResponse response = assembler.toModel(savedWorkspace);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Workspace created successfully"));
    }

    /**
     * Get workspace by ID
     * GET /api/v1/workspaces/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkspaceResponse>> getWorkspaceById(@PathVariable("id") UUID id) {
        Workspace workspace = workspaceService.getWorkspaceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace", "id", id));

        WorkspaceResponse response = assembler.toModel(workspace);

        return ResponseEntity.ok(ApiResponse.success(response, "Workspace retrieved successfully"));
    }

    /**
     * Get all workspaces with pagination
     * GET /api/v1/workspaces?page=0&size=20&sort=createdAt,desc
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PagedModel<WorkspaceResponse>>> getAllWorkspaces(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Workspace> page = workspaceService.getAllWorkspaces(pageable);
        PagedModel<WorkspaceResponse> pagedModel = pagedResourcesAssembler.toModel(page, assembler);

        return ResponseEntity.ok(ApiResponse.success(pagedModel, "Workspaces retrieved successfully"));
    }

    /**
     * Update workspace by ID
     * PUT /api/v1/workspaces/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkspaceResponse>> updateWorkspace(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateWorkspaceRequest request) {

        Workspace existingWorkspace = workspaceService.getWorkspaceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace", "id", id));

        mapper.updateEntity(request, existingWorkspace);
        Workspace updatedWorkspace = workspaceService.updateWorkspace(id, existingWorkspace);
        WorkspaceResponse response = assembler.toModel(updatedWorkspace);

        return ResponseEntity.ok(ApiResponse.success(response, "Workspace updated successfully"));
    }

    /**
     * Delete workspace by ID
     * DELETE /api/v1/workspaces/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWorkspace(@PathVariable("id") UUID id) {
        if (!workspaceService.workspaceExists(id)) {
            throw new ResourceNotFoundException("Workspace", "id", id);
        }

        workspaceService.deleteWorkspace(id);

        return ResponseEntity.ok(ApiResponse.success("Workspace deleted successfully"));
    }

    /**
     * Get workspaces by owner user ID with pagination
     * GET /api/v1/workspaces/owner/{userId}?page=0&size=20
     */
    @GetMapping("/owner/{userId}")
    public ResponseEntity<ApiResponse<PagedModel<WorkspaceResponse>>> getWorkspacesByOwner(
            @PathVariable("userId") UUID userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Workspace> page = workspaceService.getWorkspacesByOwner(userId, pageable);
        PagedModel<WorkspaceResponse> pagedModel = pagedResourcesAssembler.toModel(page, assembler);

        return ResponseEntity.ok(ApiResponse.success(pagedModel, "Workspaces retrieved successfully"));
    }
}
