package in.abhijeet.expensetracker.service;

import in.abhijeet.expensetracker.dto.CategoryDTO;
import in.abhijeet.expensetracker.dto.UserDTO;
import in.abhijeet.expensetracker.entity.Category;
import in.abhijeet.expensetracker.entity.User;
import in.abhijeet.expensetracker.exceptions.ItemAlreadyExistsException;
import in.abhijeet.expensetracker.exceptions.ResourceNotFoundException;
import in.abhijeet.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Used for constructor injection now need to use @Autowired annotation
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    /**
     * This is for reading the categories from database
     * @return list
     * */
    @Override
    public List<CategoryDTO> getAllCategories() {
        //SELECT * FROM tbl_categories WHERE user_id = ?
       List<Category> list = categoryRepository.findByUserId(userService.getLoggedInUser().getId());
       // returns a list of CategoryDTO objects (one per category owned by the logged-in user).
       return list.stream()
               .map(category -> mapToCategoryDTO(category)).collect(Collectors.toList());
    }

    /**
     * This is for creating the new category
     * @param categoryDTO
     * @return CategoryDTO
     * */
    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {

        boolean category = categoryRepository.existsByNameAndUserId(categoryDTO.getName(),
                userService.getLoggedInUser().getId());
        if(category)
        {
            throw new ItemAlreadyExistsException("Category is already present for the name : " + categoryDTO.getName());
        }
        // Map incoming DTO object from controller to JPA Entity
        Category newCategory = mapToEntity(categoryDTO);
        // save entity object to DB
        newCategory = categoryRepository.save(newCategory);
        //converts the saved entity (now enriched with DB-generated values) back into a DTO for returning to the controller.
        return mapToCategoryDTO(newCategory);
    }

    /**
     * This is for deleting the category from database
     * @param categoryId
     * */
    @Override
    public void deleteCategory(String categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findByUserIdAndCategoryId
                (userService.getLoggedInUser().getId(),categoryId);
        if(!optionalCategory.isPresent())
        {
            throw new ResourceNotFoundException("Category not found for the id : " + categoryId);
        }
        categoryRepository.delete(optionalCategory.get());
    }

    /**
     * Mapper method to convert Category entity to Category DTO
     * @param category
     * @return CategoryDTO
     * */
    private CategoryDTO mapToCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryIcon(category.getCategoryIcon())
                .description(category.getDescription())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .userDTO(mapToUserDTO(category.getUser()))
                .build();
    }

    /**
     * Mapper method to convert User entity to User DTO
     * @param user
     * @return UserDTO
     * */
    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    /**
     * Mapper method to convert Category DTO to Category entity
     * @param categoryDTO
     * @return Category
     * */
    private Category mapToEntity(CategoryDTO categoryDTO)
    {
        return Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .categoryId(UUID.randomUUID().toString())
                .user(userService.getLoggedInUser())
                .build();
    }
}
