package yourcourt.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Court;
import yourcourt.model.CourtType;

@Repository
public interface CourtRepository extends CrudRepository<Court, Integer>{
	/*
	Court findById(int id) throws DataAccessException;
	
	Collection<Court> findAll() throws DataAccessException;

	void deleteById(int id);

	void save(Court court) throws DataAccessException;*/
	
	@Query("SELECT courtTypes FROM CourtType courtTypes")
	List<CourtType> findCourtTypes();

	CourtType findCourtTypeById(int courtTypeId);
}
