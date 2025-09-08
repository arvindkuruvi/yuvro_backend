package com.management.yuvro.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long attendenceId;
    private LocalDate attendenceDate;
    private boolean present;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
