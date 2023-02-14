package com.wz.asynctaskproc.service;

import com.wz.asynctaskproc.enums.TaskStatus;
import com.wz.asynctaskproc.model.MatchResult;
import com.wz.asynctaskproc.model.Task;
//import com.wz.asynctaskproc.repository.TasksRepository;
import com.wz.asynctaskproc.repository.TasksRepository;
import com.wz.asynctaskproc.service.status.TaskStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Slf4j
@Service
public class TaskProcessor {
    private final TasksRepository tasksRepository;
    private final TaskStatusService taskProcessStatusService;
    private final Executor taskProcessorExecutor;

    private final TaskCalculator taskCalculator;

    public TaskProcessor(TasksRepository tasksRepository, TaskStatusService taskProcessStatusService, @Qualifier("taskProcessorExecutor") Executor taskProcessorExecutor, TaskCalculator taskCalculator) {
        this.tasksRepository = tasksRepository;
        this.taskProcessStatusService = taskProcessStatusService;
        this.taskProcessorExecutor = taskProcessorExecutor;
        this.taskCalculator = taskCalculator;
    }

    public void launchTask(Task task) {
        taskProcessorExecutor.execute(() -> {
            log.info("Processing task with id: {}", task.getId());

            try {
                MatchResult result = taskCalculator.findBestMatch(task, task.getInput(), task.getPattern());
                log.info("Task {} completed.", task.getId());
                // TODO to debug
                log.info("Task {} position: {} | Typos: {}", task.getId(), result.getPosition(), result.getTypos());
                taskProcessStatusService.storeTaskStatus(task.getId(), TaskStatus.COMPLETED);
                task.setTypos(result.getTypos());
                task.setPosition(result.getPosition());
                task.setStatus(TaskStatus.COMPLETED.toString());
                tasksRepository.save(task);
            } catch (Exception e) {
                log.error("Error processing task with id: {}", task.getId(), e);
                taskProcessStatusService.storeTaskStatus(task.getId(), TaskStatus.ERROR);
                task.setStatus(TaskStatus.ERROR.toString());
            }
        });
    }

}