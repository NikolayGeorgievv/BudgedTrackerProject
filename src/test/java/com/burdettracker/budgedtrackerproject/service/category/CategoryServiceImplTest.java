package com.burdettracker.budgedtrackerproject.service.category;

import com.burdettracker.budgedtrackerproject.model.dto.expense.AddCategoryDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Category;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.CategoryRepository;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.burdettracker.budgedtrackerproject.utils.TestUtils.createDummyUser;
import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    UserService userService;
    @Mock
    CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository,  userService);
        userRepository.deleteAll();
    }

    @Test
    void addCategory() {
        AddCategoryDTO addCategoryDTO = new AddCategoryDTO("TestCategory");
        User user = createDummyUser(rolesRepository);
        user.setCategories(new ArrayList<>());
        userRepository.saveAndFlush(user);

        categoryService.addCategory(addCategoryDTO, user.getEmail());

        verify(categoryRepository, times(1)).saveAndFlush(any(Category.class));
    }

//    @Test
//    void getAllCategories() {
//        User user = createDummyUser(rolesRepository);
//        List<Category> categories = new ArrayList<>();
//        categories.add(new Category("TestCategory1", false, user));
//        categories.add(new Category("TestCategory2", false, user));
//        user.setCategories(categories);
//        userRepository.saveAndFlush(user);
//
//        List<Category> returnedCategories = categoryService.getAllCategories(user.getEmail());
//
//        Assertions.assertEquals(categories, returnedCategories);
//    }
}