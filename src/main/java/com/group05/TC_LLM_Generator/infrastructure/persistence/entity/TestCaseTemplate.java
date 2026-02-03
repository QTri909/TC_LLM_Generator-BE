package com.group05.TC_LLM_Generator.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity for test_case_templates table
 */
@Entity
@Table(name = "test_case_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "test_case_template_id", nullable = false)
    private UUID testCaseTemplateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
    private Project project;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

}
