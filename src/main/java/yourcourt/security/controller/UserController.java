package yourcourt.security.controller;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.Message;
import yourcourt.security.model.Role;
import yourcourt.security.model.RoleType;
import yourcourt.security.model.User;
import yourcourt.security.model.dto.NewUser;
import yourcourt.security.model.dto.UpdateUser;
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

	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping()
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> users = userService.findAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUser(@PathVariable Long id) {
		try {
			User user = userService.findUserById(id);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping()
	public ResponseEntity<Object> createUser(@Valid @RequestBody final NewUser newUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(bindingResult));
		}


		if (userService.existsByUsername(newUser.getUsername())) {
			return new ResponseEntity<>(new Message("Nombre de usuario existente"), HttpStatus.BAD_REQUEST);
		}

		if (userService.existsByEmail(newUser.getEmail())) {
			return new ResponseEntity<>(new Message("Email existente"), HttpStatus.BAD_REQUEST);
		}

		if (userService.existsByMembershipNumber(newUser.getMembershipNumber())) {
			return new ResponseEntity<>(new Message("Numero de miembro existente"), HttpStatus.BAD_REQUEST);
		}

		User user = new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()), newUser.getEmail(),
				newUser.getBirthDate(), newUser.getPhone(), newUser.getMembershipNumber());

		user.setCreationDate(LocalDate.now());

		Set<Role> roles = new HashSet<Role>();
		roles.add(roleService.getByRoleType(RoleType.ROLE_USER).get());
		if (newUser.getRoles().contains("admin")) {
			roles.add(roleService.getByRoleType(RoleType.ROLE_ADMIN).get());
		}

		user.setRoles(roles);
		User userCreated = userService.saveUser(user);

		return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUser user,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(bindingResult));
		}

		try {
			User userToUpdate = userService.findUserById(id);
			boolean emailExistence = userService.existsByEmail(user.getEmail());
			// If exists once and its different from the previous one
			if (emailExistence && !user.getEmail().equals(userToUpdate.getEmail())) {
				return new ResponseEntity<>(new Message("Email existente"), HttpStatus.BAD_REQUEST);
			}
			try {
				User userUpdated = userService.updateUser(userToUpdate, user);

				return new ResponseEntity<>(userUpdated, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
			}
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		try {
			userService.deleteUserById(id);
			return new ResponseEntity<>(new Message("Usuario eliminado"), HttpStatus.OK);
		} catch (InexistentEntity e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

}
