package com.codmtracker.controller;

import com.codmtracker.dto.TeamInvitationDto;
import com.codmtracker.service.TeamInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
public class TeamInvitationController {

    @Autowired
    private TeamInvitationService invitationService;

    @PostMapping
    public void sendInvitation(@RequestParam String email, @RequestParam Long teamId, @RequestParam Long invitedById) {
        invitationService.sendInvitation(email, teamId, invitedById);
    }

    @PostMapping("/accept")
    public void acceptInvitation(@RequestParam String token, @RequestParam Long userId) {
        invitationService.acceptInvitation(token, userId);
    }

    @GetMapping("/team/{teamId}/pending")
    public List<TeamInvitationDto> getPendingInvitations(@PathVariable Long teamId) {
        return invitationService.getPendingInvitations(teamId);
    }

    @DeleteMapping("/{invitationId}")
    public void cancelInvitation(@PathVariable Long invitationId, @RequestParam Long teamId) {
        invitationService.cancelInvitation(invitationId, teamId);
    }
}
