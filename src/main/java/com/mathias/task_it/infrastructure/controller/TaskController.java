package com.mathias.task_it.infrastructure.controller;


import com.mathias.task_it.domain.entities.Task;
import com.mathias.task_it.domain.enums.Priority;
import com.mathias.task_it.domain.enums.Status;
import com.mathias.task_it.payload.request.TaskRequest;
import com.mathias.task_it.payload.request.TaskUpdateRequest;
import com.mathias.task_it.payload.response.TaskListResponse;
import com.mathias.task_it.payload.response.TaskResponse;
import com.mathias.task_it.payload.response.TaskStatusSummary;
import com.mathias.task_it.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        TaskResponse taskResponse = taskService.createTask(taskRequest, currentUsername);
        return ResponseEntity.ok(taskResponse);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        taskService.deleteTask(taskId, currentUsername);
        return ResponseEntity.ok("Task Deleted Successfully");
    }

    @PutMapping("/update-task")
    public String updateTask(@RequestBody TaskUpdateRequest taskRequest, @RequestParam Long taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        taskService.updateTask(taskRequest,currentUsername,taskId);
        return "Task has been updated successfully";
    }

    @GetMapping("/get-current-user-tasks")
    public ResponseEntity<?> getCurrentUserTasks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        List<TaskListResponse> taskResponse = taskService.getCurrentUserTasks(currentUsername);
        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping("/filter-by-status")
    public ResponseEntity<?> filterByStatus(@RequestParam Status status){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        List<TaskListResponse> response = taskService.getTasksByCurrentUserAndStatus(currentUsername,status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-completed-tasks")
    public ResponseEntity<?> getCompletedTasks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        List<TaskListResponse> response = taskService.getCompletedTasksForCurrentUser(currentUsername);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestParam Long taskId, @RequestParam Status status){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Task updateTask = taskService.updateTaskStatus(taskId,status,currentUsername);
        return ResponseEntity.ok(updateTask);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTask(@RequestParam(required = false) Long id,
                                        @RequestParam(required = false) String title){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Task task = taskService.getTaskById(id, title, currentUsername);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/filter-by-priority")
    public ResponseEntity<?> filterByPriority(@RequestParam Priority priority){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        List<TaskListResponse> response = taskService.getTasksByCurrentUserAndPriority(currentUsername,priority);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/task-summary")
    public ResponseEntity<?> getTaskSummary(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        List<TaskStatusSummary> response = taskService.getTaskStatusSummary(currentUsername);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/assign-category")
    public ResponseEntity<?> assignCategory(@RequestParam Long taskId, @RequestParam Long categoryId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return ResponseEntity.ok(taskService.assignTask(taskId,categoryId,currentUsername));
    }

}