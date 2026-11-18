
package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // CREATE TASK
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // GET ALL TASKS
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // GET TASK BY ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    // UPDATE TASK
    public Task updateTask(Long id, Task updatedTask) {

        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());

        return taskRepository.save(existingTask);
    }

    // DELETE TASK
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    // GET TASKS BY STATUS
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    // GET ALL TASKS WITH PAGINATION
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    // GET TASKS BY STATUS WITH PAGINATION
    public Page<Task> getTasksByStatus(
            TaskStatus status, Pageable pageable) {

        return taskRepository.findByStatus(status, pageable);
    }

    public Page<Task> searchTasks(
            String keyword, Pageable pageable) {

        return taskRepository.findByTitleContainingIgnoreCase(
                keyword, pageable);
    }

    public Page<Task> searchTasksByTitleAndStatus(
            String keyword,
            TaskStatus status,
            Pageable pageable) {

        return taskRepository.findByTitleContainingIgnoreCaseAndStatus(
                keyword, status, pageable);
    }
}
