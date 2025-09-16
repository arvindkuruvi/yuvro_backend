package com.management.yuvro.mapper;

import com.management.yuvro.dto.CandidateDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.SaveCandidateDTO;
import com.management.yuvro.jpa.entity.Candidate;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.management.yuvro.constants.Constants.RETRIEVE_SUCCESS;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CandidateMapper {

    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<CandidateDTO> convertPageOfCandidatesToPageDTOOfCandidateDTO(Page<Candidate> page);

    List<Candidate> convertListOfSaveCandidateDTOToListOfCandidate(List<SaveCandidateDTO> candidates);

    List<CandidateDTO> convertListOfCandidateToListOfCandidateDTO(List<Candidate> candidates);
}

