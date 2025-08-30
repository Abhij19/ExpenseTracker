package in.abhijeet.expensetracker.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.abhijeet.expensetracker.service.ExpenseService;
import in.abhijeet.expensetracker.service.UserService;
import jakarta.validation.Valid;
import in.abhijeet.expensetracker.entity.Expense;

@RestController
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/expenses")
	public List<Expense> getAllExpenses()
	{
		return expenseService.getAllExpenses();
	}
	
	@GetMapping("/expenses/pagination")
	public List<Expense> getAllExpensesWithPagination(Pageable page) {
		return expenseService.getAllExpensesWithPagination(page).toList();
	}
	
	@GetMapping("/expenses/{id}")
	public Expense getExpenseById(@PathVariable Long id)
	{
		return expenseService.getExpenseById(id);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void deleteExpenseById(@RequestParam Long id)
	{
		expenseService.deleteExpenseById(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/expenses")
	public Expense saveExpenseDetails(@Valid @RequestBody Expense expense)
	{
		expense.setUser(userService.getLoggedInUser());
		return expenseService.saveExpenseByDetails(expense);
	}
	
	@PutMapping("/expenses/{id}")
	public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id)
	{
		return expenseService.updateExpenseDetails(expense, id);
	}
	
	@GetMapping("/expenses/category")
	public List<Expense> getExpenseByCategory(@RequestParam String category, Pageable page)
	{
		return expenseService.readByCategory(category, page);
	}
	
	@GetMapping("/expenses/names")
	public List<Expense> getExpenseByName(@RequestParam String name, Pageable page)
	{
		return expenseService.readByName(name, page);
	}
	
	@GetMapping("/expenses/date")
	public List<Expense> getExpenseByDate(@RequestParam(required=false) Date startDate, 
			@RequestParam(required=false) Date endDate, Pageable page)
	{
		return expenseService.readByDate(startDate, endDate, page);
	}
}
