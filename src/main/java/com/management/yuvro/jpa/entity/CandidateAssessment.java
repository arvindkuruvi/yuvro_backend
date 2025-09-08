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
public class CandidateAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long candidateAssessmentId;
    private String status;
    private String remainingDuration;
    private Long lastAttendedQuestion;
    private Long candidateId;
    private Long score;
    private String totalCorrectAnswers;
    private String totalInCorrectAnswers;
    private String totalNotAttemptedQuestions;
    private LocalDateTime submissionDateTime;

    @OneToMany(mappedBy = "candidateAssessment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AttemptedQuestion> attemptedQuestions;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

}
