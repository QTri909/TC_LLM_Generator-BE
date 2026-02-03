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
 * JPA Entity for audit_logs table
 */
@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "audit_log_id", nullable = false)
    private UUID auditLogId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "workspace_id", referencedColumnName = "workspace_id")
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private UUID entityId;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "data_json")
    @JdbcTypeCode(SqlTypes.JSON)
    private String dataJson;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

}
