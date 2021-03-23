package yourcourt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import yourcourt.model.Construction;
import yourcourt.model.CourtType;
import yourcourt.model.Facility;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class CourtDto extends Construction{

	private CourtType courtType;
}
