package com.wz.asynctaskproc.service;

import com.wz.asynctaskproc.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProcessedTaskInfoEvent {

    private String taskId;

    private int progress;

    private TaskStatus status;

}
