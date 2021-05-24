package yourcourt.security.model.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class NewUser {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@Email
	@NotEmpty
	private String email;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Past(message = "La fecha debe ser pasada.")
	private LocalDate birthDate;
	
	@NotBlank
	@Pattern(regexp = "\\b\\d{5}\\b",message = "Debe ser de 5 dígitos exactos.")
	private String	membershipNumber;
	
	@NotBlank
	private String phone;
	
	private Set<String> roles = new HashSet<>();
	
}
