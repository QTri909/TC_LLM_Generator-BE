package com.group05.TC_LLM_Generator.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating an existing test plan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTestPlanRequest {

    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    private String description;

    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status;
}
