package com.management.yuvro.controller;

import com.management.yuvro.dto.BatchDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.AddCandidatesToBatchRequest;
import com.management.yuvro.dto.request.AddCoursesToBatchRequest;
import com.management.yuvro.dto.request.AddInstitutionsToBatchRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batches/")
public class BatchController {
    @Autowired
    BatchService batchService;

    @GetMapping("/get-all-batches")
    public ResponseEntity<PageDTO<BatchDTO>> getAllBatches(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(batchService.getAllCourses(page, size));
    }

    @PostMapping("/add-courses-to-batch")
    public ResponseEntity<CommonApiResponse> addCoursesToBatch(@RequestBody AddCoursesToBatchRequest request) {
        return ResponseEntity.ok().body(batchService.addCoursesToBatch(request));
    }

    @PostMapping("/add-candidates-to-batch")
    public ResponseEntity<CommonApiResponse> addCandidatesToBatch(@RequestBody AddCandidatesToBatchRequest request) {
        return ResponseEntity.ok().body(batchService.addCandidatesToBatch(request));
    }

    @PostMapping("/add-institutions-to-batch")
    public ResponseEntity<CommonApiResponse> addInstitutionsToBatch(@RequestBody AddInstitutionsToBatchRequest request) {
        return ResponseEntity.ok().body(batchService.addInstitutionsToBatch(request));
    }
}
