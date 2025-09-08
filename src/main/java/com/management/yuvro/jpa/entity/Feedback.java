package com.management.yuvro.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedbackId;
    private String feedbackText;
    private String annotatedText;
    private boolean faculty;
    private boolean student;
    private String lineNo;
    private String createdDateTime;

    @ManyToOne
    @JoinColumn(name = "candidate_task_id")
    private CandidateTask candidateTask;
}

