package com.example.demo.controllers;

import com.example.demo.exceptions.MouseNotFoundException;
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
    public Mouse create(@RequestBody String name) {
        return mouseService.save(name);
    }

    @PutMapping("/{id}")
    public Mouse update(@RequestBody Mouse updatedMouse) {
        return mouseService.update(updatedMouse);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        mouseService.deleteById(id);
    }

    /*
    @ExceptionHandler(MouseNotFoundException.class)
    public ResponseEntity<String> handleMouseNotFound(MouseNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    */
}