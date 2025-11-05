package com.example.task_manage.repository;

import com.example.task_manage.model.Task;
import com.example.task_manage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByAssigneeOrCreatedBy(User assignee, User createdBy);

    List<Task> findByCreatedBy(User user);

}
