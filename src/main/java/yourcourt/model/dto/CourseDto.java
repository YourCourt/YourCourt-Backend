package yourcourt.model.dto;

import java.time.LocalDate;


import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

	@NotBlank(message = "El título es obligatorio")
	private String title;

	@NotBlank
	private String description;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate endDate;

}
