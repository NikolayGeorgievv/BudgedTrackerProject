package com.burdettracker.budgedtrackerproject.init;

import com.burdettracker.budgedtrackerproject.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoriesRepositoryInitTest {
    private CategoriesRepositoryInit categoriesRepositoryInit;
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoriesRepositoryInit = new CategoriesRepositoryInit(categoryRepository);
    }

    @Test
    public void testRun() throws Exception {
        when(categoryRepository.count()).thenReturn(0L);

        categoriesRepositoryInit.run();

        verify(categoryRepository, times(1)).saveAllAndFlush(any(List.class));
    }
}