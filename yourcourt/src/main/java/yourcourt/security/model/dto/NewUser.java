package yourcourt.security.model.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NewUser {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@Email
	private String email;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthDate;
	
	@NotBlank
	private String	membershipNumber;
	
	@NotBlank
	private String phone;
	
	private LocalDate creationDate;
	
	private Set<String> roles = new HashSet<>();
	
}
