package in.abhijeet.expensetracker.controller;

import in.abhijeet.expensetracker.dto.CategoryDTO;
import in.abhijeet.expensetracker.io.CategoryRequest;
import in.abhijeet.expensetracker.io.CategoryResponse;
import in.abhijeet.expensetracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This controller is for managing the categories
 * @author Abhijeet Jha
 * */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * API for creating the category
     * @param categoryRequest
     * @return CategoryResponse
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest)
    {
        // 1. Request → DTO
        CategoryDTO categoryDTO = mapToCategoryDTO(categoryRequest);
        // 2. Call service (service will handle persistence)
        categoryDTO = categoryService.saveCategory(categoryDTO);
        // 3. DTO → Response object (to send back to client)
        return mapToResponse(categoryDTO);
    }

    /**
     * API for reading the categories
     * @return list
     * */
    @GetMapping
    public List<CategoryResponse> readCategories()
    {
        List<CategoryDTO> listOfCategories = categoryService.getAllCategories();
        return listOfCategories
                .stream()
                .map(categoryDTO -> mapToResponse(categoryDTO)).collect(Collectors.toList());
    }

    /**
     * API for deleting the category
     * @param categoryId
     *
     * */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId)
    {
        categoryService.deleteCategory(categoryId);
    }

    /**
     * Mapper method for converting DTO Object to Response Object
     * @param categoryDTO
     * @return CategoryResponse
     * */
    private CategoryResponse mapToResponse(CategoryDTO categoryDTO) {
        return CategoryResponse.builder()
                .categoryId(categoryDTO.getCategoryId())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .description(categoryDTO.getDescription())
                .name(categoryDTO.getName())
                .createdAt(categoryDTO.getCreatedAt())
                .updatedAt(categoryDTO.getUpdatedAt())
                .build();
    }

    /**
     * Mapper method for converting Request Object to DTO Object
     * @param categoryRequest
     * @return CategoryDTO
     * */
    private CategoryDTO mapToCategoryDTO(CategoryRequest categoryRequest)
    {
//        Here we are only setting 3 fields (name, description, icon) from the request because:
//        These are the only things the client can send.
        return CategoryDTO.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .categoryIcon(categoryRequest.getIcon())
                .build();
    }
}
