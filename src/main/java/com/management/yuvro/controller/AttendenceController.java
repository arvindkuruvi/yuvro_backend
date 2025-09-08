package com.management.yuvro.controller;

import com.management.yuvro.dto.AttendenceDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.request.CreateAttendenceRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.service.AttendenceService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendence/")
public class AttendenceController {
    @Autowired
    AttendenceService attendenceService;

    @GetMapping("/get-attendence")
    public ResponseEntity<PageDTO<AttendenceDTO>> getAttendenceByBatchIdAndDate(
            @Schema(example = "2025-05-18") @RequestParam(name = "attendenceDate") String attendenceDate,
            @RequestParam(name = "batchId") Long batchId, @RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(attendenceService.getAttendence(batchId, attendenceDate, page, size));
    }

    @PostMapping("/save-attendence")
    public ResponseEntity<CommonApiResponse> saveAttendence(@RequestBody CreateAttendenceRequest request) {
        return ResponseEntity.ok().body(attendenceService.createAttendence(request));
    }


}
