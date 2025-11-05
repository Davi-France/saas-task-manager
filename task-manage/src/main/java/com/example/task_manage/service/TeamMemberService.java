package com.example.task_manage.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.task_manage.model.Team;
import com.example.task_manage.model.TeamMember;
import com.example.task_manage.model.User;
import com.example.task_manage.repository.TeamMemberRepository;

@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public TeamMember save(TeamMember member) {
        return teamMemberRepository.save(member);
    }

    public Optional<TeamMember> findByUserAndTeam(User user, Team team) {
        return teamMemberRepository.findByUserAndTeam(user, team);
    }
}