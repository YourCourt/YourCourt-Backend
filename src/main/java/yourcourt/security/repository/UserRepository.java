package yourcourt.security.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import yourcourt.security.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT user FROM User user WHERE user.username=:username")
	Optional<User> findByUsername(@Param("username") String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	boolean existsByMembershipNumber(String membershipNumber);
	
	@Query("SELECT u FROM User u WHERE u.email=:email")
	Optional<User> findByEmail(@Param("email") String email);
	
	@Query("SELECT COUNT(*) FROM User u WHERE u.email=:email")
	Integer countByEmail(@Param("email") String email);

	
	
}
