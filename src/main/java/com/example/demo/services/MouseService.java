package com.example.demo.services;

import com.example.demo.entities.Mouse;
import java.util.List;
import java.util.UUID;

public interface MouseService {
     Mouse save(String name);
     Mouse update(Mouse mouse);
     void deleteById(UUID id);
     Mouse getById(UUID id);
     List<Mouse> getAll();
}
