package com.example.demo.services.impl;

import com.example.demo.config.PostgresTestConfig;
import com.example.demo.controllers.MouseController;
import com.example.demo.entities.Mouse;
import lombok.SneakyThrows;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;

@Testcontainers
@SpringBootTest(classes = {PostgresTestConfig.class})
@Slf4j
public class MouseServiceImplTest {
    @Autowired
    private MouseController mouseController;

    @SneakyThrows
    @Test
    public void testAddMouse() {
        UUID id = UUID.randomUUID();
        Mouse mouse = Mouse.builder().id(id).name("Mouse").build();

        mouseController.create(mouse);

        Mouse mouseCreated = mouseController.getById(id);
        assertThat(mouseCreated.getId()).isEqualTo(id);
        assertThat(mouseCreated.getName()).isEqualTo(mouse.getName());
    }

    @SneakyThrows
    @Test
    public void testDeleteMouse() {
        UUID id = UUID.randomUUID();
        Mouse mouse = Mouse.builder().id(id).name("Mouse").build();

        mouseController.create(mouse);
        Mouse mouseFound = mouseController.getById(id);
        assertThat(mouseFound).isNotNull();
        assertThat(mouseFound.getId()).isEqualTo(id);

        mouseController.delete(id);
        assertThat(mouseController.getById(id)).isNull();

        assertThatThrownBy(() -> mouseController.getById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Mouse not found");
    }

    @SneakyThrows
    @Test
    public void testUpdateMouse() {
        UUID id = UUID.randomUUID();
        Mouse mouse = Mouse.builder().id(id).name("Mouse").build();

        mouseController.create(mouse);
        Mouse mouseFound = mouseController.getById(id);
        assertThat(mouseFound).isNotNull();
        assertThat(mouseFound.getId()).isEqualTo(id);

        Mouse mouseUpdated = new Mouse();
        mouseUpdated.setName("Mice");
        mouseUpdated.setId(id);
        mouseController.update(mouseUpdated);
        Mouse updated = mouseController.getById(id);
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo(mouseUpdated.getName());
        assertThat(updated.getId()).isEqualTo(id);
    }

    @SneakyThrows
    @Test
    public void testGetMouseById() {
        UUID id = UUID.randomUUID();
        Mouse mouse = Mouse.builder().id(id).name("Mouse").build();

        mouseController.create(mouse);
        Mouse found = mouseController.getById(id);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getName()).isEqualTo(mouse.getName());
    }

    @SneakyThrows
    @Test
    public void testGetAllMouse() {
        UUID id1 = UUID.randomUUID();
        Mouse mouse1 = Mouse.builder().id(id1).name("Mouse").build();

        UUID id2 = UUID.randomUUID();
        Mouse mouse2 = Mouse.builder().id(id2).name("Mice").build();

        mouseController.create(mouse1);
        mouseController.create(mouse2);

        List<Mouse> mouseList = mouseController.getAll();
        assertThat(mouseList).isNotNull();
        assertThat(mouseList.size()).isEqualTo(2);
        assertThat(mouseList.get(0).getId()).isEqualTo(id1);
        assertThat(mouseList.get(0).getName()).isEqualTo(mouse1.getName());
        assertThat(mouseList.get(1).getId()).isEqualTo(id2);
        assertThat(mouseList.get(1).getName()).isEqualTo(mouse2.getName());
    }

}