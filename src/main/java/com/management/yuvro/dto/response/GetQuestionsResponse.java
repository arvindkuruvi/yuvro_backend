package com.management.yuvro.dto.response;

import java.util.List;

import com.management.yuvro.dto.QuestionsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class GetQuestionsResponse extends CommonApiResponse {
	private List<QuestionsDTO> questions;
}
