package com.management.yuvro.mapper;

import com.management.yuvro.dto.AssessmentDTO;
import com.management.yuvro.dto.GetAssessmentDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.SaveAssessmentRequest;
import com.management.yuvro.jpa.entity.Assessment;
import com.management.yuvro.projection.GetAssessmentProjection;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import static com.management.yuvro.constants.Constants.RETRIEVE_SUCCESS;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface AssessmentMapper {

    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<AssessmentDTO> mapPageOfAssessmentToPageDTOOfAssessmentDTO(Page<Assessment> page);


    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<GetAssessmentDTO> mapPageOfGetAssessmentProjectionToPageDTOOfAssessmentDTO(Page<GetAssessmentProjection> page);

    @Mapping(target = "createdDateTime", expression = "java(java.time.LocalDateTime.now())")
    Assessment mapSaveAssessmentRequestToAssessment(SaveAssessmentRequest request);

    AssessmentDTO mapAssessmentToAssessmentDTO(Assessment assessment);
}

