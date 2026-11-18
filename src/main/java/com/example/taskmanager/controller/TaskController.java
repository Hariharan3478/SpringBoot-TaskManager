
package com.example.taskmanager.controller;

import com.example.taskmanager.dto.CreateTaskRequest;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // CREATE TASK
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(
            @Valid @RequestBody CreateTaskRequest request) {

        Task task = new Task();

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());

        Task savedTask = taskService.createTask(task);

        return convertToResponse(savedTask);
    }

    //get all task

    @GetMapping
    public Page<TaskResponse> getAllTasks(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Task> tasks;

        if (search != null && !search.isBlank()
                && status != null) {

            tasks = taskService.searchTasksByTitleAndStatus(
                    search, status, pageable);

        } else if (search != null && !search.isBlank()) {

            tasks = taskService.searchTasks(search, pageable);

        } else if (status != null) {

            tasks = taskService.getTasksByStatus(status, pageable);

        } else {

            tasks = taskService.getAllTasks(pageable);
        }

        return tasks.map(this::convertToResponse);
    }



    // GET TASK BY ID
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {

        Task task = taskService.getTaskById(id);

        return convertToResponse(task);
    }

    // UPDATE TASK
    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request) {

        Task task = new Task();

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());

        Task updatedTask = taskService.updateTask(id, task);

        return convertToResponse(updatedTask);
    }

    // DELETE TASK
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);
    }

    // CONVERT ENTITY TO RESPONSE DTO
    private TaskResponse convertToResponse(Task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }
}
