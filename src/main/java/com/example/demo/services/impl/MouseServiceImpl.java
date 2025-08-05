package com.example.demo.services.impl;

import com.example.demo.entities.Mouse;
import com.example.demo.repository.MouseRepository;
import com.example.demo.services.MouseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MouseServiceImpl implements MouseService {
    private final MouseRepository mouseRepository;

    @Override
    public Mouse save(Mouse mouse) {
        return mouseRepository.save(mouse);
    }

    @Override
    public void deleteById(UUID id) {
        mouseRepository.deleteById(id);
    }

    @Override
    public Mouse getById(UUID id) {
        return mouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mouse not found with id: " + id));
    }

    @Override
    public List<Mouse> getAll() {
        return mouseRepository.findAll();
    }
}