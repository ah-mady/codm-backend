package com.codmtracker.controller;

import com.codmtracker.dto.AuthRequestDto;
import com.codmtracker.dto.AuthResponseDto;
import com.codmtracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public AuthResponseDto signup(@RequestBody AuthRequestDto request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        return authService.login(request);
    }

    @PostMapping("/email-confirmation")
    public void sendConfirmation(@RequestParam String email) {
        authService.sendEmailConfirmation(email);
    }

    @GetMapping("/confirm")
    public void confirm(@RequestParam String token) {
        authService.confirmEmail(token);
    }

    @PostMapping("/password-reset")
    public void sendReset(@RequestParam String email) {
        authService.sendPasswordReset(email);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestParam String token, @RequestParam String password) {
        authService.resetPassword(token, password);
    }
}
