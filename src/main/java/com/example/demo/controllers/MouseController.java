package com.example.demo.controllers;

import com.example.demo.entities.Mouse;
import com.example.demo.repository.MouseRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mouses")
public class MouseController {

    private final MouseRepository mouseRepository;

    public MouseController(MouseRepository mouseRepository) {
        this.mouseRepository = mouseRepository;
    }

    @GetMapping
    public List<Mouse> getAll() {
        return mouseRepository.findAll();
    }

    @PostMapping
    public Mouse create(@RequestBody Mouse mouse) {
        return mouseRepository.save(mouse);
    }

    @GetMapping("/{id}")
    public Mouse getById(@PathVariable int id) {
        return mouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mouse not found with id: " + id));
    }

    @PutMapping("/{id}")
    public Mouse update(@PathVariable int id, @RequestBody Mouse updatedMouse) {
        Mouse mouse = mouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mouse not found with id: " + id));

        mouse.setName(updatedMouse.getName());
        return mouseRepository.save(mouse);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        mouseRepository.deleteById(id);
    }
}