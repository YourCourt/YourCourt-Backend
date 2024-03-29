package yourcourt.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yourcourt.security.model.Role;
import yourcourt.security.model.RoleType;
import yourcourt.security.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	public Optional<Role> getByRoleType(RoleType roleType) {
		return roleRepository.findByRoleType(roleType);
	}
}
