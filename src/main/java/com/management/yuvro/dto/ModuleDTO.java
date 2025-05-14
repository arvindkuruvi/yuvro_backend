package com.management.yuvro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDTO {
    private Long moduleId;
    private String moduleName;
    private String description;
    private boolean isModuleLocked;
}
