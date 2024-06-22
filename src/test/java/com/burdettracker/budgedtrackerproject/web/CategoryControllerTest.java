package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;

    @Mock
    private BindingResult bindingResult;

    @Test
    void addCategory() {

        AddCategoryDTO addCategoryDTO = new AddCategoryDTO("TestCategory");
        assertEquals("redirect:/homePage", categoryController.addCategory(addCategoryDTO, bindingResult));
    }
}