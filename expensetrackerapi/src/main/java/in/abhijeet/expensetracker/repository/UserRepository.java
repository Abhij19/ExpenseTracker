package in.abhijeet.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.abhijeet.expensetracker.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User,Long>{

	Boolean existsByEmail(String email);
}
