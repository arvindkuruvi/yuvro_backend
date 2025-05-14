package com.management.yuvro.controller;

import com.management.yuvro.dto.CourseDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses/")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/get-all-courses")
    public ResponseEntity<PageDTO<CourseDTO>> getCourses(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(courseService.getAllCourses(page, size));
    }
}
