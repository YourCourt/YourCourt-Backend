package yourcourt.model.dto;

import java.time.LocalDate;


import javax.validation.constraints.NotBlank;


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

	@NotBlank
	private LocalDate startDate;

	@NotBlank
	private LocalDate endDate;

}
