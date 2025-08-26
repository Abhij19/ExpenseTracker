package in.abhijeet.expensetracker.service;

import in.abhijeet.expensetracker.entity.User;
import in.abhijeet.expensetracker.entity.UserModel;

public interface UserService {

	User createUser(UserModel user);
}
