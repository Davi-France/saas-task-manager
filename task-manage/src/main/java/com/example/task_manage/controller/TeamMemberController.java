package com.example.task_manage.controller;

import com.example.task_manage.dto.TeamMemberResponse;
import com.example.task_manage.model.Team;
import com.example.task_manage.model.TeamMember;
import com.example.task_manage.model.User;
import com.example.task_manage.service.TeamMemberService;
import com.example.task_manage.service.TeamService;
import com.example.task_manage.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
    @RequestMapping("/team-members")
    public class TeamMemberController {

        private final TeamMemberService teamMemberService;
        private final UserService userService;
        private final TeamService teamService;

        public TeamMemberController(TeamMemberService teamMemberService, UserService userService, TeamService teamService) {
            this.teamMemberService = teamMemberService;
            this.userService = userService;
            this.teamService = teamService;
        }

    @PostMapping
    public TeamMemberResponse addMember(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        Long teamId = Long.valueOf(body.get("teamId").toString());
        String role = body.get("role").toString();

        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Team team = teamService.getTeamById(teamId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));

        TeamMember member = new TeamMember();
        member.setUser(user);
        member.setTeam(team);
        member.setRole(role);

        TeamMember saved = teamMemberService.save(member);
        return TeamMemberResponse.fromEntity(saved);
    }
}
