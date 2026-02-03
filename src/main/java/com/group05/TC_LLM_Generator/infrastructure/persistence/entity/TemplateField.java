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
 * JPA Entity for template_fields table
 */
@Entity
@Table(name = "template_fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateField {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "template_field_id", nullable = false)
    private UUID templateFieldId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_template_id", referencedColumnName = "test_case_template_id", nullable = false)
    private TestCaseTemplate testCaseTemplate;

    @Column(name = "field_key", nullable = false, length = 100)
    private String fieldKey;

    @Column(name = "field_label", nullable = false, length = 255)
    private String fieldLabel;

    @Column(name = "field_type", nullable = false, length = 50)
    private String fieldType;

    @Column(name = "required", nullable = false)
    private Boolean isRequired;

    @Column(name = "order_no", nullable = false)
    private Integer displayOrder;

}
