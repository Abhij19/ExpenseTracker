package in.abhijeet.expensetracker.repository;

import in.abhijeet.expensetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for Category entity
 * @author Abhijeet Jha
 * */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * finder method to retrieve the categories by user id
     * @param userId
     * @return list
     * */
    List<Category> findByUserId(Long userId);

    /**
     * finder method fetch the category by user id and category id
     * @param userId, categoryId
     * @return Optional<CategoryEntity>
     * */
    Optional<Category> findByUserIdAndCategoryId(Long userId, String categoryId);
}
