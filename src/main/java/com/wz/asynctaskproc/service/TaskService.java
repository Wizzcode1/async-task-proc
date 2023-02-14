package com.wz.asynctaskproc.service;

import com.wz.asynctaskproc.model.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    Mono<Object> createTask(TaskRequest task);

    Flux<Task> listTasks();

    Mono<Object> getTaskInfo(String id);

}
