package com.management.yuvro.service;

import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.TopicDTO;

public interface TopicService {

    PageDTO<TopicDTO> getAllTopics(int page, int size);
}
