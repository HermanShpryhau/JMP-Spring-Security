package com.epam.jmp.security.maintask.model;

import java.time.LocalDateTime;

public record UserAuthAttempts(int attempts, LocalDateTime blockTimestamp) {
}
