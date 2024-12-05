package com.mathias.task_it.payload.response;

import com.mathias.task_it.domain.enums.Priority;
import com.mathias.task_it.domain.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskListResponse {
    private String taskName;

    private String taskDescription;

    private LocalDate deadline;

    private Status taskStatus;

    private Priority priority;

}
