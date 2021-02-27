package yourcourt.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

import yourcourt.model.Login;



public interface LoginRepository extends  CrudRepository<Login, String>{
	
	Collection<Login> findAll() throws DataAccessException;
}
