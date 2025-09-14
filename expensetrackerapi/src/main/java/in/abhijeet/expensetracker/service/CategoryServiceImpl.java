package in.abhijeet.expensetracker.service;

import in.abhijeet.expensetracker.dto.CategoryDTO;
import in.abhijeet.expensetracker.dto.UserDTO;
import in.abhijeet.expensetracker.entity.Category;
import in.abhijeet.expensetracker.entity.User;
import in.abhijeet.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Used for constructor injection now need to use @Autowired annotation
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final UserService userService;
    @Override
    public List<CategoryDTO> getAllCategories() {
       List<Category> list = categoryRepository.findByUserId(userService.getLoggedInUser().getId());
       return list.stream()
               .map(category -> mapToCategoryDTO(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category newCategory = mapToEntity(categoryDTO);
        newCategory = categoryRepository.save(newCategory);
        return mapToCategoryDTO(newCategory);
    }

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

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    private Category mapToEntity(CategoryDTO categoryDTO)
    {
        return Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .categoryIcon(categoryDTO.getCategoryIcon())
                .categoryId(UUID.randomUUID().toString())
                .build();
    }
}
