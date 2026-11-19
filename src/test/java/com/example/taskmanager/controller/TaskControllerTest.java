
package com.example.taskmanager.controller;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;
import com.example.taskmanager.service.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldGetAllTasks() throws Exception {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Java");
        task.setDescription("Practice Java");
        task.setStatus(TaskStatus.COMPLETED);

        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(taskService.getAllTasks(any(Pageable.class)))
                .thenReturn(taskPage);

        mockMvc.perform(get("/api/tasks")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title")
                        .value("Learn Java"))
                .andExpect(jsonPath("$.content[0].status")
                        .value("COMPLETED"));
    }


    @Test
    void shouldSearchTasksByTitle() throws Exception {

        Task task = new Task();
        task.setId(2L);
        task.setTitle("Learn Java");
        task.setDescription("Practice Java");
        task.setStatus(TaskStatus.COMPLETED);

        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(taskService.searchTasks(
                org.mockito.ArgumentMatchers.eq("Java"),
                any(Pageable.class)
        )).thenReturn(taskPage);

        mockMvc.perform(get("/api/tasks")
                        .param("search", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title")
                        .value("Learn Java"));
    }


    @Test
    void shouldFilterTasksByStatus() throws Exception {

        Task task = new Task();
        task.setId(3L);
        task.setTitle("Learn Spring Boot");
        task.setDescription("Practice REST API");
        task.setStatus(TaskStatus.PENDING);

        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(taskService.getTasksByStatus(
                org.mockito.ArgumentMatchers.eq(TaskStatus.PENDING),
                any(Pageable.class)
        )).thenReturn(taskPage);

        mockMvc.perform(get("/api/tasks")
                        .param("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status")
                        .value("PENDING"));
    }


}

