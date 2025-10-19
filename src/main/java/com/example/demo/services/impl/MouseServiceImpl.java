package com.example.demo.services.impl;

import com.example.demo.exceptions.MouseNotFoundException;
import com.example.demo.entities.Mouse;
import com.example.demo.repositories.MouseRepository;
import com.example.demo.services.MouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
    public Mouse update(Mouse mouse) {
        if (mouse.getId() != null) { //точно ли мне нужна эта проверка?
            getById(mouse.getId());
        }
        return mouseRepository.save(mouse);
    }

    @Override
    public void deleteById(UUID id) {
        getById(id);
        mouseRepository.deleteById(id);
    }

    // плохой метод в рамках бизнес-логики
    @Override
    public void deleteAll() {
        mouseRepository.deleteAll();
    }

    @Override
    public Mouse getById(UUID id) {
        return mouseRepository.findById(id).orElseThrow(() -> new MouseNotFoundException(id));
    }

    @Override
    public Page<Mouse> getAll(Pageable pageable) {
        if (!pageable.isPaged()) pageable = PageRequest.of(0, 5);
        return mouseRepository.findAll(pageable);
    }
}
