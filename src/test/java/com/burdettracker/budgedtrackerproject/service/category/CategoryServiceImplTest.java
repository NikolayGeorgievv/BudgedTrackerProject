package com.burdettracker.budgedtrackerproject.service.category;

import com.burdettracker.budgedtrackerproject.model.entity.Category;
import com.burdettracker.budgedtrackerproject.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;


    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void addCategory() {
    }

    @Test
    void getAllCategories() {

        Category category = new Category();
        List<Category> categoriesData = new ArrayList<>();
        categoriesData.add(category);

        when(categoryRepository.findAll()).thenReturn(categoriesData);

        List<Category> categories = categoryService.getAllCategories();

        Assertions.assertEquals(categories.size(), 1);
        verify(categoryRepository, times(1)).findAll();
    }
}