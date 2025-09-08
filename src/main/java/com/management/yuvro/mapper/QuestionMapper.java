package com.management.yuvro.mapper;

import com.management.yuvro.dto.MCQAnswerDTO;
import com.management.yuvro.dto.MCQQuestionDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.QuestionsDTO;
import com.management.yuvro.dto.request.SaveAssessmentQuestionRequest;
import com.management.yuvro.jpa.entity.MCQOptions;
import com.management.yuvro.jpa.entity.MCQQuestion;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.management.yuvro.constants.Constants.RETRIEVE_SUCCESS;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface QuestionMapper {

    @Mapping(target = "content", source = "content", qualifiedByName = "convertListOfMCQQuestionToQuestionsDTO")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<QuestionsDTO> convertPageOfMCQQuestionsToPageDTOOfQuestionsDTO(Page<MCQQuestion> page);

    @Named("convertListOfMCQQuestionToQuestionsDTO")
    List<QuestionsDTO> convertListOfMCQQuestionToQuestionsDTO(List<MCQQuestion> mcqQuestion);

    @Mapping(target = "options", source = "mcqOptions", qualifiedByName = "mapListofOptionsToStringlist")
    QuestionsDTO mapMCQQuestionToQuestionsDTO(MCQQuestion mcqQuestion);

    @Named("mapListofOptionsToStringlist")
    default List<String> mapListofOptionsToStringlist(List<MCQOptions> mcqOptions) {
        return mcqOptions.stream()
                .map(MCQOptions::getOption)
                .toList();
    }

    MCQQuestion mapSaveAssessmentQuestionRequestToQuestion(SaveAssessmentQuestionRequest request);

    List<MCQQuestionDTO> mapListOfMCQQuestionToMCQQuestionDTO(List<MCQQuestion> questions);

    @Mapping(target = "options", source = "mcqOptions", qualifiedByName = "mapListofOptionsToStringlist")
    MCQQuestionDTO mCQQuestionToMCQQuestionDTO(MCQQuestion mCQQuestion);

    @Mapping(target = "options", source = "mcqOptions", qualifiedByName = "mapListofOptionsToStringlist")
    MCQAnswerDTO convertMCQQuestionToMCQAnswerDTO(MCQQuestion actualQuestion);
}