package yourcourt.model.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NewUser {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	@Digits(fraction = 0, integer = 10)
	private String phone;
	
	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthDate;
	
	@Email
	private String email;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	creationDate;
	
	@Pattern(regexp="\\b\\d{5}\\b")
	@NotBlank
	private String	membershipNumber;
	
	private Set<String> roles = new HashSet<>();
	
}
