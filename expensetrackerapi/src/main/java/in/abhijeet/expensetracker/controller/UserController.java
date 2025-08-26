package in.abhijeet.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.abhijeet.expensetracker.entity.User;
import in.abhijeet.expensetracker.entity.UserModel;
import in.abhijeet.expensetracker.service.UserService;
import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel user)
	{
		return new ResponseEntity<User>(userService.createUser(user),HttpStatus.CREATED);
	}

}
