package com.management.yuvro.controller;

import com.management.yuvro.dto.BatchDTO;
import com.management.yuvro.dto.ModuleDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.service.BatchService;
import com.management.yuvro.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modules/")
public class ModuleController {
    @Autowired
    ModuleService moduleService;

    @GetMapping("/get-all-modules")
    public ResponseEntity<PageDTO<ModuleDTO>> getAllModules(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(defaultValue = "10", name = "size") int size) {
        return ResponseEntity.ok().body(moduleService.getAllModules(page, size));
    }
}
