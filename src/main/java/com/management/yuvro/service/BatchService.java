package com.management.yuvro.service;

import com.management.yuvro.dto.BatchDTO;
import com.management.yuvro.dto.CourseDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.*;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.CreateBatchResponse;
import com.management.yuvro.jpa.entity.Batch;

import java.util.List;

public interface BatchService {

    PageDTO<BatchDTO> getAllCourses(int page, int size);

    Batch findBatchById(Long id);

    CommonApiResponse addCoursesToBatch(AddCoursesToBatchRequest addCoursesToBatchRequest);

    CommonApiResponse addCandidatesToBatch(AddCandidatesToBatchRequest request);

    CommonApiResponse addInstitutionsToBatch(AddInstitutionsToBatchRequest request);

    CreateBatchResponse createBatch(CreateBatchRequest request);

    PageDTO<CourseDTO> getCoursesByBatchId(Long batchId, int page, int size);

    CommonApiResponse deleteBatches(List<Long> batchIds);

    CommonApiResponse editBatch(EditBatchRequest request);
}
