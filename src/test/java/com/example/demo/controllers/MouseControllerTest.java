package com.example.demo.controllers;
import com.example.demo.config.PostgresTestConfig;
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
public class MouseControllerTest {
    @Autowired
    private MouseController mouseController;

    @SneakyThrows
    @Test
    public void testAddMouse() {
        Mouse mouse = new Mouse();
        mouse.setName("Mouse");
        UUID id = UUID.randomUUID();
        mouse.setId(id);

        Mouse added = mouseController.create(mouse);

        assertThat(added.getId()).isEqualTo(id);
        assertThat(added.getName()).isEqualTo(mouse.getName());
    }

    @SneakyThrows
    @Test
    public void testDeleteMouse() {
        Mouse mouse = new Mouse();
        mouse.setName("Mouse");
        UUID id = UUID.randomUUID();
        mouse.setId(id);

        mouseController.create(mouse);
        Mouse found = mouseController.getById(id);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(id);

        mouseController.delete(id);
        assertThat(mouseController.getById(id)).isNull();

        assertThatThrownBy(() -> mouseController.getById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Mouse not found");
    }

    @SneakyThrows
    @Test
    public void testUpdateMouse() {
        Mouse mouse = new Mouse();
        mouse.setName("Mouse");
        UUID id = UUID.randomUUID();
        mouse.setId(id);

        mouseController.create(mouse);
        Mouse found = mouseController.getById(id);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(id);

        Mouse mouseUpdated = new Mouse();
        mouseUpdated.setName("Mice");
        mouseUpdated.setId(id);
        mouseController.update(id, mouseUpdated);
        Mouse updated = mouseController.getById(id);
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo(mouseUpdated.getName());
        assertThat(updated.getId()).isEqualTo(id);
    }

    @SneakyThrows
    @Test
    public void testGetMouseById() {
        Mouse mouse = new Mouse();
        mouse.setName("Mouse");
        UUID id = UUID.randomUUID();
        mouse.setId(id);

        mouseController.create(mouse);
        Mouse found = mouseController.getById(id);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(id);
        assertThat(found.getName()).isEqualTo(mouse.getName());
    }

    @SneakyThrows
    @Test
    public void testGetAllMouse() {
        Mouse mouse1 = new Mouse();
        mouse1.setName("Mouse");
        UUID id1 = UUID.randomUUID();
        mouse1.setId(id1);

        // два рандомных айдишника - грех, надо исправить
        Mouse mouse2 = new Mouse();
        mouse2.setName("Mice");
        UUID id2 = UUID.randomUUID();
        mouse2.setId(id2);

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
