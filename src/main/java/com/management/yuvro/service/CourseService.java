package com.management.yuvro.service;

import com.management.yuvro.dto.CourseDTO;
import com.management.yuvro.dto.PageDTO;

public interface CourseService {

    PageDTO<CourseDTO> getAllCourses(int page, int size);
}
