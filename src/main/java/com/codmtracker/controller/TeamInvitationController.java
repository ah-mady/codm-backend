package com.codmtracker.controller;

import com.codmtracker.model.TeamInvitation;
import com.codmtracker.security.SecurityUtils;
import com.codmtracker.service.TeamInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TeamInvitationController {

    @Autowired
    private TeamInvitationService teamInvitationService;

    @PostMapping("/teams/{teamId}/invites")
    public TeamInvitation createInvite(@PathVariable Long teamId, @RequestParam String email, @RequestParam String role) {
        String actor = SecurityUtils.getCurrentUserEmail();
        return teamInvitationService.createInvite(teamId, email, role, actor);
    }

    @DeleteMapping("/teams/{teamId}/invites/{inviteId}")
    public ResponseEntity<?> cancelInvite(@PathVariable Long teamId, @PathVariable Long inviteId) {
        String actor = SecurityUtils.getCurrentUserEmail();
        teamInvitationService.cancelInvite(teamId, inviteId, actor);
        return ResponseEntity.ok(Map.of("message", "Invitation cancelled"));
    }

    @GetMapping("/teams/{teamId}/invites")
    public List<TeamInvitation> listInvites(@PathVariable Long teamId) {
        String actor = SecurityUtils.getCurrentUserEmail();
        return teamInvitationService.listPendingInvites(teamId, actor);
    }

    @PostMapping("/invites/accept")
    public ResponseEntity<?> accept(@RequestParam String token) {
        String actor = SecurityUtils.getCurrentUserEmail();
        teamInvitationService.acceptInvite(token, actor);
        return ResponseEntity.ok(Map.of("message", "Invitation accepted"));
    }
}
