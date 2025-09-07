package com.example.demo.repositories;
import com.example.demo.entities.Mouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MouseRepository extends JpaRepository<Mouse, UUID> {
}