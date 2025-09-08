package com.management.yuvro.service;

import com.management.yuvro.dto.FeedbackDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.FeedbackRequest;
import com.management.yuvro.dto.response.CommonApiResponse;

public interface FeedbackService {
    CommonApiResponse createFeedback(FeedbackRequest request);

    PageDTO<FeedbackDTO> getFeedbacks(Long candidateTaskId, int page, int size);
}
