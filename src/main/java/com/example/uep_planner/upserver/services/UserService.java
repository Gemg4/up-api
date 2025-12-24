package com.example.uep_planner.upserver.services;

import com.example.uep_planner.upserver.configs.SecurityConfig;
import com.example.uep_planner.upserver.dtos.other.ResponseToken;
import com.example.uep_planner.upserver.dtos.user.LoginRequest;
import com.example.uep_planner.upserver.dtos.user.RegistrationRequest;
import com.example.uep_planner.upserver.entities.User;
import com.example.uep_planner.upserver.exceptions.ApiAssert;
import com.example.uep_planner.upserver.http.ResponseBuilder;
import com.example.uep_planner.upserver.mappers.UserMapper;
import com.example.uep_planner.upserver.repositories.UserJpaRepository;
import com.example.uep_planner.upserver.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    private final UserJpaRepository userRepo;
    private final UserMapper userMapper;
    private final SecurityConfig securityConfig;
    private final JwtUtils jwtUtils;

    public ResponseEntity<?> registerUser(RegistrationRequest registrationRequest) {
        var isEmail = this.userRepo.findByEmail(registrationRequest.getEmail()).orElse(null);
        ApiAssert.unAuthorizedIf(isEmail != null, "Email is already registered");
        var passEncoder = securityConfig.passwordEncoder();
        var user = this.userMapper.toEntity(registrationRequest);
        user.setPassword(passEncoder.encode(user.getPassword()));
        this.userRepo.save(user);
        return ResponseBuilder.created("User registered successful", null);
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        var user = this.userRepo.findByEmail(loginRequest.getEmail()).orElse(null);
        ApiAssert.notFoundIf(user == null, "Email is not registered");
        ApiAssert.unAuthorizedIf(!securityConfig.passwordEncoder().matches(loginRequest.getPassword(), user.getPassword()), "Invalid email or password");
        var token = this.jwtUtils.generateToken(user.getId());
        return ResponseBuilder.success("Login successful",new ResponseToken(token));
    }


    public User getUserById(long id) {
        User user = this.userRepo.findById(id).orElse(null);
        ApiAssert.notFoundIf(user == null, "User not found");
        return user;
    }
}
