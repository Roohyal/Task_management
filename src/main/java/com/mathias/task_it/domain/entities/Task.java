package com.mathias.task_it.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mathias.task_it.domain.enums.Priority;
import com.mathias.task_it.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Task extends BaseClass{

    private String taskName;

    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;


    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @JsonBackReference("user")
    private Person user;

}