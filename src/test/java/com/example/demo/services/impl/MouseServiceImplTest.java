package com.example.demo.services.impl;

import com.example.demo.exceptions.MouseNotFoundException;
import com.example.demo.config.PostgresTestConfig;
import com.example.demo.controllers.MouseController;
import com.example.demo.entities.Mouse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.*;

@Testcontainers
@SpringBootTest(classes = {PostgresTestConfig.class})
@Slf4j
public class MouseServiceImplTest {
    @Autowired
    private MouseController mouseController;
    private static final Logger logger = LoggerFactory.getLogger(MouseServiceImplTest.class);

    @BeforeEach
    public void setup() {
        mouseController.deleteAll();
    }

    @SneakyThrows
    @Test
    public void testGetAllMouse() {
        String mouseName1 = "Mouse";
        String mouseName2 = "Mice";
        List<String> namesListOld = new ArrayList<>();
        namesListOld.add(mouseName1);
        namesListOld.add(mouseName2);

        List<String> namesListNew = mouseController.getAll(Pageable.unpaged()).stream()
                .map(Mouse::getName)
                .toList();
        assertThat(namesListNew).containsAll(namesListOld);
    }

    @SneakyThrows
    @Test
    public void testAddMouse() {
        Mouse mouse1 = Mouse.builder().name("Mouse").build();
        mouseController.create(mouse1);

        List<String> namesList = mouseController.getAll(Pageable.unpaged()).stream()
                .map(Mouse::getName)
                .toList();
        assertThat(namesList).contains(mouse1.getName());
    }

    @SneakyThrows
    @Test
    public void testDeleteMouse() {
        String uniqueMouseName = UUID.randomUUID().toString();

        UUID mouseID = mouseController.getAll(Pageable.unpaged()).stream()
                .filter(m -> m.getName().equals(uniqueMouseName))
                .map(Mouse::getId)
                .toList()
                .getFirst();

        mouseController.delete(mouseID);
        assertThatThrownBy(() -> mouseController.getById(mouseID))
                .isInstanceOf(MouseNotFoundException.class)
                .hasMessageContaining("is not found");
    }

    @SneakyThrows
    @Test
    public void testUpdateMouse() {
        String uniqueMouseName = UUID.randomUUID().toString();

        UUID mouseId = mouseController.getAll(Pageable.unpaged()).stream()
                .filter(m -> m.getName().equals(uniqueMouseName))
                .map(Mouse::getId)
                .toList()
                .getFirst();

        Mouse mouseUpdated = Mouse.builder().id(mouseId).name("Mice").build();
        mouseController.update(mouseUpdated);
        Mouse updated = mouseController.getById(mouseId);
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo(mouseUpdated.getName());
        assertThat(updated.getId()).isEqualTo(mouseId);
    }

    @SneakyThrows
    @Test
    public void testGetMouseById() {
        String uniqueMouseName = UUID.randomUUID().toString();

        UUID mouseId = mouseController.getAll(Pageable.unpaged()).stream()
                .filter(m -> m.getName().equals(uniqueMouseName))
                .map(Mouse::getId)
                .toList()
                .getFirst();

        Mouse mouseFound = mouseController.getById(mouseId);
        assertThat(mouseFound).isNotNull();
        assertThat(mouseFound.getId()).isEqualTo(mouseId);
        assertThat(mouseFound.getName()).isEqualTo(uniqueMouseName);
    }

    /* смысл теста должен быть в том, чтобы создать такой набор записей, пейджи которого будут предсказуемыми,
    и ассёртами их сверять, соответственно пейдж ту пейдж*/
    @SneakyThrows
    @Test
    public void testBigDataGet() {
        int numberOfPages = 10000;
        int pageSize = 5;
        List<List<Mouse>> listOfPages = new ArrayList<>();
        for (int i = 0; i < numberOfPages; i++) {
            List<Mouse> pageOfMouses =  new ArrayList<>();
            for (int j = 0; j < pageSize; j++) {
                Mouse mouseCurr = Mouse.builder().name("Mouse" + (i * pageSize + j)).build();
                pageOfMouses.add(mouseCurr);
                mouseController.create(mouseCurr);
            }
            listOfPages.add(pageOfMouses);
        }

        for (int i = 0; i < numberOfPages; i++) {
            List<Mouse> pageOfCreatedMouses = mouseController.getAll(PageRequest.of(i, pageSize)).toList();

            List<String> expectedNames = listOfPages.get(i).stream()
                    .map(Mouse::getName)
                    .toList();
            List<String> actualNames = pageOfCreatedMouses.stream()
                    .map(Mouse::getName)
                    .toList();

            assertThat(actualNames).containsExactlyElementsOf(expectedNames);
        }
    }
}