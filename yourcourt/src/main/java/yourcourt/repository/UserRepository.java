package yourcourt.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import yourcourt.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.login.enabled=1 AND u.email=:email")
	Optional<User> findByEmail(@Param("email") String email);
	
	@Query("SELECT COUNT(*) FROM User u WHERE u.login.enabled=1 AND u.email=:email")
	Integer countByEmail(@Param("email") String email);
}
