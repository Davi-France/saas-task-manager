package com.example.task_manage.controller;

import com.example.task_manage.model.Task;
import com.example.task_manage.model.TeamMember;
import com.example.task_manage.model.User;
import com.example.task_manage.repository.TaskRepository;
import com.example.task_manage.repository.UserRepository;
import com.example.task_manage.service.TaskService;
import com.example.task_manage.service.TeamMemberService;
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
    private final TeamMemberService teamMemberService;

    public TaskController(TaskService taskService, UserService userService, TeamMemberService teamMemberService) {
        this.taskService = taskService;
        this.userService = userService;
        this.teamMemberService = teamMemberService;
    }

    // ------------------- GET TASKS -------------------
    @GetMapping
    public List<Task> getUserTasks(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return taskService.getTasksForUser(user);
    }

    // ----------------- CREATE TASK -------------------
    @PostMapping
    public Task createTask(@RequestBody Task task, Authentication authentication) {
        User creator = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        task.setCreatedBy(creator);

        // satuszin
        if (task.getStatus() == null || task.getStatus().isBlank()) {
            task.setStatus("todo");
        }

        // admin ta liberado criar tarefa a rodo
        if ("ADMIN".equalsIgnoreCase(creator.getRole())) {
            return taskService.createTask(task);
        }

        if (task.getTeam() == null) {
            throw new RuntimeException("A tarefa precisa estar vinculada a um time.");
        }

        // verifica se o criador e do time e pa
        TeamMember creatorMembership = teamMemberService
                .findByUserAndTeam(creator, task.getTeam())
                .orElseThrow(() -> new RuntimeException("Você não faz parte deste time."));

        boolean isManager = "GESTOR".equalsIgnoreCase(creatorMembership.getRole());

        if (isManager) {
            if (task.getAssignee() != null) {
                teamMemberService.findByUserAndTeam(task.getAssignee(), task.getTeam())
                        .orElseThrow(() -> new RuntimeException("O usuário designado não pertence a este time."));
            } else {
                task.setAssignee(creator);
            }
        } else {
            if (task.getAssignee() != null && !task.getAssignee().getId().equals(creator.getId())) {
                throw new RuntimeException("Você só pode criar tarefas para você mesmo.");
            }
            task.setAssignee(creator);
        }

        return taskService.createTask(task);
    }

    // ------------------- UPDATE TASK -------------------
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        boolean isAdmin = "ADMIN".equalsIgnoreCase(user.getRole());
        boolean isOwner = task.getAssignee() != null && task.getAssignee().getId().equals(user.getId());

        boolean isManager = task.getTeam() != null && teamMemberService.findByUserAndTeam(user, task.getTeam())
                .map(member -> "GESTOR".equalsIgnoreCase(member.getRole()))
                .orElse(false);

        if (!isAdmin && !isOwner && !isManager) {
            throw new RuntimeException("Você não tem permissão para editar esta tarefa.");
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());

        return taskService.updateTask(task);
    }

    // - DELETE TASK -------------------
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        boolean isAdmin = "ADMIN".equalsIgnoreCase(user.getRole());
        boolean isOwner = task.getAssignee() != null && task.getAssignee().getId().equals(user.getId());

        boolean isManager = task.getTeam() != null && teamMemberService.findByUserAndTeam(user, task.getTeam())
                .map(member -> "GESTOR".equalsIgnoreCase(member.getRole()))
                .orElse(false);

        if (!isAdmin && !isOwner && !isManager) {
            throw new RuntimeException("Você não tem permissão para deletar esta tarefa.");
        }

        taskService.deleteTask(id);
    }
}
