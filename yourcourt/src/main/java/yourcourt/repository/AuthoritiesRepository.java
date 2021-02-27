package yourcourt.repository;

import org.springframework.data.repository.CrudRepository;

import yourcourt.model.Authorities;


public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
}
