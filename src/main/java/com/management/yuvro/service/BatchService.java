package com.management.yuvro.service;

import com.management.yuvro.dto.BatchDTO;
import com.management.yuvro.dto.CourseDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.AddCandidatesToBatchRequest;
import com.management.yuvro.dto.request.AddCoursesToBatchRequest;
import com.management.yuvro.dto.request.AddInstitutionsToBatchRequest;
import com.management.yuvro.dto.response.CommonApiResponse;

public interface BatchService {

    PageDTO<BatchDTO> getAllCourses(int page, int size);

    CommonApiResponse addCoursesToBatch(AddCoursesToBatchRequest addCoursesToBatchRequest);

    CommonApiResponse addCandidatesToBatch(AddCandidatesToBatchRequest request);

    CommonApiResponse addInstitutionsToBatch(AddInstitutionsToBatchRequest request);
}
