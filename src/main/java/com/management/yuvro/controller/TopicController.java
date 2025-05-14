package com.management.yuvro.controller;

import com.management.yuvro.dto.BatchDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.TopicDTO;
import com.management.yuvro.service.BatchService;
import com.management.yuvro.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics/")
public class TopicController {
    @Autowired
    TopicService topicService;

    @GetMapping("/get-all-topics")
    public ResponseEntity<PageDTO<TopicDTO>> getAllTopics(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(topicService.getAllTopics(page, size));
    }
}
