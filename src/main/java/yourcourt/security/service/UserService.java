package yourcourt.security.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yourcourt.exceptions.user.AttributeAlreadyExists;
import yourcourt.exceptions.user.DateAttributeMustBePast;
import yourcourt.exceptions.user.InexistentUser;
import yourcourt.security.model.User;
import yourcourt.security.model.dto.NewUser;
import yourcourt.security.repository.UserRepository;


@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public List<User> findAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	@Transactional(readOnly = true)
	public User findUserById(Long id) throws InexistentUser {

		User user = userRepository.findById(id).orElseThrow(() -> new InexistentUser());
		return user;

		
	}
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}

	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		return currentPrincipalName;
	}
	
	@Transactional
	public User updateUser(User userToUpdate, NewUser userRequest) throws DateAttributeMustBePast, AttributeAlreadyExists {
		
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
	public User deleteUserById(Long id) {

		// userRepository.deleteById(id);
		User user;
		try {
			user = userRepository.findById(id).get();
			userRepository.delete(user);
		} catch (NoSuchElementException e) {
			throw new InexistentUser();
		}
		return user;
	}
}
