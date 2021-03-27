package yourcourt.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import yourcourt.model.Construction;
import yourcourt.model.CourtType;
import yourcourt.model.Facility;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtDto{
	
	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotNull
	private CourtType courtType;
}
