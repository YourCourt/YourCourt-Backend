package yourcourt.security.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import yourcourt.model.dto.Message;
import yourcourt.security.model.Role;
import yourcourt.security.model.RoleType;
import yourcourt.security.model.User;
import yourcourt.security.model.dto.NewUser;
import yourcourt.security.service.RoleService;
import yourcourt.security.service.UserService;



@RestController
@RequestMapping("users")
@Api(tags = "User")
@CrossOrigin
public class UserController {
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@PostMapping()
	public ResponseEntity<Object> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Message("Binding error"), HttpStatus.BAD_REQUEST);
		}
		
		if (userService.existsByUsername(newUser.getUsername())) {
			return new ResponseEntity<>(new Message("Existing username"), HttpStatus.BAD_REQUEST);
		}
		
		if (userService.existsByEmail(newUser.getEmail())) {
			return new ResponseEntity<>(new Message("Existing email"), HttpStatus.BAD_REQUEST);
		}
		
		User user = new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()), newUser.getEmail(), newUser.getBirthDate(), newUser.getPhone(), newUser.getMembershipNumber());
		
		user.setCreationDate(LocalDate.now());
		
		Set<Role> roles = new HashSet();
		roles.add(roleService.getByRoleType(RoleType.ROLE_USER).get());
		if (newUser.getRoles().contains("admin")) {
			roles.add(roleService.getByRoleType(RoleType.ROLE_ADMIN).get());
		}
		
		user.setRoles(roles);
		userService.save(user);
		
		return new ResponseEntity<>(new Message("Created user"), HttpStatus.CREATED);
	}
	
}
