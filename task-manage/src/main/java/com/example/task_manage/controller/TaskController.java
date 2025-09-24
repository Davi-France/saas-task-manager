package com.example.task_manage.controller;

import com.example.task_manage.model.Task;
import com.example.task_manage.model.User;
import com.example.task_manage.repository.TaskRepository;
import com.example.task_manage.repository.UserRepository;
import com.example.task_manage.service.TaskService;
import com.example.task_manage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public List<Task> getUserTasks(Authentication authentication){
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        return taskService.getTaskByUser(user);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, Authentication authentication){
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        task.setUser(user);
        return taskService.createTask(task);
    }

    @PostMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask, Authentication authentication){
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        Task task = taskService.getTaskById(id).orElseThrow();

        if(!task.getUser().getId().equals(user.getId())){
            throw new RuntimeException("voce nao pode editar essa tarefa");
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());

        return taskService.updateTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id, Authentication authentication){
        User user = userService.findByEmail(authentication.getName()).orElseThrow();
        Task task = taskService.getTaskById(id).orElseThrow();

        if(!task.getUser().getId().equals(user.getId())){
            throw  new RuntimeException("Voce nao pode deletar essa tarefa");
        }

        taskService.deleteTask(id);
    }
}
