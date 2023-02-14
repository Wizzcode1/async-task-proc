package com.wz.asynctaskproc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResult {

    private String id;

    private String input;

    private String pattern;

    private String status;

    private Integer position;

    private Integer typos;

    private String error;
}
