package yourcourt.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import yourcourt.model.Login;



public interface LoginRepository extends  CrudRepository<Login, String>{
	
	Collection<Login> findAll() throws DataAccessException;
	
	@Query("SELECT COUNT(*) FROM Login login WHERE login.enabled=1 AND login.username=:username AND login.password=:password")
	Integer checkLoginAccount(@Param("username") String username, @Param("password") String password);
}
