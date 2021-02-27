package yourcourt.repository;


import org.springframework.data.repository.CrudRepository;


import yourcourt.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
}
