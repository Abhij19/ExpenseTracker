package in.abhijeet.expensetracker.repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.abhijeet.expensetracker.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	// filter by category
	// Internally hibernate runs SELECT * FROM TABLE_NAME WHERE category=?
	Page<Expense> findByCategory(String category,Pageable page);
	
	// Internally hibernate runs SELECT * FROM TABLE_NAME WHERE name LIKE '%name'
	Page<Expense> findByName(String name,Pageable page);
	
	// Internally hibernate runs SELECT * FROM TABLE_NAME WHERE date BETWEEN 'startDate' AND 'endDate'
	Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);

}
