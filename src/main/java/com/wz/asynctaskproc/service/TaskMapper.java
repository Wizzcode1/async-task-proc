package com.wz.asynctaskproc.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.wz.asynctaskproc.model.Task;
import com.wz.asynctaskproc.model.TaskCreateResponse;
import com.wz.asynctaskproc.model.TaskResult;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "success", constant = "true")
    TaskCreateResponse mapToTaskCreateResponse(Task task);

    TaskResult mapToTaskResult(Task task);

}
