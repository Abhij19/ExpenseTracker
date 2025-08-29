package in.abhijeet.expensetracker.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.abhijeet.expensetracker.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) 
            throws UsernameNotFoundException {

        // fetch user from DB (by email in your case)
		in.abhijeet.expensetracker.entity.User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the email: " + email));

        // convert DB UserEntity -> Spring Security UserDetails
        return new org.springframework.security.core.userdetails.User(existingUser.getEmail(),
                   existingUser.getPassword(),
                   new ArrayList<>()) ;
              
    }
}
