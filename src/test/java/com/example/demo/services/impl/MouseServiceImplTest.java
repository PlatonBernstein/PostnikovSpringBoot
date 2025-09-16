package com.example.demo.services.impl;

import com.example.demo.exceptions.MouseNotFoundException;
import com.example.demo.config.PostgresTestConfig;
import com.example.demo.controllers.MouseController;
import com.example.demo.entities.Mouse;
import lombok.SneakyThrows;
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
    public void testGetAllMouse() {
        String mouseName1 = "Mouse";
        String mouseName2 = "Mice";

        mouseController.create(mouseName1);
        mouseController.create(mouseName2);

        List<Mouse> mouseList = mouseController.getAll();
        assertThat(mouseList).isNotNull();
        assertThat(mouseList.size()).isEqualTo(2);
        Mouse mouse1 = mouseList.stream()
                .filter(m -> m.getName().equals(mouseName1))
                .findFirst()
                .get();
        Mouse mouse2 = mouseList.stream()
                .filter(m -> m.getName().equals(mouseName2))
                .findFirst()
                .get();
        assertThat(mouse1).isNotNull();
        assertThat(mouse2).isNotNull();
        mouseController.delete(mouse1.getId());
        mouseController.delete(mouse2.getId());
    }

    @SneakyThrows
    @Test
    public void testAddMouse() {
        String mouseName = "Mouse";
        mouseController.create(mouseName);

        List<Mouse> mousesList = mouseController.getAll();
        Mouse mouseCreated = new Mouse();
        for (Mouse mouse : mousesList) {
            if (mouse.getName().equals(mouseName)) {
                mouseCreated = mouse;
                break;
            }
        }

        assertThat(mouseCreated.getName()).isEqualTo(mouseName);
        mouseController.delete(mouseCreated.getId());
    }

    @SneakyThrows
    @Test
    public void testDeleteMouse() {
        String mouseName = "Mouse";
        mouseController.create(mouseName);

        List<Mouse> mousesList = mouseController.getAll();
        UUID mouseID = mousesList.stream()
                .filter(m -> m.getName().equals(mouseName))
                .findFirst()
                .map(Mouse::getId)
                .orElseThrow(() -> new RuntimeException("Mouse with name " + mouseName + " not found"));

        mouseController.delete(mouseID);
        assertThatThrownBy(() -> mouseController.getById(mouseID))
                .isInstanceOf(MouseNotFoundException.class)
                .hasMessageContaining("is not found");
    }

    @SneakyThrows
    @Test
    public void testUpdateMouse() {
        String mouseName = "Mouse";
        mouseController.create(mouseName);

        List<Mouse> mousesList = mouseController.getAll();
        UUID mouseID = mousesList.stream()
                .filter(m -> m.getName().equals(mouseName))
                .findFirst()
                .map(Mouse::getId)
                .orElseThrow(() -> new RuntimeException("Mouse with name " + mouseName + " not found"));

        Mouse mouseUpdated = Mouse.builder().id(mouseID).name("Mice").build();
        mouseController.update(mouseUpdated);
        Mouse updated = mouseController.getById(mouseID);
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo(mouseUpdated.getName());
        assertThat(updated.getId()).isEqualTo(mouseID);
        mouseController.delete(mouseID);
    }

    @SneakyThrows
    @Test
    public void testGetMouseById() {
        String mouseName = "Mouse";
        mouseController.create(mouseName);

        List<Mouse> mousesList = mouseController.getAll();
        UUID mouseID = mousesList.stream()
                .filter(m -> m.getName().equals(mouseName))
                .findFirst()
                .map(Mouse::getId)
                .orElseThrow(() -> new RuntimeException("Mouse with name " + mouseName + " not found"));

        Mouse mouseFound = mouseController.getById(mouseID);
        assertThat(mouseFound).isNotNull();
        assertThat(mouseFound.getId()).isEqualTo(mouseID);
        assertThat(mouseFound.getName()).isEqualTo(mouseName);
        mouseController.delete(mouseID);
    }



}