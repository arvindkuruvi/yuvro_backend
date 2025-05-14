package com.management.yuvro.service.impl;

import com.management.yuvro.dto.InstitutionDTO;
import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.jpa.repository.InstitutionRepository;
import com.management.yuvro.mapper.InstitutionMapper;
import com.management.yuvro.service.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InstitutionServiceImpl implements InstitutionService {
    @Autowired
    InstitutionRepository institutionRepository;
    @Autowired
    InstitutionMapper institutionMapper;

    @Override
    public PageDTO<InstitutionDTO> getAllInstitutions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("topicId"));
        return institutionMapper.convertPageOfInstitutionToPageDTOOfInstitutionDTO(institutionRepository.findAll(pageable));
    }
}
