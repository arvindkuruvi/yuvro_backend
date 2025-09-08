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
public class CandidateTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long candidateTaskId;
    private String status;
    private LocalDateTime submissionDateTime;
    private LocalDateTime attemptedDateTime;
    @Lob
    private String content;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "candidateTask")
    private List<Feedback> feedbacks;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
