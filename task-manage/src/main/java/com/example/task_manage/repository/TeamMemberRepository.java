package com.example.task_manage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task_manage.model.Team;
import com.example.task_manage.model.TeamMember;
import com.example.task_manage.model.User;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findByTeam(Team team);

    List<TeamMember> findByUser(User user);

    Optional<TeamMember> findByUserAndTeam(User user, Team team);
}