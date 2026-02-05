package com.group05.TC_LLM_Generator.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating an existing project
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProjectRequest {

    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status;

    @Size(max = 100, message = "Jira site ID must not exceed 100 characters")
    private String jiraSiteId;

    @Size(max = 50, message = "Jira project key must not exceed 50 characters")
    private String jiraProjectKey;
}
