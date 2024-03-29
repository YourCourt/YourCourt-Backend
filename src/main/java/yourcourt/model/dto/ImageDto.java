package yourcourt.model.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

	@NotBlank
	private String name;

	@NotBlank
	@URL
	private String imageUrl;

}
