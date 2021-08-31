package yourcourt.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto{
	
	@NotBlank
	private String name;

	@NotBlank
	private String description;
	
}
