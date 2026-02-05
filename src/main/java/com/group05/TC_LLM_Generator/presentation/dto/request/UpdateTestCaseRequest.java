package com.group05.TC_LLM_Generator.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating an existing test case
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTestCaseRequest {

    @Size(max = 500, message = "Title must not exceed 500 characters")
    private String title;

    private String preconditions;

    private String steps;

    private String expectedResult;

    private String customFieldsJson;
}
