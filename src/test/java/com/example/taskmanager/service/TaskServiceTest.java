
package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    // TEST 1: CREATE TASK
    @Test
    void shouldCreateTask() {

        Task task = new Task();
        task.setTitle("Learn Java");
        task.setDescription("Practice Java");
        task.setStatus(TaskStatus.PENDING);

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertEquals("Learn Java", result.getTitle());

        verify(taskRepository).save(task);
    }

    // TEST 2: GET TASK BY ID
    @Test
    void shouldGetTaskById() {

        Task task = new Task();
        task.setTitle("Learn Spring Boot");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertEquals("Learn Spring Boot", result.getTitle());

        verify(taskRepository).findById(1L);
    }

    // TEST 3: TASK NOT FOUND
    @Test
    void shouldThrowExceptionWhenTaskNotFound() {

        when(taskRepository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(100L)
        );
    }

    // TEST 4: UPDATE TASK
    @Test
    void shouldUpdateTask() {

        Task existingTask = new Task();
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");
        existingTask.setStatus(TaskStatus.PENDING);

        Task updatedTask = new Task();
        updatedTask.setTitle("New Title");
        updatedTask.setDescription("New Description");
        updatedTask.setStatus(TaskStatus.COMPLETED);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(existingTask))
                .thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals("New Title", result.getTitle());
        assertEquals(TaskStatus.COMPLETED, result.getStatus());

        verify(taskRepository).save(existingTask);
    }
}
