package in.abhijeet.expensetracker.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {

	@NotBlank(message="Name should not be empty")
	private String name;
	
	@Email(message="Enter a valid email")
	@NotNull(message="Email should not be empty")
	private String email;
	
	@Size(min=5,message="Size should be atleast 5 characters")
	@NotNull(message="Password should not be empty")
	private String password;
	
	private Long age=0L;
}
