package yourcourt.repository;


import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Facility;
import yourcourt.model.FacilityType;

@Repository
public interface FacilityRepository extends CrudRepository<Facility, Long>{
	
	@Query("SELECT facilityTypes FROM FacilityType facilityTypes")
	List<FacilityType> findFacilityTypes();

	FacilityType findFacilityTypeById(int facilityTypeId);
}
