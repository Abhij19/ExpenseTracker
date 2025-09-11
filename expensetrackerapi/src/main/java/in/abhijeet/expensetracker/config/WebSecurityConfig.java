package in.abhijeet.expensetracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.abhijeet.expensetracker.security.CustomUserDetailsService;
import in.abhijeet.expensetracker.security.JwtRequestFilter;

@Configuration
public class WebSecurityConfig{
    
	@Autowired
	private CustomUserDetailsService service;
	
	@Bean
	public JwtRequestFilter authenticationJwtTokenFilter()
	{
		return new JwtRequestFilter();
	}
	
	// Below method will allow all users to access login and register API rest will need authentication
	@Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
         .csrf(csrf -> csrf.disable()) // disable CSRF
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/login", "/register").permitAll() // allow login & register
             .anyRequest().authenticated() // secure all other APIs
         )
         .sessionManagement(session -> session
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         )
         .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
         .httpBasic(Customizer.withDefaults()); // enable HTTP Basic auth

     return http.build();
   }

    @Bean
    PasswordEncoder passwordEncoder() {
		   // this method needed because Spring says all passwords should be encrypted otherwise Spring
		   // wouldn't compare raw password and throw error
	        return new BCryptPasswordEncoder();
	    }
    
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
   AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                  .authenticationProvider(daoAuthenticationProvider())
                  .build();
    }
	 
}
