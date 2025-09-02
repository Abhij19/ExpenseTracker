package in.abhijeet.expensetracker.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.abhijeet.expensetracker.entity.User;
import in.abhijeet.expensetracker.entity.UserModel;
import in.abhijeet.expensetracker.exceptions.ItemAlreadyExistsException;
import in.abhijeet.expensetracker.exceptions.ResourceNotFoundException;
import in.abhijeet.expensetracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder bCryptEncoder;
	
	@Override
	public User createUser(UserModel user) {
		if(userRepository.existsByEmail(user.getEmail()))
		{
			throw new ItemAlreadyExistsException("User is already registerd with email: "+user.getEmail());
		}
		User newUser=new User();
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(bCryptEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}

	@Override
	public User readUser() {
		Long userId=getLoggedInUser().getId();
		return userRepository.findById(userId).orElseThrow(()-> 
		new ResourceNotFoundException("User not found for the id :"+userId));
	}

	@Override
	public User updateUser(UserModel user) {
		User existingUser=readUser();
		existingUser.setAge(user.getAge()!=null?user.getAge():existingUser.getAge());
		existingUser.setName(user.getName()!=null?user.getName():existingUser.getName());
		existingUser.setEmail(user.getEmail()!=null?user.getEmail():existingUser.getEmail());
		existingUser.setPassword(user.getPassword()!=null?bCryptEncoder.encode(user.getPassword()):existingUser.getPassword());
		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser() {
		User existingUser=readUser();
		userRepository.delete(existingUser);
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email: "+email));
	}

}
