package com.example.demo.controllers;

import com.example.demo.entities.Mouse;
import com.example.demo.services.MouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

// Заебашить енум с константами
@RestController
@RequiredArgsConstructor
@RequestMapping("/mouses")
public class MouseController {
    private final MouseService mouseService;

    @GetMapping
    public Page<Mouse> getAll(Pageable pageable) {
        return mouseService.getAll(pageable);
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