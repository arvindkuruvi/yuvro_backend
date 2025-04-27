package com.management.yuvro.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class MCQOptions {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long optionId;
	private String option;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
}
