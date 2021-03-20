package yourcourt.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yourcourt.repository.RoleRepository;
import yourcourt.security.Role;
import yourcourt.security.RoleType;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	public Optional<Role> getByRoleType(RoleType roleType) {
		return roleRepository.findByRoleType(roleType);
	}
}
