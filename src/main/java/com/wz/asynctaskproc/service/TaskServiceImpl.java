package com.wz.asynctaskproc.service;

import com.wz.asynctaskproc.enums.TaskStatus;
import com.wz.asynctaskproc.exception.ErrorResponse;
import com.wz.asynctaskproc.model.*;
import com.wz.asynctaskproc.repository.ReactiveTasksRepository;
import com.wz.asynctaskproc.service.status.TaskStatusResponseService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final ReactiveTasksRepository tasksRepository;
    private final TaskProcessor taskProcessor;

    private final TaskStatusResponseService taskStatusResponseService;
    private final TaskMapper mapper;

    @Override
    public Mono<Object> createTask(TaskRequest taskRequest) {

        if (isTaskRequestInvalid(taskRequest)) {
            log.error("Invalid task input or pattern");

            return Mono.just(TaskCreateResponse.builder()
                    .input(taskRequest.getInput())
                    .pattern(taskRequest.getPattern())
                    .success(false)
                    .error("Invalid task input or pattern")
                    .build());
        }

        Task task = getTask(taskRequest, TaskStatus.PENDING);

        return tasksRepository.save(task)
                .doOnSuccess(taskProcessor::launchTask)
                .map(mapper::mapToTaskCreateResponse);
    }

    public Flux<Task> listTasks() {
        return tasksRepository.findAll();
    }

    public Mono<Object> getTaskInfo(String id) {
        TaskStatusResponse taskStatusResponse = taskStatusResponseService.getTaskStatus(id);

        // TODO redis delete unnesery data (not here, just in general)

        if (isStatusInProgress(taskStatusResponse)) {
            return Mono.just(taskStatusResponse);
        }

        return tasksRepository.findById(id)
                .map(mapper::mapToTaskResult)
                .cast(Object.class)
                .switchIfEmpty(Mono.just(new ErrorResponse("Task not found", id)));
    }

    private static boolean isStatusInProgress(TaskStatusResponse taskStatusResponse) {
        return !isNull(taskStatusResponse) && !isNull(taskStatusResponse.getStatus()) &&
                taskStatusResponse.getStatus().equals(TaskStatus.IN_PROGRESS.toString());
    }

    private static boolean isTaskRequestInvalid(TaskRequest taskRequest) {
        return taskRequest == null
                || StringUtils.isBlank(taskRequest.getInput())
                || StringUtils.isBlank(taskRequest.getPattern())
                || inputShorterThanPattern(taskRequest);
    }

    private static boolean inputShorterThanPattern(TaskRequest taskRequest) {
        return taskRequest.getInput().length() < taskRequest.getPattern().length();
    }

    private static Task getTask(TaskRequest taskRequest, TaskStatus error) {
        return Task.builder()
                .input(taskRequest.getInput())
                .pattern(taskRequest.getPattern())
                .status(error.toString())
                .createdDate(Instant.now())
                .build();
    }

}
