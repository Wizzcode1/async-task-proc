package com.wz.asynctaskproc.service;

import com.wz.asynctaskproc.enums.TaskStatus;
import com.wz.asynctaskproc.model.*;
import com.wz.asynctaskproc.repository.ReactiveTasksRepository;
import com.wz.asynctaskproc.service.status.TaskStatusResponseService;
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

    @Override
    public Mono<Object> createTask(TaskRequest taskRequest) {

        if (isTaskRequestValid(taskRequest)) {
            log.error("Invalid task input or pattern");

            return Mono.just(TaskCreateResponse.builder()
                    .input(taskRequest.getInput())
                    .pattern(taskRequest.getPattern())
                    .success(false)
                    .error("Invalid task input or pattern")
                    .build());
        }

        Task task = Task.builder()
                .input(taskRequest.getInput())
                .pattern(taskRequest.getPattern())
                .status(TaskStatus.PENDING.toString())
                .createdDate(Instant.now())
                .build();
        return tasksRepository.save(task)
                .doOnSuccess(taskProcessor::launchTask)
                .map(TaskMapper::mapToTaskCreateResponse);
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

        // TODO if empty
        return tasksRepository.findById(id)
                .map(TaskMapper::mapToTaskResult);
    }

    private static boolean isStatusInProgress(TaskStatusResponse taskStatusResponse) {
        return !isNull(taskStatusResponse) && !isNull(taskStatusResponse.getStatus()) &&
                taskStatusResponse.getStatus().equals(TaskStatus.IN_PROGRESS.toString());
    }

    private static boolean isTaskRequestValid(TaskRequest taskRequest) {
        return taskRequest == null || taskRequest.getInput() == null || taskRequest.getPattern() == null
                || taskRequest.getInput().isEmpty() || taskRequest.getPattern().isEmpty();
    }

}
