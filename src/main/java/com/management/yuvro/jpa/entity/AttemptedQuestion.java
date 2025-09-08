package com.management.yuvro.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttemptedQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long attemptedQuestionId;
    private Long questionId;
    private String answer;
    private Integer score;
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "candidate_assessment_id")
    private CandidateAssessment candidateAssessment;
}
