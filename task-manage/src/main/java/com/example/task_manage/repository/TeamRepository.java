package com.example.task_manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task_manage.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
