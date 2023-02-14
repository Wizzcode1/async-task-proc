package com.wz.asynctaskproc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskCreateResponse {

    private String id;

    private String input;

    private String pattern;

    private boolean success;

    private String error;

}
