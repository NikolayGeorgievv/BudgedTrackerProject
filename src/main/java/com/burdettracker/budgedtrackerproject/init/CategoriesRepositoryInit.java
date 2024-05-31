package com.burdettracker.budgedtrackerproject.init;

import com.burdettracker.budgedtrackerproject.model.entity.Category;
import com.burdettracker.budgedtrackerproject.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!test")
public class CategoriesRepositoryInit implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    public CategoriesRepositoryInit(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count() == 0){
            Category GENERAL = new Category("GENERAL");
            Category UTILITIES = new Category("UTILITIES");
            Category SUBSCRIPTIONS = new Category("SUBSCRIPTIONS");
            Category ENTERTAINMENT = new Category("ENTERTAINMENT");
            Category EDUCATION = new Category("EDUCATION");
            Category TRAVEL = new Category("TRAVEL");


            List<Category> basicCategories = List.of(GENERAL, UTILITIES, SUBSCRIPTIONS, ENTERTAINMENT, EDUCATION, TRAVEL);

            categoryRepository.saveAllAndFlush(basicCategories);
        }
    }
}
