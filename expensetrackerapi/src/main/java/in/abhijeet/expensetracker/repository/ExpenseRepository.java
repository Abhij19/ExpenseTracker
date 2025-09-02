package in.abhijeet.expensetracker.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.abhijeet.expensetracker.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	// filter by category
	// Internally hibernate runs SELECT * FROM TABLE_NAME WHERE category=? AND user_id=?
	Page<Expense> findByUserIdAndCategory(Long userId,String category,Pageable page);
	
	// Internally hibernate runs SELECT * FROM TABLE_NAME WHERE user_id=? AND name LIKE '%name'
	Page<Expense> findByUserIdAndName(Long userId,String name,Pageable page);
	
	// Internally hibernate runs SELECT * FROM TABLE_NAME WHERE user_id=? date 
	//BETWEEN 'startDate' AND 'endDate'
	Page<Expense> findByUserIdAndDateBetween(Long userId,Date startDate, Date endDate, Pageable page);
	
	Page<Expense> findByUserId(Long userId,Pageable page);
	
	Optional<Expense> findByUserIdAndId(Long userId,Long expenseId);
}
