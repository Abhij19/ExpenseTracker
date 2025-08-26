package in.abhijeet.expensetracker.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.abhijeet.expensetracker.entity.User;
import in.abhijeet.expensetracker.entity.UserModel;
import in.abhijeet.expensetracker.exceptions.ItemAlreadyExistsException;
import in.abhijeet.expensetracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User createUser(UserModel user) {
		if(userRepository.existsByEmail(user.getEmail()))
		{
			throw new ItemAlreadyExistsException("User is already registerd with email: "+user.getEmail());
		}
		User newUser=new User();
		BeanUtils.copyProperties(user, newUser);
		return userRepository.save(newUser);
	}

}
