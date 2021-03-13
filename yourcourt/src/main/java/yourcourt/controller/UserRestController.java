package yourcourt.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import yourcourt.exceptions.user.InexistentUser;
import yourcourt.model.User;
import yourcourt.service.UserService;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin("*")
public class UserRestController {
	static final String ID = "/{id}";

	@Autowired
	private UserService userServiceAPI;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>((List<User>) userServiceAPI.findUsers(), HttpStatus.OK);
	}

	@GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUser(@PathVariable Integer id) {

		try {
			User obj = userServiceAPI.findUserById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (InexistentUser e) {
			
		    return new ResponseEntity<>(
		    	e.getMessage(), HttpStatus.NOT_FOUND);
			
		}
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		try {
			User obj = userServiceAPI.saveUser(user);
			return new ResponseEntity<>(obj, HttpStatus.CREATED);
		} catch (Exception e) {

			return new ResponseEntity<>(
			    	e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
		try {
			User userToUpdate = userServiceAPI.findUserById(id);

			try {
				User obj = userServiceAPI.updateUser(userToUpdate, user);

				return new ResponseEntity<>(obj, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(
				    	e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} catch (InexistentUser e) {
			return new ResponseEntity<>(
			    	e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
	
		try {
			User obj =	userServiceAPI.deleteUserById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (InexistentUser e) {
			return new ResponseEntity<>(
			    	e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
