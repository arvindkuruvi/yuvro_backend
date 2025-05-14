package com.management.yuvro.mapper;

import com.management.yuvro.dto.CourseDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.jpa.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import static com.management.yuvro.constants.Constants.RETRIEVE_SUCCESS;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CourseMapper {
    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<CourseDTO> convertPageOfCourseToPageDTOOfCourseDTO(Page<Course> page);
}