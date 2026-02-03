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
 * JPA Entity for test_suite_items table
 */
@Entity
@Table(name = "test_suite_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSuiteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "test_suite_item_id", nullable = false)
    private UUID testSuiteItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_suite_id", referencedColumnName = "test_suite_id", nullable = false)
    private TestSuite testSuite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id", referencedColumnName = "test_case_id", nullable = false)
    private TestCase testCase;

    @Column(name = "order_no", nullable = false)
    private Integer orderNo;

}
