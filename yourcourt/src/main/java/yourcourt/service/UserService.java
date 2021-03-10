/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package yourcourt.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yourcourt.exceptions.user.AttributeAlreadyExists;
import yourcourt.exceptions.user.DateAttributeMustBePast;
import yourcourt.exceptions.user.InexistentUser;
import yourcourt.model.Login;
import yourcourt.model.User;
import yourcourt.repository.UserRepository;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	private LoginService loginService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public Iterable<User> findUsers() throws DataAccessException {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public User findUserById(Integer id) throws InexistentUser {
		User user;
		try {
			user = userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new InexistentUser();
		}
		return user;
	}

	@Transactional
	public User saveUser(User user) throws AttributeAlreadyExists, DateAttributeMustBePast {

		if (user.getBirthDate().isAfter(LocalDate.now())) {
			throw new DateAttributeMustBePast("Birth date");
		}

		user.setCreationDate(LocalDate.now());

		// creating user

		Optional<Login> loginExistence = loginService.findByUsername(user.getLogin().getUsername());
		Optional<User> emailExistence = userRepository.findByEmail(user.getEmail());
		if (!loginExistence.isEmpty()) {
			throw new AttributeAlreadyExists("username");
		}
		if (!emailExistence.isEmpty()) {
			throw new AttributeAlreadyExists("email");
		}
		userRepository.save(user);
		loginService.saveLogin(user.getLogin());
		// creating authorities
		authoritiesService.saveAuthorities(user.getLogin().getUsername(), "user");

		return user;
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
		BeanUtils.copyProperties(userRequest, userToUpdate, "id", "creationDate", "login");
		userRepository.save(userToUpdate);
		return userToUpdate;
	}

	@Transactional
	public User deleteUserById(Integer id) {

		// userRepository.deleteById(id);
		User user;
		try {
			user = userRepository.findById(id).get();
			user.getLogin().setEnabled(false);
		} catch (NoSuchElementException e) {
			throw new InexistentUser();
		}
		return user;
	}

}
