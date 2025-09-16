package com.example.demo.services.impl;

import com.example.demo.exceptions.MouseNotFoundException;
import com.example.demo.entities.Mouse;
import com.example.demo.repositories.MouseRepository;
import com.example.demo.services.MouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MouseServiceImpl implements MouseService {
    private final MouseRepository mouseRepository;

    @Override
    public Mouse save(String name) {
        Mouse mouse = new Mouse();
        mouse.setName(name);
        return mouseRepository.save(mouse);
    }

    @Override
    public Mouse update(Mouse mouse) {
        if  (mouse.getId() != null) {
            getById(mouse.getId());
        }
        return mouseRepository.save(mouse);
    }

    @Override
    public void deleteById(UUID id) {
        getById(id);
        mouseRepository.deleteById(id);
    }

    @Override
    public Mouse getById(UUID id) {
        return mouseRepository.findById(id).orElseThrow(() -> new MouseNotFoundException(id));
    }

    @Override
    public List<Mouse> getAll() {
        return mouseRepository.findAll();
    }
}
