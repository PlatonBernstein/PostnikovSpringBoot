package com.example.demo.services;

import com.example.demo.entities.Mouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface MouseService {
     Mouse save(Mouse mouse);
     Mouse update(Mouse mouse);
     void deleteById(UUID id);
     void deleteAll();
     Mouse getById(UUID id);
     Page<Mouse> getAll(Pageable pageable, boolean isDeleted);
}
