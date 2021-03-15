package yourcourt.repository;


import org.springframework.data.repository.CrudRepository;

import yourcourt.model.New;

public interface NewRepository extends CrudRepository<New, Integer> {
	
}
