package yourcourt.security.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUser {

	@NotBlank(message = "El usuario es obligatorio")
	private String username;
	
	@NotBlank(message = "La contraseña es obligatoria")
	private String password;
	
}
