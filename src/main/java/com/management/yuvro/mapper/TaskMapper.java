package com.management.yuvro.mapper;

import com.management.yuvro.dto.*;
import com.management.yuvro.dto.request.CreateTaskRequest;
import com.management.yuvro.dto.response.CandidateTaskAnsResponse;
import com.management.yuvro.jpa.entity.CandidateTask;
import com.management.yuvro.jpa.entity.Task;
import com.management.yuvro.projection.GetCandidateTasksProjection;
import com.management.yuvro.projection.ViewTaskCandidatesProjection;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.management.yuvro.constants.Constants.RETRIEVE_SUCCESS;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskMapper {

    Task mapCreateTaskRequestToTask(CreateTaskRequest request);


    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<TaskDTO> mapPageOfTasksToPageOfTaskDTO(Page<Task> page);


    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<GetCandidateTasksDTO> mapPageOfGetCandidateTasksProjectionToPageOfGetCandidateTasksDTO(Page<GetCandidateTasksProjection> page);


    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = "page.pageable.pageNumber")
    @Mapping(target = "size", source = "page.pageable.pageSize")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "message", constant = RETRIEVE_SUCCESS)
    @Mapping(target = "success", constant = "true")
    PageDTO<ViewTaskCandidatesDTO> mapPageOfViewTaskCandidatesProjectionToPageOfViewTaskCandidatesDTO(Page<ViewTaskCandidatesProjection> page);

    CandidateTaskAnsResponse mapCandidateTaskToCandidateTaskAnsResponse(CandidateTask candidateTask);

    @Mapping(target = "submittedDateTime", expression = "java(projection.getSubmissionDateTime() != null ? projection.getSubmissionDateTime().toLocalDate().toString() : \"NA\")")
    @Mapping(target = "createdDateTime", expression = "java(projection.getCreatedDateTime() != null ? projection.getCreatedDateTime().toLocalDate().toString() : \"NA\")")
    TaskCandidatesDTO viewTaskCandidatesProjectionToTaskCandidatesDTO(ViewTaskCandidatesProjection projection);

    List<TaskCandidatesDTO> mapListOfViewTaskCandidatesProjectionToListOfTaskCandidatesDTO(List<ViewTaskCandidatesProjection> taskCandidatesProjections);
}

