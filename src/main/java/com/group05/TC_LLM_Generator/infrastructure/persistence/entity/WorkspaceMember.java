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
 * JPA Entity for workspace_members table
 */
@Entity
@Table(name = "workspace_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "workspace_member_id", nullable = false)
    private UUID workspaceMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", referencedColumnName = "workspace_id", nullable = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

}
