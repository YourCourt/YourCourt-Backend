package yourcourt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yourcourt.model.Facility;

@Repository
public interface FacilityRepository extends CrudRepository<Facility, Long> {}
