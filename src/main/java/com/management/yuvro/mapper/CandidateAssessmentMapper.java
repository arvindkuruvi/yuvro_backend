package com.management.yuvro.mapper;

import com.management.yuvro.dto.AssessmentCandidatesDTO;
import com.management.yuvro.dto.CandidateAssessmentDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.projection.AssessmentCandidateProjection;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.management.yuvro.constants.Constants.RETRIEVE_SUCCESS;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CandidateAssessmentMapper {

    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<CandidateAssessmentDTO> mapAssessmentCandidateProjectionPageToCandidateAssessmentDTOPage(Page<AssessmentCandidateProjection> page);

    @Mapping(target = "submissionDateTime", expression = "java(projection.getSubmissionDateTime() != null ? projection.getSubmissionDateTime().toLocalDate().toString() : \"NA\")")
    AssessmentCandidatesDTO assessmentCandidateProjectionToAssessmentCandidatesDTO(AssessmentCandidateProjection projection);

    List<AssessmentCandidatesDTO> mapAssessmentCandidateProjectionListToAssessmentCandidatesDTOList(List<AssessmentCandidateProjection> assessmentCandidates);
}
