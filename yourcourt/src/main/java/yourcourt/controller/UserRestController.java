package yourcourt.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import yourcourt.model.User;
import yourcourt.service.UserService;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin("*")
public class UserRestController {

	@Autowired
	private UserService userServiceAPI;

	@GetMapping(value = "/all")
	public List<User> getAll() {
		return (List<User>) userServiceAPI.findUsers();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUser(@PathVariable Integer id) {

		try {
			User obj = userServiceAPI.findUserById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping(value = "/new")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		try {
			User obj = userServiceAPI.saveUser(user);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (Exception e) {
			e.getMessage();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
		try {
			User userToUpdate = userServiceAPI.findUserById(id);
			System.out.println(userToUpdate);
			System.out.println(user);
			try {
				User obj = userServiceAPI.updateUser(userToUpdate, user);

				return new ResponseEntity<>(obj, HttpStatus.OK);
			} catch (Exception e) {
				e.getMessage();
				System.out.println(e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
	
		try {
			User obj =	userServiceAPI.deleteUserById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
