package com.wz.asynctaskproc.service.status;

import com.wz.asynctaskproc.model.TaskStatusResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskStatusResponseService {

    private final TaskProgressService taskProcessProgressService;
    private final TaskStatusService taskProcessStatusService;

    public TaskStatusResponse getTaskStatus(String id) {
        return new TaskStatusResponse(
                id,
                taskProcessStatusService.retrieveTaskStatus(id),
                taskProcessProgressService.retrieveTaskProgress(id)
        );
    }

}
