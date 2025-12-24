package com.example.uep_planner.upserver.controllers;

import com.example.uep_planner.upserver.dtos.user.LoginRequest;
import com.example.uep_planner.upserver.dtos.user.RegistrationRequest;
import com.example.uep_planner.upserver.helpers.ValidationHelper;
import com.example.uep_planner.upserver.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my_websites/Projects/uep-planner/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ValidationHelper validationHelper;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute RegistrationRequest registrationRequest) {
        this.validationHelper.validate(registrationRequest);
        return this.userService.registerUser(registrationRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@ModelAttribute LoginRequest loginRequest) {
        this.validationHelper.validate(loginRequest);
        return this.userService.loginUser(loginRequest);
    }

}
