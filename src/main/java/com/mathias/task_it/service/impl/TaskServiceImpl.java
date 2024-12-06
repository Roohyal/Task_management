package com.mathias.task_it.service.impl;

import com.mathias.task_it.domain.entities.Categories;
import com.mathias.task_it.domain.entities.Person;
import com.mathias.task_it.domain.entities.Task;
import com.mathias.task_it.domain.enums.Priority;
import com.mathias.task_it.domain.enums.Status;
import com.mathias.task_it.exceptions.NotFoundException;
import com.mathias.task_it.payload.request.TaskRequest;
import com.mathias.task_it.payload.request.TaskUpdateRequest;
import com.mathias.task_it.payload.response.TaskListResponse;
import com.mathias.task_it.payload.response.TaskResponse;
import com.mathias.task_it.payload.response.TaskStatusSummary;
import com.mathias.task_it.repository.CategoryRepository;
import com.mathias.task_it.repository.PersonRepository;
import com.mathias.task_it.repository.TaskRepository;
import com.mathias.task_it.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public TaskResponse createTask(TaskRequest taskRequest, String email) {
        // Fetch the user based on the provided email
        Person person = personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));


        Task task = Task.builder()
                .taskName(cleanInput(taskRequest.getTaskName()))
                .taskDescription(cleanInput(taskRequest.getTaskDescription()))
                .priority(taskRequest.getPriority())
                .status(Status.PENDING)
                .deadline(taskRequest.getDeadline())
                .user(person)
                .build();

        taskRepository.save(task);
        return TaskResponse.builder()
                .responseCode("003")
                .responseMessage("Task created")
                .build();
    }

    @Override
    public String updateTask(TaskUpdateRequest taskRequest, String email, Long id) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));
        Task task = taskRepository.findById(id).orElseThrow(()-> new NotFoundException("Task not found"));

        task.setTaskName(cleanInput(taskRequest.getTaskName()));
        task.setTaskDescription(cleanInput(taskRequest.getTaskDescription()));
        task.setPriority(taskRequest.getPriority());
        task.setDeadline(taskRequest.getDeadline());
        task.setStatus(taskRequest.getStatus());

        taskRepository.save(task);

        return "Task has been updated Successfully";

    }
    @Override
    public List<TaskListResponse> getCurrentUserTasks(String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        List<Task> tasks = taskRepository.findByUserEmail(email);


        // Use the builder pattern for each Task and collect as TaskListResponse
        return tasks.stream()
                .map(task -> TaskListResponse.builder()
                        .id(task.getId())
                        .taskName(task.getTaskName())          // Assuming TaskListResponse has an ID field// Set other relevant fields as needed
                        .taskDescription(task.getTaskDescription())
                        .taskStatus(task.getStatus())
                        .priority(task.getPriority())
                        .deadline(task.getDeadline())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskListResponse> getTasksByCurrentUserAndStatus(String email, Status status) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        List<Task> tasks = taskRepository.findByUserEmailAndStatus(email, status);

        return tasks.stream()
                .map(task -> TaskListResponse.builder()
                        .taskName(task.getTaskName())          // Assuming TaskListResponse has an ID field// Set other relevant fields as needed
                        .taskDescription(task.getTaskDescription())
                        .taskStatus(task.getStatus())
                        .priority(task.getPriority())
                        .deadline(task.getDeadline())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskListResponse> getCompletedTasksForCurrentUser(String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        List<Task> tasks = taskRepository.findByUserEmailAndStatus(email, Status.COMPLETED);

        return tasks.stream()
                .map(task -> TaskListResponse.builder()
                        .taskName(task.getTaskName())
                        .taskDescription(task.getTaskDescription())
                        .taskStatus(task.getStatus())
                        .priority(task.getPriority())
                        .deadline(task.getDeadline())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public Task updateTaskStatus(Long taskId, Status status, String email) {

        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        Task task = taskRepository.findById(taskId).orElseThrow(()-> new NotFoundException("Task not found"));


        task.setStatus(status);

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long taskId, String title, String email) {
        Task task;

        if (taskId != null) {
            task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new NotFoundException("Task not found with id " + taskId));
        } else if (title != null && !title.isEmpty()) {
            task = taskRepository.findByTaskNameIgnoreCase(title)
                    .orElseThrow(() -> new NotFoundException("Task not found with title " + title));
        } else {
            throw new IllegalArgumentException("Both id and title are null");
        }


        return task;
    }

    @Override
    public List<TaskListResponse> getTasksByCurrentUserAndPriority(String email, Priority priority) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        List<Task> tasks = taskRepository.findByUserEmailAndPriority(email, priority);

        return tasks.stream()
                .map(task -> TaskListResponse.builder()
                        .taskName(task.getTaskName())
                        .taskDescription(task.getTaskDescription())
                        .taskStatus(task.getStatus())
                        .priority(task.getPriority())
                        .deadline(task.getDeadline())
                        .build()
                )
                .collect(Collectors.toList());


    }

    @Override
    public List<TaskStatusSummary> getTaskStatusSummary(String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        Long pending = taskRepository.countByStatus(Status.PENDING);
        Long completed = taskRepository.countByStatus(Status.COMPLETED);
        Long inProgress = taskRepository.countByStatus(Status.IN_PROGRESS);

        Long total = pending + completed + inProgress;


        TaskStatusSummary taskStatusSummary = TaskStatusSummary.builder()
                .completed(completed)
                .inProgress(inProgress)
                .pending(pending)
                .totalTasks(total)
                .build();

        return Collections.singletonList(taskStatusSummary);
    }

    @Override
    public void deleteTask(Long taskId, String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        taskRepository.deleteById(taskId);
    }

    @Override
    public Task assignTask(Long taskId, Long categoryId, String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        // Fetch the task by ID
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        //Fetch the category by ID
        Categories category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Assign the new category to the task
        task.setCategory(category);

        // Save and return the updated task
        return taskRepository.save(task);

    }

    // Helper method to trim and remove extra spaces
    private String cleanInput(String input) {
        return input == null ? null : input.trim().replaceAll("\\s+", " ");
    }
}
