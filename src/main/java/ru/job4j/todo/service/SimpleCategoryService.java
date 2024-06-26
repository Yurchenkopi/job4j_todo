package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.HbmCategoryRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private final HbmCategoryRepository hbmCategoryRepository;

    @Override
    public Collection<Category> findAll() {
        return hbmCategoryRepository.findAll();
    }

    @Override
    public Collection<Category> findAllById(Collection<Integer> categoriesId) {
        return hbmCategoryRepository.findByIds(categoriesId);
    }
}
