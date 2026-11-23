package com.example.taskmanager.repository;


import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.TaskStatus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;



    @Test
    void shouldSaveAndFindTask() {

        // Create a task
        Task task = new Task();
        task.setTitle("Repository Testing");
        task.setDescription("Testing PostgreSQL repository");
        task.setStatus(TaskStatus.PENDING);

        // Save task
        Task savedTask = taskRepository.save(task);

        // Verify saved task
        assertNotNull(savedTask.getId());

        // Retrieve task
        Task foundTask = taskRepository.findById(savedTask.getId())
                .orElseThrow();

        // Verify retrieved data
        assertEquals("Repository Testing", foundTask.getTitle());
        assertEquals(TaskStatus.PENDING, foundTask.getStatus());
    }

    @Test
    void shouldFindTasksByStatus() {

        Task task = new Task();
        task.setTitle("Learn JPA Testing");
        task.setDescription("Practice repository methods");
        task.setStatus(TaskStatus.PENDING);

        taskRepository.save(task);

        var tasks = taskRepository.findByStatus(TaskStatus.PENDING);

        assertTrue(tasks.stream()
                .anyMatch(t -> t.getTitle().equals("Learn JPA Testing")));
    }
}

