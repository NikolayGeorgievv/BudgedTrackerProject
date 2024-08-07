package com.burdettracker.budgedtrackerproject.service.category;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Category;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.CategoryRepository;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Override
    public void addCategory(AddCategoryDTO addCategoryDTO, String currentUserName) {
        User user = this.userService.getUserByEmail(currentUserName);
        Category category = new Category(addCategoryDTO.getCategory().toUpperCase(), false);
        user.getCategories().add(category);
        this.categoryRepository.saveAndFlush(category);
        this.userService.saveAndFlush(user);
    }

    @Override
    public List<Category> getAllCategories(String currentUserName) {
        User user = this.userService.getUserByEmail(currentUserName);
        return user.getCategories();
    }
}
