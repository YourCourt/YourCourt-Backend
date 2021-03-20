package yourcourt.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yourcourt.security.Role;
import yourcourt.security.RoleType;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleType(RoleType roleType);
	
}
