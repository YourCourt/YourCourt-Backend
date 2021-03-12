package yourcourt.repository;


import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import yourcourt.model.Installation;
import yourcourt.model.InstallationType;

@Repository
public interface InstallationRepository extends CrudRepository<Installation, Integer>{
	
	@Query("SELECT installationTypes FROM InstallationType installationTypes")
	List<InstallationType> findInstallationTypes();

	InstallationType findInstallationTypeById(int installationTypeId);
}
