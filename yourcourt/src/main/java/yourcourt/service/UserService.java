package yourcourt.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yourcourt.exceptions.user.AttributeAlreadyExists;
import yourcourt.exceptions.user.DateAttributeMustBePast;
import yourcourt.exceptions.user.InexistentUser;
import yourcourt.repository.UserRepository;
import yourcourt.security.User;


@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public List<User> getUsers() {
		return (List<User>) userRepository.findAll();
	}
	
	public Optional<User> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	@Transactional
	public User updateUser(User userToUpdate, User userRequest) throws DateAttributeMustBePast, AttributeAlreadyExists {
		if (userRequest.getBirthDate().isAfter(LocalDate.now())) {
			throw new DateAttributeMustBePast("Birth date");
		}

		Integer emailExistence = userRepository.countByEmail(userRequest.getEmail());
		// If exists once and its different from the previous one
		if (emailExistence == 1 && !userRequest.getEmail().equals(userToUpdate.getEmail())) {
			throw new AttributeAlreadyExists("email");
		}
		BeanUtils.copyProperties(userRequest, userToUpdate, "id", "creationDate", "login", "membershipNumber");
		userRepository.save(userToUpdate);
		return userToUpdate;
	}
	
	@Transactional
	public User deleteUserById(String username) {

		// userRepository.deleteById(id);
		User user;
		try {
			user = userRepository.findByUsername(username).get();
		} catch (NoSuchElementException e) {
			throw new InexistentUser();
		}
		return user;
	}

}
