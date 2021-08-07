package yourcourt.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import yourcourt.exceptions.RestrictedEntity;
import yourcourt.security.model.RoleType;
import yourcourt.security.model.User;
import yourcourt.security.service.UserService;

public class ValidationUtils {

  public static final Integer LOW_STOCK = 20;

  public static Map<String, List<String>> validateDto(BindingResult result) {
    Map<String, List<String>> errors = new HashMap<>();

    for (FieldError error : result.getFieldErrors()) {
      if (errors.containsKey(error.getField())) {
        errors.get(error.getField()).add(error.getDefaultMessage());
      } else {
        List<String> aux = new ArrayList<>();
        aux.add(error.getDefaultMessage());
        errors.put(error.getField(), aux);
      }
    }

    return errors;
  }

  public static Map<String, List<String>> throwError(String key, String value) {

    Map<String, List<String>> errors = new HashMap<>();
    List<String> descriptions = new ArrayList<String>();
    descriptions.add(value);
    errors.put(key, descriptions);

    return errors;
  }

  public static void accessRestrictedObjectById(Long objectUserId, UserService userService, String objectClass)
      throws RestrictedEntity {

    String usernameContext = userService.getCurrentUsername();
    Optional<User> foundUser = userService.findByUsername(usernameContext);
    if (foundUser.get().getId().equals(objectUserId) == false && (foundUser.isPresent()
        && foundUser.get().getRoles().stream().anyMatch(r -> r.getRoleType().equals(RoleType.ROLE_ADMIN))) == false) {
      throw new RestrictedEntity(objectClass);
    }
  }

  public static void accessRestrictedObjectByUsername(String username, UserService userService, String objectClass)
      throws RestrictedEntity {

    String usernameContext = userService.getCurrentUsername();
    Optional<User> foundUser = userService.findByUsername(usernameContext);
    if (usernameContext.equals(username) == false && (foundUser.isPresent()
        && foundUser.get().getRoles().stream().anyMatch(r -> r.getRoleType().equals(RoleType.ROLE_ADMIN))) == false) {
      throw new RestrictedEntity(objectClass);
    }
  }
}
