package in.abhijeet.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.abhijeet.expensetracker.entity.User;
import in.abhijeet.expensetracker.entity.UserModel;
import in.abhijeet.expensetracker.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> readUser(@PathVariable Long id)
	{
		return new ResponseEntity<User>(userService.readUser(id),HttpStatus.OK);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserModel user)
	{
		return new ResponseEntity<User>(userService.updateUser(user, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id)
	{
		userService.deleteUser(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

}
