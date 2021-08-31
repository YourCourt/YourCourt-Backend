package yourcourt.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Court;

@Repository
public interface CourtRepository extends CrudRepository<Court, Long>{
	/*
	Court findById(int id) throws DataAccessException;
	
	Collection<Court> findAll() throws DataAccessException;

	void deleteById(int id);

	void save(Court court) throws DataAccessException;*/
	/*
	@Query("SELECT courtTypes FROM CourtType courtTypes")
	List<CourtType> findCourtTypes();

	CourtType findCourtTypeById(int courtTypeId);*/
}
