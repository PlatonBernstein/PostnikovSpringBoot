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
import java.util.stream.Stream;

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

        mouseController.create(mouseName1);
        mouseController.create(mouseName2);

        List<String> namesListNew = mouseController.getAll(Optional.empty()).stream()
                .map(Mouse::getName)
                .toList();
        assertThat(namesListNew).containsAll(namesListOld);
    }

    @SneakyThrows
    @Test
    public void testAddMouse() {
        String uniqueMouseName = UUID.randomUUID().toString();
        mouseController.create(uniqueMouseName);

        List<String> namesList = mouseController.getAll(Optional.empty()).stream()
                .map(Mouse::getName)
                .toList();
        assertThat(namesList).contains(uniqueMouseName);
    }

    @SneakyThrows
    @Test
    public void testDeleteMouse() {
        String uniqueMouseName = UUID.randomUUID().toString();
        mouseController.create(uniqueMouseName);

        UUID mouseID = mouseController.getAll(Optional.empty()).stream()
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
        mouseController.create(uniqueMouseName);

        UUID mouseId = mouseController.getAll(Optional.empty()).stream()
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
        mouseController.create(uniqueMouseName);

        UUID mouseId = mouseController.getAll(Optional.empty()).stream()
                .filter(m -> m.getName().equals(uniqueMouseName))
                .map(Mouse::getId)
                .toList()
                .getFirst();

        Mouse mouseFound = mouseController.getById(mouseId);
        assertThat(mouseFound).isNotNull();
        assertThat(mouseFound.getId()).isEqualTo(mouseId);
        assertThat(mouseFound.getName()).isEqualTo(uniqueMouseName);
    }

    @SneakyThrows
    @Test
    public void testBigDataGet() {
        List<String> mouseNamesOrig =  new ArrayList<>();
        for (int i = 0; i < 200000; i++) {
            String mouseCurrName = "Mouse" + i;
            mouseNamesOrig.add(mouseCurrName);
            mouseController.create(mouseCurrName);
        }

        List<String> mouseNamesCreated = mouseController.getAll(Optional.empty()).stream()
                .map(Mouse::getName)
                .toList();

        assertThat(mouseNamesCreated).containsAll(mouseNamesOrig);

        // время теста: 9 минут, 51 секунда
        // к слову, увеличенная версия теста на getAll
    }

    @SneakyThrows
    @Test
    public void testGetAllPageable() {
        String uniqueMouseName1 = UUID.randomUUID().toString();
        mouseController.create(uniqueMouseName1);

        String uniqueMouseName2 = UUID.randomUUID().toString();
        mouseController.create(uniqueMouseName2);

        String uniqueMouseName3 = UUID.randomUUID().toString();
        mouseController.create(uniqueMouseName3);

        String uniqueMouseName4 = UUID.randomUUID().toString();
        mouseController.create(uniqueMouseName4);

        String uniqueMouseName5 = UUID.randomUUID().toString();
        mouseController.create(uniqueMouseName5);

        Pageable firstPageWithTwoElements = PageRequest.of(0, 3);
        Pageable secondPageWithThreeElements = PageRequest.of(1, 3);

        List<Mouse> mouseList1 = mouseController.getAll(firstPageWithTwoElements.toOptional());
        List<Mouse> mouseList2 = mouseController.getAll(secondPageWithThreeElements.toOptional());
        List<Mouse> mouseList3 = Stream.concat(mouseList1.stream(), mouseList2.stream()).toList();

        System.out.println(mouseList1);
        System.out.println(mouseList2);
        System.out.println(mouseList3);
    }

}