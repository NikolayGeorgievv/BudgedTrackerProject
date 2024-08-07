package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.web.advisedControllers.CategoryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

import static com.burdettracker.budgedtrackerproject.utils.TestUtils.createDummyUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("myEmail@example.com")
class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;
    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }
    @Test
    void addCategory() {
        User user = createDummyUser(rolesRepository);
        userRepository.save(user);
        AddCategoryDTO addCategoryDTO = new AddCategoryDTO("TestCategory");
        assertEquals("redirect:/homePage", categoryController.addCategory(addCategoryDTO, bindingResult));
    }
}