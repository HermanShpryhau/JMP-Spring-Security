package com.epam.jmp.security.maintask.controller;

import com.epam.jmp.security.maintask.model.User;
import com.epam.jmp.security.maintask.model.UserAuthAttempts;
import com.epam.jmp.security.maintask.service.LoginAttemptService;
import com.epam.jmp.security.maintask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SecurityController {
    @Autowired
    private UserService userService;

    record EndpointDto(String endpoint, String securityPolicy){}

    @GetMapping("/info")
    public EndpointDto info() {
        return new EndpointDto("info", "secured");
    }

    @GetMapping("/about")
    public EndpointDto about() {
        return new EndpointDto("about", "public");
    }

    @GetMapping("/admin")
    public EndpointDto admin() {
        return new EndpointDto("admin", "only admin");
    }

    @GetMapping("/blocked")
    public List<User> blockedUserList() {
        return userService.findBlockedUsers();
    }
}
