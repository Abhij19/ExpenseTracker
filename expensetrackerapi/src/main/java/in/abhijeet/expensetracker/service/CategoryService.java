package in.abhijeet.expensetracker.service;

import in.abhijeet.expensetracker.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
}
