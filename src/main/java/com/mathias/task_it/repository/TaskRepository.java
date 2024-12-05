package com.mathias.task_it.repository;

import com.mathias.task_it.domain.entities.Task;
import com.mathias.task_it.domain.enums.Priority;
import com.mathias.task_it.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTaskNameIgnoreCase(String taskName);

    List<Task> findByUserEmail(String email);

    List<Task> findByUserEmailAndStatus(String email, Status status);

    List<Task> findByUserEmailAndPriority(String email, Priority priority);

    Long countByStatus(Status status);
}
