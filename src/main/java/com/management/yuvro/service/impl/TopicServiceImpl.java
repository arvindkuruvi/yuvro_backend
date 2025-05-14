package com.management.yuvro.service.impl;

import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.TopicDTO;
import com.management.yuvro.jpa.repository.TopicRepository;
import com.management.yuvro.mapper.TopicMapper;
import com.management.yuvro.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicMapper topicMapper;

    @Override
    public PageDTO<TopicDTO> getAllTopics(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("topicId"));
        return topicMapper.convertPageOfTopicToPageDTOOfTopicDTO(topicRepository.findAll(pageable));
    }
}
