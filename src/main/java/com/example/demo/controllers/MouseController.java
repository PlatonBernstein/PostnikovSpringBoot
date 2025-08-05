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
    public Mouse create(@RequestBody Mouse mouse) {
        return mouseService.save(mouse);
    }

    @PutMapping("/{id}")
    public Mouse update(@PathVariable UUID id, @RequestBody Mouse updatedMouse) {
        Mouse mouse = mouseService.getById(id);
        mouse.setName(updatedMouse.getName());
        return mouseService.save(mouse);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        mouseService.deleteById(id);
    }
}