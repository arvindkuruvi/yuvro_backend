package com.management.yuvro.dto;

import java.util.List;

import com.management.yuvro.enums.QuestionType;
import com.management.yuvro.jpa.entity.Topic;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionsDTO {
	private Long questionId;
	private String question;
	private String questionType;
	private String answer;
	private Integer score;
	private List<String> options;
}
