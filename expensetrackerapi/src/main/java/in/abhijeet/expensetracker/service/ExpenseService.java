package in.abhijeet.expensetracker.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.abhijeet.expensetracker.entity.Expense;

public interface ExpenseService {

	List<Expense> getAllExpenses();
	
	Page<Expense> getAllExpensesWithPagination(Pageable page);
	
	Expense getExpenseById(Long id);
	
	public void deleteExpenseById(Long id);
	
	Expense saveExpenseByDetails(Expense expense);
	
	Expense updateExpenseDetails(Expense expense, Long id);
	
	List<Expense> readByCategory(String category,Pageable page);
	
	List<Expense> readByName(String name,Pageable page);
	
	List<Expense> readByDate(Date startDate, Date endDate, Pageable page);
}
