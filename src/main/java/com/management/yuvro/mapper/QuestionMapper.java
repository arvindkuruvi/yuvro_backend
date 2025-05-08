//package com.management.yuvro.mapper;
//
//import com.management.yuvro.dto.QuestionsDTO;
//import com.management.yuvro.jpa.entity.MCQQuestion;
//import org.mapstruct.Mapper;
//import org.mapstruct.NullValueMappingStrategy;
//import org.mapstruct.NullValuePropertyMappingStrategy;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring",
//        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
//        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
//public interface QuestionMapper {
//    List<QuestionsDTO> ListOfMCQQuestionToQuestionsDTO(List<MCQQuestion> mcqQuestions);
//}
