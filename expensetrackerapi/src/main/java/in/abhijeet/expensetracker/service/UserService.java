package in.abhijeet.expensetracker.service;

import in.abhijeet.expensetracker.entity.User;
import in.abhijeet.expensetracker.entity.UserModel;

public interface UserService {

	User createUser(UserModel user);
	
	User readUser(Long id);
	
	User updateUser(UserModel user, Long id);
	
	void deleteUser(Long id);
	
	User getLoggedInUser();
}
