package com.management.yuvro.service.impl;

import com.management.yuvro.dto.CourseDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.jpa.repository.CourseRepository;
import com.management.yuvro.mapper.CourseMapper;
import com.management.yuvro.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public PageDTO<CourseDTO> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("courseId"));
        return courseMapper.convertPageOfCourseToPageDTOOfCourseDTO(courseRepository.findAll(pageable));
    }
}
