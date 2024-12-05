package com.mathias.task_it.service;

import com.mathias.task_it.domain.entities.Task;
import com.mathias.task_it.domain.enums.Priority;
import com.mathias.task_it.domain.enums.Status;
import com.mathias.task_it.payload.request.TaskRequest;
import com.mathias.task_it.payload.request.TaskUpdateRequest;
import com.mathias.task_it.payload.response.TaskListResponse;
import com.mathias.task_it.payload.response.TaskResponse;
import com.mathias.task_it.payload.response.TaskStatusSummary;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest taskRequest, String email);

    String updateTask(TaskUpdateRequest taskRequest, String email, Long id);

    List<TaskListResponse> getCurrentUserTasks(String email);

    List<TaskListResponse> getTasksByCurrentUserAndStatus(String email, Status status);

    List<TaskListResponse> getCompletedTasksForCurrentUser(String email);

    Task updateTaskStatus(Long taskId, Status status, String email);

    Task getTaskById(Long taskId, String title, String email);

    List<TaskListResponse> getTasksByCurrentUserAndPriority(String email, Priority priority);

    List<TaskStatusSummary> getTaskStatusSummary(String email);

    void deleteTask(Long taskId, String email);

    Task assignTask(Long taskId, Long categoryId, String email);
}
