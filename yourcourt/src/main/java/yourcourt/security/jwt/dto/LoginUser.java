package yourcourt.security.jwt.dto;


import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Table(name = "login_users")
public class LoginUser {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
}
