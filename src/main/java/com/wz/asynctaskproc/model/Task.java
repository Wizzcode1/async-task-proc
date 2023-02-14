package com.wz.asynctaskproc.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    @NotEmpty
    private String input;

    @NotEmpty
    private String pattern;

    private String status;

    private Integer position;

    private Integer typos;

    @CreatedDate
    private Instant createdDate;

}
