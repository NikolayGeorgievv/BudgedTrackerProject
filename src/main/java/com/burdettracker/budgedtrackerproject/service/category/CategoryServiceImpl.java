package com.burdettracker.budgedtrackerproject.service.category;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Category;
import com.burdettracker.budgedtrackerproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(AddCategoryDTO addCategoryDTO) {
        Category category = new Category(addCategoryDTO.getCategory().toUpperCase());
        categoryRepository.saveAndFlush(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }
}
