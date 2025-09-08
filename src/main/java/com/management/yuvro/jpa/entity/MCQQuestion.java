package com.management.yuvro.jpa.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.management.yuvro.enums.QuestionType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCQQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long questionId;
	private String question;
	private String questionType;
	private String description;
	private String answer;
	private Integer score;

	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;

	@ManyToOne
	@JoinColumn(name = "assessment_id")
	private Assessment assessment;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "question")
	private List<MCQOptions> mcqOptions;
}
