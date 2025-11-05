package com.example.task_manage.service;

import com.example.task_manage.model.Task;
import com.example.task_manage.model.User;
import com.example.task_manage.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksForUser(User user) {
        String role = user.getRole();

        if ("ADMIN".equalsIgnoreCase(role)) {
            return taskRepository.findAll();
        }

        return taskRepository.findByAssigneeOrCreatedBy(user, user);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
