package com.codmtracker.service;

import com.codmtracker.dto.AuthRequestDto;
import com.codmtracker.dto.AuthResponseDto;
import com.codmtracker.exception.CustomException;
import com.codmtracker.model.Role;
import com.codmtracker.model.User;
import com.codmtracker.repository.RoleRepository;
import com.codmtracker.repository.UserRepository;
import com.codmtracker.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EmailService emailService;

    @Override
    public void signup(AuthRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new CustomException("Email already registered", 409);
        String verificationToken = UUID.randomUUID().toString();
        long userCount = userRepository.count();
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role r = new Role();
            r.setName("ADMIN");
            return roleRepository.save(r);
        });
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .emailVerified(false)
                .createdAt(Instant.now())
                .roles(Set.of(adminRole))
                .emailVerificationToken(verificationToken)
                .emailVerificationTokenExpiry(Instant.now().plusSeconds(86400))
                .build();
        userRepository.save(user);
        emailService.sendEmailConfirmation(request.getEmail(), verificationToken);
    }

    @Override
    public AuthResponseDto login(AuthRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("User not found", 404));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
            throw new CustomException("Invalid credentials", 401);
        if (!user.isEmailVerified())
            throw new CustomException("Email not verified", 403);
        java.util.List<String> roles = user.getRoles().stream()
                .map(Role::getName).toList();
        String accessToken = jwtTokenProvider.generateToken(user.getEmail(), roles);
        return AuthResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken("")
                .build();
    }

    @Override
    public void confirmEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new CustomException("Invalid or expired confirmation token", 404));
        if (user.getEmailVerificationTokenExpiry() == null || user.getEmailVerificationTokenExpiry().isBefore(Instant.now()))
            throw new CustomException("Verification token expired", 400);
        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationTokenExpiry(null);
        userRepository.save(user);
    }

    @Override
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null)
            return;
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(Instant.now().plusSeconds(86400));
        userRepository.save(user);
        emailService.sendPasswordResetEmail(email, resetToken);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new CustomException("Invalid or expired reset token", 404));
        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(Instant.now()))
            throw new CustomException("Reset token expired", 400);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}
