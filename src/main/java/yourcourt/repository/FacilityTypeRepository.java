package yourcourt.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yourcourt.model.FacilityType;

@Repository
public interface FacilityTypeRepository extends CrudRepository<FacilityType, Long> {
  @Query(
    "select case when count(f)>= 1 then true else false end from Facility f where f.facilityType.id = :facilityTypeId"
  )
  Boolean isUsedFacilityTypeById(Long facilityTypeId);

  @Query(
    "select case when count(type)> 0 then true else false end from FacilityType type where type.typeName = :facilityType"
  )
  Boolean existsFacilityTypeByType(String facilityType);

  Optional<FacilityType> findFacilityTypeByTypeName(String facilityType);
}
