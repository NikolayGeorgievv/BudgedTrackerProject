package com.burdettracker.budgedtrackerproject.web.advisedControllers;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/addCategory")
    public String addCategory(@Valid AddCategoryDTO addCategoryDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/homePage";
        }
        categoryService.addCategory(addCategoryDTO);
        return "redirect:/homePage";
    }
}
