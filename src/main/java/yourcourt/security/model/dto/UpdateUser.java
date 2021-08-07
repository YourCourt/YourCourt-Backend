package yourcourt.security.model.dto;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UpdateUser {
	
	@Email
	@NotBlank
	private String email;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Past(message = "La fecha debe ser pasada.")
	private LocalDate birthDate;
	
	@NotBlank
	private String phone;

	
}
