package com.management.yuvro.service.impl;

import com.management.yuvro.dto.ModuleDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.jpa.repository.ModuleRepository;
import com.management.yuvro.mapper.ModuleMapper;
import com.management.yuvro.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ModuleMapper moduleMapper;

    @Override
    public PageDTO<ModuleDTO> getAllModules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("moduleId"));
        return moduleMapper.convertPageOfModuleToPageDTOOfModuleDTO(moduleRepository.findAll(pageable));
    }
}
