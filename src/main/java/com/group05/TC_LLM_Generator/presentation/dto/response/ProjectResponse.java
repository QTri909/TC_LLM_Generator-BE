package com.group05.TC_LLM_Generator.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO for Project entity with HATEOAS support
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse extends RepresentationModel<ProjectResponse> {

    private UUID projectId;
    private UUID workspaceId;
    private String workspaceName;
    private UUID createdByUserId;
    private String createdByUserName;
    private String projectKey;
    private String name;
    private String description;
    private String jiraSiteId;
    private String jiraProjectKey;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
