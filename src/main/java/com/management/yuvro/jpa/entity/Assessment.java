package com.management.yuvro.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long assessmentId;
    private String assessmentName;
    private String assessmentType;
    private String description;
    private String subject;
    private String topic;
    private LocalDateTime createdDateTime;
    private LocalDateTime startDateTime;
    private LocalDateTime dueDateTime;
    private String durationInHours;
    private boolean sent;
    private Long totalQuestions;
    private String createdBy;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "assessment")
    private List<MCQQuestion> questions;

    @OneToMany(mappedBy = "assessment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CandidateAssessment> candidateAssessments;
}
