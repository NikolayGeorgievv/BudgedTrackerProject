package com.burdettracker.budgedtrackerproject.service.category;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(AddCategoryDTO addCategoryDTO, String currentUserName);

    List<Category> getAllCategories(String currentUserName);
}
