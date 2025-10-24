package com.example.demo.services.impl;

import com.example.demo.exceptions.MouseNotFoundException;
import com.example.demo.entities.Mouse;
import com.example.demo.repositories.MouseRepository;
import com.example.demo.services.MouseService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MouseServiceImpl implements MouseService {
    @Autowired
    private final EntityManager entityManager;

    @Autowired
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
        /* учитывая, что у нас UUID, а не индекс, этот метод вообще имеет смысл? С пагинацией и сортировкой одиночные
        данные можно не хуже выбирать*/
        return mouseRepository.findById(id).orElseThrow(() -> new MouseNotFoundException(id));
    }

    @Override
    public Page<Mouse> getAll(Pageable pageable, boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedProductFilter");
        filter.setParameter("isDeleted", isDeleted);
        if (!pageable.isPaged()) pageable = PageRequest.of(0, 5);
        Page<Mouse> mouses = mouseRepository.findAll(pageable);
        session.disableFilter("deletedProductFilter");
        return mouses;
    }
}
