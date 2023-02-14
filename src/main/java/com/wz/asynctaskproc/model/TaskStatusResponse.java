package com.wz.asynctaskproc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskStatusResponse {

    private String id;

    private String status;

    private String progress;

}
