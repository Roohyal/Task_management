package com.mathias.task_it.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categories extends BaseClass{

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @JsonBackReference("person")
    private Person person;
}
