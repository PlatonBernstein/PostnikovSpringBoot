package com.example.demo.auxillaries;

import lombok.AllArgsConstructor;
import java.util.UUID;

@AllArgsConstructor
public class WrongIdException extends RuntimeException {
    private final UUID mouseId;

    @Override
    public String getMessage() {
        return "Mouse with ID " + mouseId + " is not found";
    }
}
