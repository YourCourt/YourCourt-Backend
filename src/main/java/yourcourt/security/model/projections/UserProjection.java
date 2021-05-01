package yourcourt.security.model.projections;

import java.time.LocalDate;
import java.util.Set;

import yourcourt.security.model.Role;

public interface UserProjection {

    Long getId();

    String getUsername();

    String getEmail();

    LocalDate getBirthDate();

    String getPhone();
    
    LocalDate getCreationDate();

    String getMembershipNumber();

    Set<Role> getRoles();
    
    String getImageUrl();

}
