package com.management.yuvro.mapper;

import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.TopicDTO;
import com.management.yuvro.dto.TopicPracticeDTO;
import com.management.yuvro.jpa.entity.Topic;
import com.management.yuvro.projection.PracticeCandidatesProjection;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.management.yuvro.constants.Constants.RETRIEVE_SUCCESS;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PracticeMapper {
    List<TopicPracticeDTO> mapToTopicPracticeDTOs(List<PracticeCandidatesProjection> projections);
}

