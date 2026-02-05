package com.group05.TC_LLM_Generator.presentation.mapper;

import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.TestPlan;
import com.group05.TC_LLM_Generator.presentation.dto.request.CreateTestPlanRequest;
import com.group05.TC_LLM_Generator.presentation.dto.request.UpdateTestPlanRequest;
import com.group05.TC_LLM_Generator.presentation.dto.response.TestPlanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * MapStruct mapper for TestPlan presentation layer
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TestPlanPresentationMapper {

    /**
     * Map TestPlan to TestPlanResponse
     */
    @Mapping(target = "projectId", source = "project.projectId")
    @Mapping(target = "createdByUserId", source = "createdByUser.userId")
    @Mapping(target = "createdByUserFullName", source = "createdByUser.fullName")
    TestPlanResponse toResponse(TestPlan entity);

    /**
     * Map list of TestPlan to list of TestPlanResponse
     */
    List<TestPlanResponse> toResponseList(List<TestPlan> entities);

    /**
     * Map CreateTestPlanRequest to TestPlan
     */
    @Mapping(target = "testPlanId", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    TestPlan toEntity(CreateTestPlanRequest request);

    /**
     * Update TestPlan from UpdateTestPlanRequest
     */
    @Mapping(target = "testPlanId", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(UpdateTestPlanRequest request, @MappingTarget TestPlan entity);
}
