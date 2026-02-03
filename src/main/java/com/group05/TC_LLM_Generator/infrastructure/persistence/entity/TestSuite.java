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
 * JPA Entity for test_suites table
 */
@Entity
@Table(name = "test_suites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSuite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "test_suite_id", nullable = false)
    private UUID testSuiteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
    private Project project;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

}
