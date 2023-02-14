package com.wz.asynctaskproc.controller;

import com.wz.asynctaskproc.model.Task;
import com.wz.asynctaskproc.model.TaskRequest;
import com.wz.asynctaskproc.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public Mono<Object> createTask(@RequestBody TaskRequest task) {
        return taskService.createTask(task);
    }

    @GetMapping("/list")
    public Flux<Task> listTasks() {
        return taskService.listTasks();
    }

    @GetMapping("/{id}")
    public Mono<Object> getTaskInfo(@PathVariable("id") String id) {
        return taskService.getTaskInfo(id);
    }

}