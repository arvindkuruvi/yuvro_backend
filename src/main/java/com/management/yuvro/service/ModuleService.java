package com.management.yuvro.service;

import com.management.yuvro.dto.ModuleDTO;
import com.management.yuvro.dto.PageDTO;

public interface ModuleService {
    /**
     * Retrieves a paginated list of all modules.
     *
     * @param page the page number to retrieve
     * @param size the number of items per page
     * @return a PageDTO containing a list of ModuleDTO objects
     */
    PageDTO<ModuleDTO> getAllModules(int page, int size);
}
