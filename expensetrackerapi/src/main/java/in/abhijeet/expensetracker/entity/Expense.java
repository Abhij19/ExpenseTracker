package in.abhijeet.expensetracker.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_expenses")
public class Expense {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "expense_name")
	@NotBlank(message="Expense name must not be null or empty")
	@Size(min=3, message="Expense name must be 3 characters long")
	private String name;
	
	private String description;
	
	@Column(name = "expense_amount")
	@NotNull(message="Expense amount must not be null")
	private BigDecimal amount;
	
	@NotBlank(message="Expense category must not be empty")
	private String category;
	
	@NotNull(message="Expense date must not be null or empty")
	private Date date;
	
	@Column(name="creation_timestamp", nullable=false, updatable=false)
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name="updated_at")
	@UpdateTimestamp
	private Timestamp updatedAt;
}
