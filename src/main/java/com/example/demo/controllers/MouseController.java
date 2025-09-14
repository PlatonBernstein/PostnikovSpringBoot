package com.example.demo.controllers;

import com.example.demo.auxillaries.WrongIdException;
import com.example.demo.entities.Mouse;
import com.example.demo.services.MouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mouses")
public class MouseController {
    private final MouseService mouseService;

    @GetMapping
    public List<Mouse> getAll() {
        return mouseService.getAll();
    }

    @GetMapping("/{id}")
    public Mouse getById(@PathVariable UUID id) {
        return mouseService.getById(id);
    }

    @PostMapping
    public Mouse create(@RequestBody Mouse mouse) {
        return mouseService.save(mouse);
    }

    @PutMapping("/{id}")
    public Mouse update(@RequestBody Mouse updatedMouse) {
        return mouseService.save(updatedMouse);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        mouseService.deleteById(id);
    }

    @ExceptionHandler(WrongIdException.class)
    public ResponseEntity<String> handleIdNotFound(WrongIdException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}