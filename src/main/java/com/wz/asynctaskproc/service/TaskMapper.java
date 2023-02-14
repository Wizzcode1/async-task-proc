package com.wz.asynctaskproc.service;

import com.wz.asynctaskproc.model.Task;
import com.wz.asynctaskproc.model.TaskCreateResponse;
import com.wz.asynctaskproc.model.TaskResult;

public class TaskMapper {

    public static TaskCreateResponse mapToTaskCreateResponse(Task task) {
        return TaskCreateResponse.builder()
                .id(task.getId())
                .input(task.getInput())
                .pattern(task.getPattern())
                .success(true)
                .build();
    }

    public static TaskResult mapToTaskResult(Task task) {
        return TaskResult.builder()
                .id(task.getId())
                .input(task.getInput())
                .pattern(task.getPattern())
                .status(task.getStatus())
                .position(task.getPosition())
                .typos(task.getTypos())
                .build();
    }

}
