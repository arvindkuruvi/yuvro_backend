package com.management.yuvro.service;

import com.management.yuvro.dto.InstitutionDTO;
import com.management.yuvro.dto.PageDTO;

public interface InstitutionService {

    PageDTO<InstitutionDTO> getAllInstitutions(int page, int size);
}
