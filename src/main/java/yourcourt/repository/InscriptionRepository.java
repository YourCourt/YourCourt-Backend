package yourcourt.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Inscription;
import yourcourt.model.projections.InscriptionProjection;

@Repository
public interface InscriptionRepository extends CrudRepository<Inscription, Long>{
	
	@Query("SELECT i FROM Inscription i WHERE i.user.username=:username")
	Iterable<Inscription> findAllInscriptionsByUserUsername(String username);

	@Query("SELECT i FROM Inscription i WHERE i.course.id=:courseId")
	Iterable<InscriptionProjection> findAllInscriptionsByCourseId(Long courseId);

	@Query("SELECT i FROM Inscription i WHERE i.name=:name AND i.surnames=:surnames")
	Optional<Inscription> findInscriptionByName(String name, String surnames);

	@Query("SELECT i FROM Inscription i WHERE i.id=:id")
	Optional<InscriptionProjection> findProjectionById(Long id);

	@Query("SELECT i FROM Inscription i ")
	Iterable<InscriptionProjection> findAllInscriptionProjections();

	@Query("SELECT i FROM Inscription i WHERE i.user.username=:username")
	Iterable<InscriptionProjection> findAllInscriptionProjectionsByUserUsername(String username);


}
