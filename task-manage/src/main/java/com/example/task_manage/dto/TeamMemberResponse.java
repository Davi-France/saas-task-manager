package com.example.task_manage.dto;

import com.example.task_manage.model.TeamMember;

public record TeamMemberResponse(
        Long id,
        String role,
        String userEmail,
        String userName,
        String teamName
) {
    public static TeamMemberResponse fromEntity(TeamMember member) {
        return new TeamMemberResponse(
                member.getUser().getId(),
                member.getRole(),
                member.getUser().getEmail(),
                member.getUser().getUsername(),
                member.getTeam().getName()
        );
    }
}
