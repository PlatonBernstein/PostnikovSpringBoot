package com.example.demo.exceptions;

import lombok.AllArgsConstructor;
import java.util.UUID;

@AllArgsConstructor
public class MouseNotFoundException extends RuntimeException {
    private final UUID mouseId;

    @Override
    public String getMessage() {
        return "Mouse with ID " + mouseId + " is not found";
    }
}
