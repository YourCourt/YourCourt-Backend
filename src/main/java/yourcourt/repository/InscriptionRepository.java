package yourcourt.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Inscription;

@Repository
public interface InscriptionRepository extends CrudRepository<Inscription, Long>{
	
	@Query("SELECT i FROM Inscription i WHERE i.user.username=:username")
	Iterable<Inscription> findAllInscriptionsByUserUsername(String username);

}
