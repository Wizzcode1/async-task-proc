package com.wz.asynctaskproc.service.event;


import com.wz.asynctaskproc.service.ProcessedTaskInfoEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskEventEmitter {

    private final ApplicationEventPublisher publisher;

    public void emitEvent(ProcessedTaskInfoEvent task) {
        publisher.publishEvent(task);
    }
}
