package com.wz.asynctaskproc.service.event;

import com.wz.asynctaskproc.service.ProcessedTaskInfoEvent;
import com.wz.asynctaskproc.service.status.TaskProgressService;
import com.wz.asynctaskproc.service.status.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskEventListener {

    private final TaskProgressService taskProcessProgressService;
    private final TaskStatusService taskProcessStatusService;

    @Async
    @EventListener
    public void handleTaskEvent(ProcessedTaskInfoEvent taskEvent) {
        taskProcessProgressService.storeTaskProgress(taskEvent.getTaskId(), taskEvent.getProgress());
        taskProcessStatusService.storeTaskStatus(taskEvent.getTaskId(), taskEvent.getStatus());
    }

}
