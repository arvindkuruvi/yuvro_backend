package com.management.yuvro.service;

import com.management.yuvro.dto.AttendenceDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.CreateAttendenceRequest;
import com.management.yuvro.dto.response.CommonApiResponse;

public interface AttendenceService {
    CommonApiResponse createAttendence(CreateAttendenceRequest request);

    PageDTO<AttendenceDTO> getAttendence(Long batchId, String attendenceDate, int page, int size);
}