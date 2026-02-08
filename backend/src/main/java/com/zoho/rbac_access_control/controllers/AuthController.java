package com.zoho.rbac_access_control.controllers;

import com.zoho.rbac_access_control.dto.LoginRequest;
import com.zoho.rbac_access_control.dto.LoginResponse;
import com.zoho.rbac_access_control.dto.RegisterRequest;
import com.zoho.rbac_access_control.entities.User;
import com.zoho.rbac_access_control.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody RegisterRequest request){
        return authService.register(request.getUsername(), request.getPassword());
    }

    @PostMapping("/login")
    public LoginResponse login(LoginRequest request){
        User user = authService.login(request.getUsername(), request.getPassword());
        return new LoginResponse("Login successful", user.getUsername());
    }
}
