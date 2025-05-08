package com.management.yuvro.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticeQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long practiceQuestionId;
	
	private Long questionId;
	private String selectedAns;
	private boolean correct;

	@ManyToOne
	@JoinColumn(name = "practice_id")
	private Practice practice;

}