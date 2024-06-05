package com.burdettracker.budgedtrackerproject.model.dto.expense;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AddCategoryDTO {

    @NotEmpty
    @Size(min = 2, max = 40, message = "Category name length must be between 2 and 40 symbols.")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
