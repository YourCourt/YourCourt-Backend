package yourcourt.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yourcourt.exceptions.InexistentEntity;
import yourcourt.security.model.User;
import yourcourt.security.model.dto.UpdateUser;
import yourcourt.security.model.projections.UserProjection;
import yourcourt.security.repository.UserRepository;

@Service
@Transactional
public class UserService {
  /**
   *
   */
  private static final String USUARIO = "Usuario";
  @Autowired
  UserRepository userRepository;

  public List<User> findAllUsers() {
    return (List<User>) userRepository.findAll();
  }

  public List<UserProjection> findAllUserUserProjections() {
    return userRepository.findAllUserUserProjections();
  }

  @Transactional(readOnly = true)
  public User findUserById(Long id) throws InexistentEntity {
    User user = userRepository.findById(id).orElseThrow(() -> new InexistentEntity(USUARIO));
    return user;
  }

  @Transactional(readOnly = true)
  public UserProjection findUserProjectionById(Long id) throws InexistentEntity {
    UserProjection user = userRepository.findUserProjectionById(id).orElseThrow(() -> new InexistentEntity(USUARIO));
    return user;
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public UserProjection findUserProjectionByUsername(String username) {
    return userRepository.findUserProjectionByUsername(username).orElseThrow(() -> new InexistentEntity(USUARIO));
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public boolean existsByMembershipNumber(String membershipNumber) {
    return userRepository.existsByMembershipNumber(membershipNumber);
  }

  public User saveUser(User user) {
    User userCreated = userRepository.save(user);
    return userCreated;
  }

  public String getCurrentUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    return currentPrincipalName;
  }

  public Collection<? extends GrantedAuthority> getCurrenAuths() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Collection<? extends GrantedAuthority> currentPrincipalName = authentication.getAuthorities();
    return currentPrincipalName;
  }

  @Transactional
  public User updateUser(User userToUpdate, UpdateUser userRequest) {
    BeanUtils.copyProperties(userRequest, userToUpdate);
    userRepository.save(userToUpdate);
    return userToUpdate;
  }

  @Transactional
  public void deleteUserById(Long id) throws InexistentEntity {
    User user = userRepository.findById(id).orElseThrow(() -> new InexistentEntity(USUARIO));
    userRepository.delete(user);
  }
}
