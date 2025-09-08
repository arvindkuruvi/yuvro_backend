package com.management.yuvro.jpa.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Practice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long practiceId;
	private Long topicId;
	private String status;
	private boolean attempted;
	private boolean completed;
	private LocalDateTime attemptedDateTime;
	private LocalDateTime submittedDateTime;

	@ManyToOne
	@JoinColumn(name = "candidate_id")
	private Candidate candidate;

	@OneToMany(mappedBy = "practice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PracticeQuestion> practiceQuestions;
}
