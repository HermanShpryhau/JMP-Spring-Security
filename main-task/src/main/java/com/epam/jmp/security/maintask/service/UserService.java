package com.epam.jmp.security.maintask.service;

import com.epam.jmp.security.maintask.model.User;
import com.epam.jmp.security.maintask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if (loginAttemptService.isBlocked(username)) {
            throw new LockedException(String.format("User %s is blocked.", username));
        }

        String[] authorities = user.getAuthorities().split(";");

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    public List<User> findBlockedUsers() {
        return userRepository.findAll().stream()
                .filter(user -> loginAttemptService.isBlocked(user.getUsername()))
                .collect(Collectors.toList());
    }
}
