package yourcourt.model.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import yourcourt.model.Construction;
import yourcourt.model.CourtType;
import yourcourt.model.Facility;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CourtDto extends Construction{

	@NotEmpty
	private CourtType courtType;
}
