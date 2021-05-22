package yourcourt.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Inscription;

@Repository
public interface InscriptionRepository extends CrudRepository<Inscription, Long>{
	

}
