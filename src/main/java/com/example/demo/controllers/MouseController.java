package com.example.demo.controllers;

import com.example.demo.entities.Mouse;
import com.example.demo.services.MouseService;
import lombok.RequiredArgsConstructor;
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

    @DeleteMapping
    public void deleteAll() {
        mouseService.deleteAll();
    }
}